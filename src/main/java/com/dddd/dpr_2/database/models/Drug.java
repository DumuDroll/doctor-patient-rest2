package com.dddd.dpr_2.database.models;

import com.dddd.dpr_2.database.models.logger_listeners.DrugLoggerListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners({DrugLoggerListener.class})
@Table(name = "drugs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drug {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private UUID uuid;

	private int order;

	@OneToMany(
			mappedBy = "drug",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<PatientDrug> patients = new ArrayList<>();

}
