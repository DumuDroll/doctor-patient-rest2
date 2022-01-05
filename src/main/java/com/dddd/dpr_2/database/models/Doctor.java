package com.dddd.dpr_2.database.models;

import com.dddd.dpr_2.database.models.logger_listeners.DoctorLoggerListener;
import com.dddd.dpr_2.database.models.pre_remove_listeners.DoctorPreRemoveListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@EntityListeners({DoctorLoggerListener.class, DoctorPreRemoveListener.class})
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends OrderedModel {

	@OneToMany(mappedBy = "doctor")
	private List<Patient> patients;

}
