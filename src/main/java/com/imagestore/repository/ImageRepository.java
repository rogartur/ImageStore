package com.imagestore.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.imagestore.model.ImageFile;
import com.imagestore.model.User;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<ImageFile, Long> {

	@SuppressWarnings("unchecked")
	ImageFile save(ImageFile image);

	void delete(ImageFile image);

	Page<ImageFile> findByUser(Pageable pageRequest, User user);

	ImageFile findOne(Long id);

	Optional<ImageFile> findOneByName(String name);
	
	Optional<ImageFile> findOneByNameAndUser(String name, User user);

}
