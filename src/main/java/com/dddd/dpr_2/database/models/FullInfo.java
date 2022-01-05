package com.dddd.dpr_2.database.models;


import com.dddd.dpr_2.database.models.logger_listeners.FullInfoLoggerListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@EntityListeners(FullInfoLoggerListener.class)
@Table(name = "full_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullInfo extends OrderedModel {

	@OneToOne(mappedBy = "fullInfo")
	private Patient patient;

}
