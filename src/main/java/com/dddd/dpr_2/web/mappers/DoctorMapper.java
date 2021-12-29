package com.dddd.dpr_2.web.mappers;

import com.dddd.dpr_2.database.models.Doctor;
import com.dddd.dpr_2.web.dto.DoctorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

	DoctorDto doctorToDoctorDto(Doctor doctor);

	Doctor doctorDtoToDoctor(DoctorDto doctorDto);

	List<DoctorDto> doctorListToDoctorDtoList(List<Doctor> doctors);

}
