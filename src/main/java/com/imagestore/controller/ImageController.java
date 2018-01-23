package com.imagestore.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.imagestore.model.ImageFile;
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
	public Page<ImageFile> list(final HttpServletRequest request, final @RequestParam("page") int page,
			final @RequestParam("size") int size) throws ServletException {

		final User user = getUserFromClaims(request);

		return imageService.findByUserId(new PageRequest(page, size), user);
	}

	@PostMapping(value = "/upload")
	public ResponseEntity<?> uploadFile(final MultipartHttpServletRequest request) throws ServletException {

		final User user = getUserFromClaims(request);

		try {
			Iterator<String> itr = request.getFileNames();

			while (itr.hasNext()) {
				ImageFile newFile = getFile(request, user, itr);
				imageService.uploadFile(newFile);
			}

		} catch (Exception e) {
			return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("{}", HttpStatus.OK);
	}

	@GetMapping(value = "/download")
	public ResponseEntity<?> downloadFile(final HttpServletRequest request,
			final @RequestParam("filename") String filename) throws ServletException {

		final User user = getUserFromClaims(request);

		Optional<ImageFile> imageFile = imageService.findByFilename(filename, user);

		if (!imageFile.isPresent()) {
			return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
		}

		HttpHeaders headers;
		ImageFile image = imageFile.get();
		try {
			headers = createHeadersForImage(image);
		} catch (IndexOutOfBoundsException | NullPointerException ex) {
			return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
	}

	private HttpHeaders createHeadersForImage(ImageFile image) throws IndexOutOfBoundsException, NullPointerException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "attachment; filename=" + image.getName());

		String primaryType, subType;
		primaryType = image.getMimeType().split("/")[0];
		subType = image.getMimeType().split("/")[1];

		headers.setContentType(new MediaType(primaryType, subType));
		return headers;
	}

	private ImageFile getFile(final MultipartHttpServletRequest request, final User user, Iterator<String> itr)
			throws IOException, SerialException, SQLException {

		String uploadedFile = itr.next();
		MultipartFile file = request.getFile(uploadedFile);
		String name = file.getOriginalFilename();
		String mimeType = file.getContentType();
		Blob bytes = new javax.sql.rowset.serial.SerialBlob(file.getBytes());

		ImageFile newFile = new ImageFile(name, user, bytes, mimeType);
		newFile.setDateUpload(new Date());
		return newFile;
	}

	private User getUserFromClaims(final HttpServletRequest request) throws ServletException {
		final Claims claims = (Claims) request.getAttribute("claims");

		final User user = userService.loadUserByEmail(claims.get("email", String.class));

		if (user == null) {
			throw new ServletException("Invalid claims");
		}
		return user;
	}

}
