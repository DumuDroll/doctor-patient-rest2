package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.FullInfo;
import com.dddd.dpr_2.database.repository.FullInfoRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceAlreadyExistsException;
import com.dddd.dpr_2.general.exceptions.ResourceNotFoundException;
import com.dddd.dpr_2.web.dto.FullInfoDto;
import com.dddd.dpr_2.web.mappers.FullInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class FullInfoServiceImpl {

	private final FullInfoRepository fullInfoRepository;

	private final FullInfoMapper fullInfoMapper;

	public FullInfoServiceImpl(FullInfoRepository fullInfoRepository,
							   FullInfoMapper fullInfoMapper) {
		this.fullInfoRepository = fullInfoRepository;
		this.fullInfoMapper = fullInfoMapper;
	}


	public List<FullInfoDto> findAll() {
		return fullInfoMapper.fullInfoListToFullInfoDtoList(fullInfoRepository.findAll());
	}

	public ResponseEntity<Map<String, Object>> findAllFiltered(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
		Page<FullInfo> fullInfos;
		fullInfos = fullInfoRepository.findAll(pageRequest);
		Map<String, Object> response = new HashMap<>();
		response.put("data", fullInfoMapper.fullInfoListToFullInfoDtoList(fullInfos.getContent()));
		response.put("currentPage", fullInfos.getNumber());
		response.put("pageSize", fullInfos.getSize());
		response.put("totalItems", fullInfos.getTotalElements());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public FullInfoDto findById(long id) {
		return fullInfoRepository.findById(id).map(fullInfoMapper::fullInfoToFullInfoDto)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.FULL_INFO_NOT_FOUND, id));
	}

	public FullInfoDto create(FullInfoDto fullInfoDto) {
		if (fullInfoDto.getId() != 0 && fullInfoRepository.findById(fullInfoDto.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException(Constants.FULL_INFO_ALREADY_EXISTS, fullInfoDto.getId());
		}
		return fullInfoMapper.fullInfoToFullInfoDto(fullInfoRepository.save(fullInfoMapper.fullInfoDtoToFullInfo(fullInfoDto)));
	}

	public FullInfoDto update(FullInfoDto fullInfoDto) {
		Optional<FullInfo> fullInfo = fullInfoRepository.findById(fullInfoDto.getId());
		return fullInfo.map(value -> fullInfoMapper.fullInfoToFullInfoDto(fullInfoRepository
				.save(fullInfoMapper.fullInfoDtoToFullInfo(fullInfoDto))))
				.orElseThrow(() -> new ResourceNotFoundException(Constants.FULL_INFO_NOT_FOUND, fullInfoDto.getId()));
	}

	public void deleteById(long id) {
		fullInfoRepository.deleteById(id);
	}
}
