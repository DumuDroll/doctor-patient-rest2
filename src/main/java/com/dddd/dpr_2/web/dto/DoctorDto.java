package com.dddd.dpr_2.web.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DoctorDto {

	@ToString.Exclude
	private long id;

	private UUID uuid;

	private int order;
}
