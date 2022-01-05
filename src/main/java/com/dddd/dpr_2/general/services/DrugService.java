package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.Drug;
import com.dddd.dpr_2.database.repository.DrugRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceAlreadyExistsException;
import com.dddd.dpr_2.general.order_strategies.OrderContext;
import com.dddd.dpr_2.general.order_strategies.OrderContextBuilder;
import com.dddd.dpr_2.general.order_strategies.strategies.SetValueOrderStrategy;
import com.dddd.dpr_2.web.dto.DrugDto;
import com.dddd.dpr_2.web.mappers.DrugMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class DrugService {

	private final DrugRepository drugRepository;

	private final DrugMapper drugMapper;

	private final SetValueOrderStrategy<Drug, DrugRepository> setValueOrderStrategy;

	public DrugService(DrugRepository drugRepository,
					   DrugMapper drugMapper,
					   SetValueOrderStrategy<Drug, DrugRepository> setValueOrderStrategy) {
		this.drugRepository = drugRepository;
		this.drugMapper = drugMapper;
		this.setValueOrderStrategy = setValueOrderStrategy;
	}

	public void create(DrugDto drugDto) {
		Drug drug;
		if (drugDto.getId() != 0 && drugRepository.findById(drugDto.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException(Constants.DRUG_ALREADY_EXISTS, drugDto.getId());
		}
		drug = drugMapper.drugDtoToDrug(drugDto);

		OrderContext<Drug, DrugRepository> orderContext = new OrderContextBuilder<Drug, DrugRepository>()
				.setObject(drug)
				.setOrderStrategy(setValueOrderStrategy)
				.setOrder(13)
				.build();
		orderContext.executeStrategy();

		drugMapper.drugToDrugDto(drugRepository.save(drug));
	}

	public void deleteById(long id) {
		if (drugRepository.findById(id).isPresent()) {
			drugRepository.deleteById(id);
		}
	}

}
