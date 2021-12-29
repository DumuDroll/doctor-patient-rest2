package com.dddd.dpr_2.web.mappers;

import com.dddd.dpr_2.database.models.Drug;
import com.dddd.dpr_2.web.dto.DrugDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DrugMapper {

	DrugDto drugToDrugDto(Drug drug);

	Drug drugDtoToDrug(DrugDto drugDto);

	List<DrugDto> drugListToDrugDtoList(List<Drug> drugs);

}
