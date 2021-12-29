package com.dddd.dpr_2;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DoctorPatientRest2Application {

	@Bean
	public Queue myQueue() {
		return new Queue("myQueue", false);
	}

	@RabbitListener(queues = "myQueue")
	public void listen(String in) {
		System.out.println("Message read from myQueue : " + in);
	}

	public static void main(String[] args) {
		SpringApplication.run(DoctorPatientRest2Application.class, args);
	}

}
