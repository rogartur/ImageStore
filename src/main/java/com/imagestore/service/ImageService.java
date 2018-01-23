package com.imagestore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.imagestore.model.ImageFile;
import com.imagestore.model.User;
import com.imagestore.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	public Page<ImageFile> findByUserId(Pageable pageRequest, User user) {
		return imageRepository.findByUser(pageRequest, user);
	}
	
    public Optional<ImageFile> findByFilename(String filename, User owner) {
        return imageRepository.findOneByNameAndUser(filename, owner);
    }

    public void uploadFile(ImageFile imageFile) {
    	imageRepository.save(imageFile);
    }
}
