package com.hexaware.service;

import org.springframework.stereotype.Service;

import com.hexaware.dto.UsersDTO;
import com.hexaware.exception.UserNameAlreadyExistsException;
import com.hexaware.exception.UserNameNotFoundException;


@Service
public interface UsersService {
	
	UsersDTO signUp(UsersDTO userDto) throws UserNameAlreadyExistsException;
	UsersDTO updatePassword(UsersDTO userDto) throws UserNameNotFoundException;
}
