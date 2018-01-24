package com.imagestore.controller;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class UserController extends AbstractRestController {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Autowired
	private UserService userService;

	@PostMapping(value = "authorize")
	public ResponseEntity<RestMessage> authorize(@RequestBody final Login login) throws ServletException {

		return new ResponseEntity<>(new RestMessage(Jwts.builder().setSubject(login.getEmail())
				.claim("email", login.getEmail()).claim("password", login.getPassword()).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, jwtSecret).compact()), HttpStatus.OK);
	}

	@PostMapping(value = "register")
	public ResponseEntity<User> register(@RequestBody final User user) throws ServletException {

		userService.save(user);

		if (user.getUserId() == null) {
			throw new ServletException("User could not be saved");
		}

		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "update")
	public ResponseEntity<?> update(final HttpServletRequest request, @RequestBody final User user) throws ServletException {
		
		User existingDB = userService.findByEmail(user.getEmail());
		User currentUser = getUserFromClaims(request);
		User currentUserDB = userService.loadUserByEmail(currentUser.getEmail());
		
		if(existingDB != null && !currentUser.getEmail().equals(existingDB.getEmail()))
			return new ResponseEntity<>(new RestMessage("User with that email address is already existing"), HttpStatus.CONFLICT);

		currentUserDB.setEmail(user.getEmail());
		currentUserDB.setName(user.getName());
		currentUserDB.setPassword(user.getPassword());
		userService.update(currentUserDB);

		return new ResponseEntity<>(currentUserDB, HttpStatus.OK);
	}

	@GetMapping(value = "get/{email}")
	public ResponseEntity<User> getUser(@PathVariable final String email) throws ServletException {

		User user = userService.loadUserByEmail(email);

		if (user == null) {
			throw new ServletException("User could not be updated");
		}

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
