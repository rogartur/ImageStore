package com.imagestore.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.imagestore.dto.ImageDTO;
import com.imagestore.model.ImageFile;

@Component
public class ImageMapper {

	public Page<ImageDTO> mapImagePagetoImageDTOPage(Page<ImageFile> imagesPage) {
		return imagesPage.map(new Converter<ImageFile, ImageDTO>() {
			@Override
			public ImageDTO convert(ImageFile entity) {
				ImageDTO dto = mapImageToImageDTO(entity);
				return dto;
			}
		});
	}

	private ImageDTO mapImageToImageDTO(ImageFile imageFile) {
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setId(imageFile.getId());
		imageDTO.setName(imageFile.getName());
		imageDTO.setDateUpload(imageFile.getDateUpload());
		imageDTO.setUserId(imageFile.getUser().getUserId());
		return imageDTO;
	}
}
