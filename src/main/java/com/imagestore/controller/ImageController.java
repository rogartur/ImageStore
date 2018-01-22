package com.imagestore.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagestore.model.Image;
import com.imagestore.model.User;
import com.imagestore.service.ImageService;
import com.imagestore.service.UserService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private UserService userService;

	@GetMapping(value = "list")
	public Page<Image> list(final HttpServletRequest request, Pageable pageRequest) throws ServletException {

		final Claims claims = (Claims) request.getAttribute("claims");

		final User user = userService.loadUserByEmail(claims.get("email", String.class));

		if (user == null) {
			throw new ServletException("Invalid claims");
		}

		return imageService.findByUserId(pageRequest, user);
	}

}
