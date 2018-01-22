package com.imagestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.imagestore.model.Image;
import com.imagestore.model.User;
import com.imagestore.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	public Page<Image> findByUserId(Pageable pageRequest, User user) {
		return imageRepository.findByUser(pageRequest, user);
	}
}
