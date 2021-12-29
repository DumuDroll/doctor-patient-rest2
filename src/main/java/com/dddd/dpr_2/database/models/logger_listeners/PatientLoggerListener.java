package com.dddd.dpr_2.database.models.logger_listeners;

import com.dddd.dpr_2.database.models.Patient;
import com.dddd.dpr_2.general.constants.Constants;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@Log4j2
public class PatientLoggerListener {

	@PrePersist
	public void methodInvokedBeforePersist(Patient entity) {
		log.info(Constants.PERSISTING + Constants.PATIENT);
	}

	@PostPersist
	public void methodInvokedAfterPersist(Patient entity) {
		log.info(Constants.PERSISTED + Constants.PATIENT_WITH_ID + entity.getId());
	}

	@PreUpdate
	public void methodInvokedBeforeUpdate(Patient entity) {
		log.info(Constants.UPDATING + Constants.PATIENT_WITH_ID + entity.getId());
	}

	@PostUpdate
	public void methodInvokedAfterUpdate(Patient entity) {
		log.info(Constants.UPDATED + Constants.PATIENT_WITH_ID + entity.getId());
	}

	@PreRemove
	private void methodInvokedBeforeRemove(Patient entity) {
		log.info(Constants.REMOVING + Constants.PATIENT_WITH_ID + entity.getId());
	}

	@PostRemove
	public void methodInvokedAfterRemove(Patient entity) {
		log.info(Constants.REMOVED + Constants.PATIENT_WITH_ID + entity.getId());
	}

}
