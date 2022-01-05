package com.dddd.dpr_2.database.models;

import com.dddd.dpr_2.database.models.logger_listeners.DrugLoggerListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners({DrugLoggerListener.class})
@Table(name = "drugs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drug extends OrderedModel{

	@OneToMany(
			mappedBy = "drug",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<PatientDrug> patients = new ArrayList<>();

}
