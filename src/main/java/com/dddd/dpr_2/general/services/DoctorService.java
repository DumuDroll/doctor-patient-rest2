package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.Doctor;
import com.dddd.dpr_2.database.repository.DoctorRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceAlreadyExistsException;
import com.dddd.dpr_2.general.order_strategies.OrderContext;
import com.dddd.dpr_2.general.order_strategies.OrderContextBuilder;
import com.dddd.dpr_2.general.order_strategies.strategies.IteratorOrderStrategy;
import com.dddd.dpr_2.web.dto.DoctorDto;
import com.dddd.dpr_2.web.mappers.DoctorMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class DoctorService {

	private final DoctorRepository doctorRepository;

	private final DoctorMapper doctorMapper;

	private final IteratorOrderStrategy<Doctor, DoctorRepository> iteratorOrderStrategy;

	public DoctorService(DoctorRepository doctorRepository,
						 DoctorMapper doctorMapper,
						 IteratorOrderStrategy<Doctor, DoctorRepository> iteratorOrderStrategy) {
		this.doctorRepository = doctorRepository;
		this.doctorMapper = doctorMapper;
		this.iteratorOrderStrategy = iteratorOrderStrategy;
	}

	public void create(DoctorDto doctorDto) {
		Doctor doctor = doctorMapper.doctorDtoToDoctor(doctorDto);

		if (doctorDto.getId() != 0 && doctorRepository.findById(doctorDto.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException(Constants.DOCTOR_ALREADY_EXISTS, doctorDto.getId());
		}
		doctorMapper.doctorToDoctorDto(doctorRepository.save(doctor));

		OrderContext<Doctor, DoctorRepository> orderContext = new OrderContextBuilder<Doctor, DoctorRepository>()
				.setObject(doctor)
				.setRepository(doctorRepository)
				.setOrderStrategy(iteratorOrderStrategy)
				.build();
		orderContext.executeStrategy();
	}

	public void deleteById(long id) {
		if (doctorRepository.findById(id).isPresent()) {
			doctorRepository.deleteById(id);
		}
	}

}
