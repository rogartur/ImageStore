package com.imagestore.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.imagestore.model.Image;
import com.imagestore.model.User;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {

	@SuppressWarnings("unchecked")
	Image save(Image image);

	void delete(Image image);

	Page<Image> findByUser(Pageable pageRequest, User user);

	Image findOne(Long id);

	Optional<Image> findOneByName(String name);

}
