package com.dddd.dpr_2.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private long id;

	private UUID uuid;

	@JsonIgnore
	private int order;

	private String iconName;

	private byte[] icon;

}
