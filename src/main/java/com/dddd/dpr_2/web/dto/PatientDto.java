package com.dddd.dpr_2.web.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PatientDto {

	@ToString.Exclude
	private long id;

	private UUID uuid;

	private int order;

	@ToString.Exclude
	private DoctorDto doctor;

	@ToString.Exclude
	private List<PatientDrugDto> drugs;

}