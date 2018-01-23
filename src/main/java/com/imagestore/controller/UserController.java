package com.imagestore.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagestore.model.Login;
import com.imagestore.model.User;
import com.imagestore.service.UserService;
import com.imagestore.util.RestMessage;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Value("${jwt.secret}")
	private String jwtSecret;

	@Autowired
	private UserService userService;

	@PostMapping(value = "login")
	public RestMessage login(@RequestBody final Login login) throws ServletException {

		User user = userService.loadUserByEmail(login.getEmail());

		if (!login.getPassword().equals(user.getPassword())) {
			throw new ServletException("Invalid password");
		}

		return new RestMessage(
				Jwts.builder().setSubject(user.getName()).claim("email", user.getEmail()).claim("name", user.getName())
				.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, jwtSecret).compact());
	}

	@PostMapping(value = "register")
	public ResponseEntity<User> register(@RequestBody final User user) throws ServletException {

		userService.save(user);

		if (user.getUserId() == null) {
			throw new ServletException("User could not be saved");
		}

		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
}
