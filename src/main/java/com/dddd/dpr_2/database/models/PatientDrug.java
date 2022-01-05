package com.dddd.dpr_2.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "patient_drug")
@Getter
@Setter
@NoArgsConstructor
public class PatientDrug  {

	@EmbeddedId
	private PatientDrugId id;

	private UUID uuid;

	private int itemOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("patientId")
	private Patient patient;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("drugId")
	private Drug drug;

	public PatientDrug(Patient patient, Drug drug) {
		this.patient = patient;
		this.drug = drug;
		this.id = new PatientDrugId(patient.getId(), drug.getId());
	}

}
