package com.dddd.dpr_2.database.models.pre_remove_listeners;

import com.dddd.dpr_2.database.models.Drug;

import javax.persistence.PreRemove;

public class DrugPreRemoveListener {

	@PreRemove
	private void preDrugRemove(Drug drug) {
		drug.getPatients().forEach(patientDrug -> {
			if (patientDrug.getDrug().equals(drug)) {
				patientDrug.setDrug(null);
			}
		});
	}
}
