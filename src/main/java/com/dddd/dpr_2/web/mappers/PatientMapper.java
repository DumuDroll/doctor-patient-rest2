package com.dddd.dpr_2.web.mappers;

import com.dddd.dpr_2.database.models.*;
import com.dddd.dpr_2.web.dto.PatientDrugDto;
import com.dddd.dpr_2.web.dto.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

	PatientDto patientToPatientDto(Patient patient);

	Patient patientDtoToPatient(PatientDto patientDto);

	List<PatientDto> patientListToPatientDtoList(List<Patient> patients);

}
