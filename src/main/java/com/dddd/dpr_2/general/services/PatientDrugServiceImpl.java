package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.repository.PatientDrugRepository;
import com.dddd.dpr_2.web.mappers.PatientMapper;
import org.springframework.stereotype.Service;

@Service
public class PatientDrugServiceImpl {

	private final PatientDrugRepository patientDrugRepository;

	private final PatientMapper patientMapper;

	public PatientDrugServiceImpl(PatientDrugRepository patientDrugRepository,
								  PatientMapper patientMapper) {
		this.patientDrugRepository = patientDrugRepository;
		this.patientMapper = patientMapper;
	}


}
