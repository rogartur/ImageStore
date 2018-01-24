package com.imagestore.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.imagestore.model.User;
import com.imagestore.service.UserService;

import io.jsonwebtoken.Claims;

@Controller
public class AbstractRestController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;

	protected User getUserFromClaims(final HttpServletRequest request) throws ServletException {
		final Claims claims = (Claims) request.getAttribute("claims");

		final User user = userService.loadUserByEmail(claims.get("email", String.class));

		if (user == null) {
			log.error("Error in getUserFromClaims(), user not found");
			throw new ServletException("Invalid claims");
		}
		return user;
	}
}
