package com.dddd.dpr_2.web.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FullInfoDto {

	@ToString.Exclude
	private long id;

	private UUID uuid;

	private int order;

}
