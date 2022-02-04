package com.dddd.dpr_2;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DoctorPatientRest2Application {

	@Bean
	public Queue savedDoctor() {
		return new Queue("savedDoctor", false);
	}
	@Bean
	public Queue deletedDoctor() {
		return new Queue("deletedDoctor", false);
	}

	@Bean
	public Queue savedDrug() {
		return new Queue("savedDrug", false);
	}
	@Bean
	public Queue deletedDrug() {
		return new Queue("deletedDrug", false);
	}

	@Bean
	public Queue savedPatient() {
		return new Queue("savedPatient", false);
	}
	@Bean
	public Queue updatedPatient() {
		return new Queue("updatedPatient", false);
	}
	@Bean
	public Queue deletedPatient() {
		return new Queue("deletedPatient", false);
	}

	@Bean
	public Queue savedUser() {
		return new Queue("savedUser", false);
	}
	@Bean
	public Queue deletedUser() {
		return new Queue("deletedUser", false);
	}

	@Bean
	public Queue saveUserIcon() {
		return new Queue("saveIcon", false);
	}
	@Bean
	public Queue requestForIcon() {
		return new Queue("requestForIcon", false);
	}

	@Bean
	public Queue saveDiagnosis() {
		return new Queue("saveDiagnosis", false);
	}

	public static void main(String[] args) {
		SpringApplication.run(DoctorPatientRest2Application.class, args);
	}

}
