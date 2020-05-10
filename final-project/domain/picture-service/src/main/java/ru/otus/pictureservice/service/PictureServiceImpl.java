package ru.otus.pictureservice.service;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.pictureservice.dto.PictureMetadataDto;
import ru.otus.pictureservice.exception.PictureNotFoundException;
import ru.otus.pictureservice.repository.PictureRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

	private final PictureRepository pictureRepository;

	@Override
	public GridFsResource getResource(String id) {
		return pictureRepository.getResource(id).orElseThrow(PictureNotFoundException::new);
	}

	@Override
	public Collection<PictureMetadataDto> getByBookId(String bookId) {
		return pictureRepository.findByBookId(bookId).stream()
				.map(picture -> new PictureMetadataDto(
						picture.getId().asObjectId().getValue().toHexString(),
						picture.getFilename(),
						picture.getUploadDate().toString()
				))
				.collect(Collectors.toList());
	}

	@Override
	public PictureMetadataDto save(MultipartFile multipartFile, String bookId) throws IOException {
		final ObjectId id = pictureRepository.store(
				multipartFile.getInputStream(),
				multipartFile.getOriginalFilename(),
				multipartFile.getContentType(),
				new Document("bookId", bookId)
		);

		return new PictureMetadataDto(id.toString(), multipartFile.getOriginalFilename(), new Date().toString());
	}

	@Override
	public void delete(String id) {
		pictureRepository.delete(id);
	}
}
