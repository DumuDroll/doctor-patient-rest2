package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.*;
import com.dddd.dpr_2.database.repository.DoctorRepository;
import com.dddd.dpr_2.database.repository.DrugRepository;
import com.dddd.dpr_2.database.repository.PatientRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceAlreadyExistsException;
import com.dddd.dpr_2.general.exceptions.ResourceNotFoundException;
import com.dddd.dpr_2.web.dto.PatientDrugDto;
import com.dddd.dpr_2.web.dto.PatientDto;
import com.dddd.dpr_2.web.mappers.PatientMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Log4j2
@Service
@Transactional
public class PatientServiceImpl {

	private final PatientRepository patientRepository;

	private final DoctorRepository doctorRepository;

	private final DrugRepository drugRepository;

	private final PatientMapper patientMapper;


	public PatientServiceImpl(PatientRepository patientRepository,
							  DoctorRepository doctorRepository,
							  DrugRepository drugRepository,
							  PatientMapper patientMapper) {
		this.patientRepository = patientRepository;
		this.doctorRepository = doctorRepository;
		this.drugRepository = drugRepository;
		this.patientMapper = patientMapper;
	}

	public PatientDto addDoctorToPatient(long doctorId, PatientDto patientDto) {
		Doctor doctor;
		Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
		if (optionalDoctor.isPresent()) {
			doctor = optionalDoctor.get();
		} else {
			throw new ResourceNotFoundException(Constants.DOCTOR_NOT_FOUND, doctorId);
		}
		findById(patientDto.getId());
		Patient patient = patientMapper.patientDtoToPatient(patientDto);
		if (doctor.getPatients() == null) {
			doctor.setPatients(new ArrayList<>());
		}
		doctor.getPatients().add(patient);
		patient.setDoctor(doctor);
		doctorRepository.save(doctor);
		return patientMapper.patientToPatientDto(patientRepository.save(patient));
	}

	public PatientDto addDrugToPatient(List<PatientDrugDto> patientDrugDtoList, long patientId) {
		List<Drug> drugs = new ArrayList<>();
		for (PatientDrugDto patientDrugDto : patientDrugDtoList) {
			Optional<Drug> optionalDrug = drugRepository.findById(patientDrugDto.getDrugId());
			if (optionalDrug.isPresent()) {
				drugs.add(optionalDrug.get());
			} else {
				throw new ResourceNotFoundException(Constants.DRUG_NOT_FOUND, patientDrugDto.getDrugId());
			}
		}
		Patient patient = patientMapper.patientDtoToPatient(findById(patientId));
		drugs.forEach(drug -> {
			PatientDrug patientDrug = new PatientDrug(patient, drug);
			patient.getDrugs().add(patientDrug);
		});
		return patientMapper.patientToPatientDto(patientRepository.save(patient));
	}

	public List<PatientDto> findAll() {
		return patientMapper.patientListToPatientDtoList(patientRepository.findAll());
	}

	public ResponseEntity<Map<String, Object>> findAllFiltered(String fNameLName, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
		Page<Patient> patients;
		if (fNameLName.equals("")) {
			patients = patientRepository.findAll(pageRequest);
		} else {
			String[] arr = fNameLName.split(" ", 2);
			String fName;
			String lName = "";
			if (arr.length == 2) {
				fName = arr[0];
				lName = arr[1];
			} else {
				fName = arr[0];
			}
			patients = patientRepository.findAll(pageRequest);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("data", patientMapper.patientListToPatientDtoList(patients.getContent()));
		response.put("currentPage", patients.getNumber());
		response.put("totalItems", patients.getTotalElements());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public PatientDto findById(long id) {
		return patientRepository.findById(id).map(patientMapper::patientToPatientDto)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.PATIENT_NOT_FOUND, id));
	}

	public PatientDto create(PatientDto patientDto) {
		if (patientDto.getId() != 0 && patientRepository.findById(patientDto.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException(Constants.PATIENT_ALREADY_EXISTS, patientDto.getId());
		}
		Patient patient = patientMapper.patientDtoToPatient(patientDto);
		if (patient.getFullInfo() == null) {
			patient.setFullInfo(new FullInfo());
		}
		FullInfo fullInfo = patient.getFullInfo();
		fullInfo.setPatient(patient);
		return patientMapper.patientToPatientDto(patientRepository.save(patient));
	}

	public PatientDto update(PatientDto patientDto) {
		Optional<Patient> patient = patientRepository.findById(patientDto.getId());
		return patient.map(value -> patientMapper.patientToPatientDto(patientRepository
						.save(patientMapper.patientDtoToPatient(patientDto))))
				.orElseThrow(() -> new ResourceNotFoundException(Constants.PATIENT_NOT_FOUND, patientDto.getId()));
	}

	public void deleteById(long id) {
		patientRepository.deleteById(id);
	}
}
