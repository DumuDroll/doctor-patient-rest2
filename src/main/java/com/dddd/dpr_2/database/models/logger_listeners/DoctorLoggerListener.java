package com.dddd.dpr_2.database.models.logger_listeners;

import com.dddd.dpr_2.database.models.Doctor;
import com.dddd.dpr_2.general.constants.Constants;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@Log4j2
public class DoctorLoggerListener {

	@PrePersist
	public void methodInvokedBeforePersist(Doctor doctor) {
		log.info(Constants.PERSISTING + Constants.DOCTOR);
	}

	@PostPersist
	public void methodInvokedAfterPersist(Doctor doctor) {
		log.info(Constants.PERSISTED + Constants.DOCTOR_WITH_ID + doctor.getId());
	}

	@PreUpdate
	public void methodInvokedBeforeUpdate(Doctor doctor) {
		log.info(Constants.UPDATING + Constants.DOCTOR_WITH_ID + doctor.getId());
	}

	@PostUpdate
	public void methodInvokedAfterUpdate(Doctor doctor) {
		log.info(Constants.UPDATED + Constants.DOCTOR_WITH_ID + doctor.getId());
	}

	@PreRemove
	private void methodInvokedBeforeRemove(Doctor doctor) {
		log.info(Constants.REMOVING + Constants.DOCTOR_WITH_ID + doctor.getId());
	}

	@PostRemove
	public void methodInvokedAfterRemove(Doctor doctor) {
		log.info(Constants.REMOVED + Constants.DOCTOR_WITH_ID + doctor.getId());
	}

}

