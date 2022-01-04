package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.repository.DoctorRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceAlreadyExistsException;
import com.dddd.dpr_2.web.dto.DoctorDto;
import com.dddd.dpr_2.web.mappers.DoctorMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class DoctorServiceImpl {

	private final DoctorRepository doctorRepository;

	private final DoctorMapper doctorMapper;

	@Autowired
	public DoctorServiceImpl(DoctorRepository doctorRepository,
							 DoctorMapper doctorMapper) {
		this.doctorRepository = doctorRepository;
		this.doctorMapper = doctorMapper;
	}

	public void create(DoctorDto doctorDto) {
		if (doctorDto.getId() != 0 && doctorRepository.findById(doctorDto.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException(Constants.DOCTOR_ALREADY_EXISTS, doctorDto.getId());
		}
		doctorMapper.doctorToDoctorDto(doctorRepository.save(doctorMapper.doctorDtoToDoctor(doctorDto)));
	}

	public void deleteById(long id) {
		if (doctorRepository.findById(id).isPresent()) {
			doctorRepository.deleteById(id);
		}
	}

}
