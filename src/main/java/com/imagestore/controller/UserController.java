package com.imagestore.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserService userService;

	@PostMapping(value = "login")
	public RestMessage login(@RequestBody final Login login) throws ServletException {

		User user = userService.loadUserByEmail(login.getEmail());

		if (login.getPassword().equals(user.getPassword())) {
			throw new ServletException("Invalid password");
		}

		return new RestMessage(
				Jwts.builder().setSubject(user.getName()).claim("email", user.getEmail()).claim("name", user.getName())
						.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact());
	}

}
