package com.imagestore.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.imagestore.dto.ImageDTO;
import com.imagestore.mapper.ImageMapper;
import com.imagestore.model.ImageFile;
import com.imagestore.model.User;
import com.imagestore.service.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController extends AbstractRestController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private ImageMapper imageMapper;

	@GetMapping(value = "list")
	public Page<ImageDTO> list(final HttpServletRequest request, final @RequestParam("page") int page,
			final @RequestParam("size") int size) throws ServletException {

		final User user = getUserFromClaims(request);

		Page<ImageFile> imagesPage = imageService.findByUserId(new PageRequest(page, size), user);

		return imageMapper.mapImagePagetoImageDTOPage(imagesPage);
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

	@GetMapping(value = "download")
	public ResponseEntity<Map<String, String>> downloadFile(final HttpServletRequest request,
			final @RequestParam("filename") String filename) throws ServletException, SQLException {

		final User user = getUserFromClaims(request);

		Map<String, String> jsonMap = new HashMap<>();
		
		Optional<ImageFile> imageFile = imageService.findByFilename(filename, user);

		if (!imageFile.isPresent()) {
			jsonMap.put("content", "Image not found");
			return new ResponseEntity<>(jsonMap, HttpStatus.NOT_FOUND);
		}

		ImageFile image = imageFile.get();
		
		getByte64FileAsJson(jsonMap, image);

		return new ResponseEntity<>(jsonMap, HttpStatus.OK);
	}

	private void getByte64FileAsJson(Map<String, String> jsonMap, ImageFile image) throws SQLException {
		Blob imageBlob = image.getFile();
		String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(imageBlob.getBytes(1, (int) imageBlob.length()));
		jsonMap.put("content", encodeImage);
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

}
