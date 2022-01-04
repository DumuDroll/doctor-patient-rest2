package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.FullInfo;
import com.dddd.dpr_2.database.models.Patient;
import com.dddd.dpr_2.database.repository.PatientRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceAlreadyExistsException;
import com.dddd.dpr_2.general.exceptions.ResourceNotFoundException;
import com.dddd.dpr_2.web.dto.PatientDto;
import com.dddd.dpr_2.web.mappers.PatientMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@Transactional
public class PatientServiceImpl {

	private final PatientRepository patientRepository;


	private final PatientMapper patientMapper;

	public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
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
		patientMapper.patientToPatientDto(patientRepository.save(patient));
	}

	public void update(PatientDto patientDto) {
		Optional<Patient> patient = patientRepository.findById(patientDto.getId());
		if (patient.isEmpty()) {
			throw new ResourceNotFoundException(Constants.PATIENT_NOT_FOUND, patientDto.getId());
		}
		patientMapper.patientToPatientDto(patientRepository.save(patientMapper.patientDtoToPatient(patientDto)));

	}

	public void deleteById(long id) {
		if (patientRepository.findById(id).isPresent()) {
			patientRepository.deleteById(id);
		}
	}
}
