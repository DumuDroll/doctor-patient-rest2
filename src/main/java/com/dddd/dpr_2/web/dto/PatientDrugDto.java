package com.dddd.dpr_2.web.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PatientDrugDto {

	private long patientId;

	private long drugId;

	private UUID uuid;

	private int order;

}