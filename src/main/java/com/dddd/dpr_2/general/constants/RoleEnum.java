package com.dddd.dpr_2.general.constants;

import lombok.Getter;

@Getter
public enum RoleEnum {

	ROLE_USER("User"),
	ROLE_ADMIN("Admin");

	private final String label;

	RoleEnum(String label) {
		this.label = label;
	}

}
