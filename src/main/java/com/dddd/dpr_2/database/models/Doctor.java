package com.dddd.dpr_2.database.models;

import com.dddd.dpr_2.database.models.logger_listeners.DoctorLoggerListener;
import com.dddd.dpr_2.database.models.pre_remove_listeners.DoctorPreRemoveListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners({DoctorLoggerListener.class, DoctorPreRemoveListener.class})
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private UUID uuid;

	private int order;

	@OneToMany(mappedBy = "doctor")
	private List<Patient> patients;

}
