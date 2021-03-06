package com.imagestore.service;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imagestore.model.User;
import com.imagestore.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User loadUserByEmail(String email) throws ServletException {
		User user = userRepository.findByEmail(email);
		if (user == null) throw new ServletException("Could not find user with email: " + email);
		return user;
	}
	
	public User findByEmail(String email) throws ServletException {
		return userRepository.findByEmail(email);
	}


	public User save(User user) throws ServletException {
		checkEmailNotInUse(user.getEmail());
		return userRepository.save(user);
	}

	public User update(User user) {
		return userRepository.saveAndFlush(user);
	}

	private void checkEmailNotInUse(String email) throws ServletException {
		User user = userRepository.findByEmail(email);
		if (user != null) throw new ServletException("Found already existing user with email: " + email);
	}
}
