package com.dddd.dpr_2.database.models;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PatientDrugId implements Serializable {

	private long patientId;

	private long drugId;

}
