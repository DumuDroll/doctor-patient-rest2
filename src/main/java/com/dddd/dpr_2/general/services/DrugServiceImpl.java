package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.Drug;
import com.dddd.dpr_2.database.models.Patient;
import com.dddd.dpr_2.database.repository.DrugRepository;
import com.dddd.dpr_2.database.repository.PatientRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceAlreadyExistsException;
import com.dddd.dpr_2.general.exceptions.ResourceNotFoundException;
import com.dddd.dpr_2.web.dto.DrugDto;
import com.dddd.dpr_2.web.mappers.DrugMapper;
import com.dddd.dpr_2.web.mappers.PatientMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class DrugServiceImpl {

	private final DrugRepository drugRepository;

	private final PatientRepository patientRepository;

	private final PatientServiceImpl patientService;

	private final DrugMapper drugMapper;

	private final PatientMapper patientMapper;

	public DrugServiceImpl(DrugRepository drugRepository,
						   PatientRepository patientRepository, PatientServiceImpl patientService,
						   DrugMapper drugMapper,
						   PatientMapper patientMapper) {
		this.drugRepository = drugRepository;
		this.patientRepository = patientRepository;
		this.patientService = patientService;
		this.drugMapper = drugMapper;
		this.patientMapper = patientMapper;
	}

	public List<DrugDto> findAll() {
		return drugMapper.drugListToDrugDtoList(drugRepository.findAll());
	}

	public ResponseEntity<Map<String, Object>> findAllFiltered(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
		Page<Drug> drugs;
		drugs = drugRepository.findAll(pageRequest);
		Map<String, Object> response = new HashMap<>();
		response.put("data", drugMapper.drugListToDrugDtoList(drugs.getContent()));
		response.put("currentPage", drugs.getNumber());
		response.put("pageSize", drugs.getSize());
		response.put("totalItems", drugs.getTotalElements());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public DrugDto findById(long id) {
		return drugRepository.findById(id).map(drugMapper::drugToDrugDto)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.DRUG_NOT_FOUND, id));
	}

	public DrugDto create(DrugDto drugDto) {
		if (drugDto.getId() != 0 && drugRepository.findById(drugDto.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException(Constants.DRUG_ALREADY_EXISTS, drugDto.getId());
		}
		return drugMapper.drugToDrugDto(drugRepository.save(drugMapper.drugDtoToDrug(drugDto)));
	}

	public DrugDto update(DrugDto drugDto) {
		Optional<Drug> drug = drugRepository.findById(drugDto.getId());
		return drug.map(value -> drugMapper.drugToDrugDto(drugRepository
						.save(drugMapper.drugDtoToDrug(drugDto))))
				.orElseThrow(() -> new ResourceNotFoundException(Constants.DRUG_NOT_FOUND, drugDto.getId()));
	}

	public void deleteById(long id) {
		drugRepository.deleteById(id);
	}

	public DrugDto addPatientToDrug(long patientId, DrugDto drugDto) {
		Patient patient = patientMapper.patientDtoToPatient(patientService.findById(patientId));
		findById(drugDto.getId());
		Drug drug = drugMapper.drugDtoToDrug(drugDto);
		if (patient.getDrugs() == null) {
			patient.setDrugs(new ArrayList<>());
		}
		patient.getDrugs().forEach(value -> {
			if (value.getDrug().getId()==drugDto.getId()) {
				throw new ResourceAlreadyExistsException("This drug is already prescribed to a patient with id: ", patientId);
			}
		});
		patientRepository.save(patient);
		return drugMapper.drugToDrugDto(drugRepository.save(drug));
	}
}
