package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.FullInfo;
import com.dddd.dpr_2.database.models.Patient;
import com.dddd.dpr_2.database.models.User;
import com.dddd.dpr_2.database.repository.PatientRepository;
import com.dddd.dpr_2.general.config.ChainStrategyConfig;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceAlreadyExistsException;
import com.dddd.dpr_2.general.exceptions.ResourceNotFoundException;
import com.dddd.dpr_2.general.order_strategies.OrderContext;
import com.dddd.dpr_2.general.order_strategies.OrderContextBuilder;
import com.dddd.dpr_2.web.dto.PatientDto;
import com.dddd.dpr_2.web.dto.UserDto;
import com.dddd.dpr_2.web.mappers.PatientMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@Transactional
public class PatientService {

	private final PatientRepository patientRepository;

	private final PatientMapper patientMapper;

	private final ChainStrategyConfig<Patient, PatientRepository> chainStrategyConfig;

	public PatientService(PatientRepository patientRepository,
						  PatientMapper patientMapper,
						  ChainStrategyConfig<Patient, PatientRepository> chainStrategyConfig) {
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
		this.chainStrategyConfig = chainStrategyConfig;
	}

	public void create(PatientDto patientDto) {
		if (patientDto.getId() != 0 && patientRepository.findById(patientDto.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException(Constants.PATIENT_ALREADY_EXISTS, patientDto.getId());
		}
		Patient patient = patientMapper.patientDtoToPatient(patientDto);
		if (patient.getFullInfo() == null) {
			patient.setFullInfo(new FullInfo());
		}
		FullInfo fullInfo = patient.getFullInfo();
		fullInfo.setPatient(patient);
		OrderContext<Patient, PatientRepository> orderContext = new OrderContextBuilder<Patient, PatientRepository>()
				.setObject(patient)
				.setRepository(patientRepository)
				.setOrderStrategy(chainStrategyConfig.chainOrderStrategy())
				.build();
		orderContext.executeStrategy();

		patientMapper.patientToPatientDto(patientRepository.save(patient));
	}

	public void update(PatientDto patientDto) {
		Optional<Patient> patient = patientRepository.findById(patientDto.getId());
		if (patient.isEmpty()) {
			throw new ResourceNotFoundException(Constants.PATIENT_NOT_FOUND, patientDto.getId());
		}
		patientMapper.patientToPatientDto(patientRepository.save(patientMapper.patientDtoToPatient(patientDto)));

	}

	public Message saveDiagnosisAndReturnPath(PatientDto patientDto) {
		MultipartFile file;
		try {
			file = new ObjectMapper().readValue(patientDto.getDiagnosis(), MultipartFile.class);
			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
			String fileName = date + file.getOriginalFilename();
			Files.write(Path.of(fileName), file.getBytes());
			String filePath = File.separator + fileName;

			Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
			return createMessage(filePath);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public void deleteById(long id) {
		if (patientRepository.findById(id).isPresent()) {
			patientRepository.deleteById(id);
		}
	}

	private Message createMessage(String path) {
		try {
			ObjectMapper mapper = JsonMapper.builder()
					.findAndAddModules()
					.build();
			return MessageBuilder
					.withBody(mapper.writeValueAsString(Paths.get(path)).getBytes(StandardCharsets.UTF_8))
					.setContentType(MessageProperties.CONTENT_TYPE_JSON)
					.build();
		} catch (JsonProcessingException jsonProcessingException) {
			return MessageBuilder
					.withBody("".getBytes(StandardCharsets.UTF_8))
					.setContentType(MessageProperties.CONTENT_TYPE_JSON)
					.build();
		}
	}
}
