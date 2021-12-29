package com.dddd.dpr_2.web.mappers;

import com.dddd.dpr_2.database.models.FullInfo;
import com.dddd.dpr_2.web.dto.FullInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FullInfoMapper {

	FullInfoDto fullInfoToFullInfoDto(FullInfo fullInfo);

	FullInfo fullInfoDtoToFullInfo(FullInfoDto fullInfoDto);

	List<FullInfoDto> fullInfoListToFullInfoDtoList(List<FullInfo> fullInfos);

}
