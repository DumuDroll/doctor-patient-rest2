package com.dddd.dpr_2.database.models;


import com.dddd.dpr_2.database.models.logger_listeners.FullInfoLoggerListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@EntityListeners(FullInfoLoggerListener.class)
@Table(name = "full_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullInfo {

	@Id
	private long id;

	private UUID uuid;

	private int itemOrder;

	@OneToOne(mappedBy = "fullInfo")
	private Patient patient;

}
