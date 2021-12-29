package com.dddd.dpr_2.general.services;

import com.dddd.dpr_2.database.models.Status;
import com.dddd.dpr_2.database.models.User;
import com.dddd.dpr_2.database.repository.StatusRepository;
import com.dddd.dpr_2.database.repository.UserRepository;
import com.dddd.dpr_2.general.constants.Constants;
import com.dddd.dpr_2.general.exceptions.ResourceNotFoundException;
import com.dddd.dpr_2.web.dto.UserDto;
import com.dddd.dpr_2.web.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl {

	private final UserMapper userMapper;

	private final UserRepository userRepository;


	private final StatusRepository statusRepository;

	@Autowired
	public UserServiceImpl(UserMapper userMapper,
						   UserRepository userRepository,
						   StatusRepository statusRepository) {
		this.userMapper = userMapper;
		this.userRepository = userRepository;
		this.statusRepository = statusRepository;
	}

	public ResponseEntity<Map<String, Object>> findAllFiltered(boolean blocked, String email, int page, int size) {
		Page<User> users = Page.empty();
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));

		Optional<Status> statusBlocked = statusRepository.findById(0L);
		if (blocked) {
			if (statusBlocked.isPresent()) {
				users = userRepository.findAll(pageRequest);
			}
		} else {
			if (statusBlocked.isPresent()) {
				users = userRepository.findAll(pageRequest);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("data", userMapper.userListToUserDtoList(users.getContent()));
		response.put("currentPage", users.getNumber());
		response.put("pageSize", users.getSize());
		response.put("totalItems", users.getTotalElements());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public List<UserDto> findAll() {
		return userMapper.userListToUserDtoList(userRepository.findAll());
	}

	public UserDto findById(long id) {
		return userRepository.findById(id).map(userMapper::userToUserDto)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND, id));
	}

	public UserDto create(UserDto userDto) {
		return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDto)));
	}

	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

}
