package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.repository.DrugRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceAlreadyExistsException;
import com.dddd.dpr_2.web.dto.DrugDto;
import com.dddd.dpr_2.web.mappers.DrugMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class DrugServiceImpl {

	private final DrugRepository drugRepository;

	private final DrugMapper drugMapper;

	public DrugServiceImpl(DrugRepository drugRepository, DrugMapper drugMapper) {
		this.drugRepository = drugRepository;
		this.drugMapper = drugMapper;
	}

	public void create(DrugDto drugDto) {
		if (drugDto.getId() != 0 && drugRepository.findById(drugDto.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException(Constants.DRUG_ALREADY_EXISTS, drugDto.getId());
		}
		drugMapper.drugToDrugDto(drugRepository.save(drugMapper.drugDtoToDrug(drugDto)));
	}

	public void deleteById(long id) {
		if (drugRepository.findById(id).isPresent()) {
			drugRepository.deleteById(id);
		}
	}

}
