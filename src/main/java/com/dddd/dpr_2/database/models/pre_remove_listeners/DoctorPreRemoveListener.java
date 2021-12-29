package com.dddd.dpr_2.database.models.pre_remove_listeners;


import com.dddd.dpr_2.database.models.Doctor;

import javax.persistence.PreRemove;

public class DoctorPreRemoveListener {
	@PreRemove
	private void methodInvokedBeforeRemove(Doctor entity) {
		entity.getPatients().forEach(patient -> patient.setDoctor(null));
	}
}
