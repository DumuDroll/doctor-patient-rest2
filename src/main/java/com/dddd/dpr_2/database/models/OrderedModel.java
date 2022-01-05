package com.dddd.dpr_2.database.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class OrderedModel {

	@Id
	long id;

	UUID uuid;

	int itemOrder;

}
