package ru.otus.pictureservice.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.pictureservice.dto.PictureMetadataDto;
import ru.otus.pictureservice.exception.PictureNotFoundException;
import ru.otus.pictureservice.model.Picture;
import ru.otus.pictureservice.repository.PictureRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

	private final PictureRepository pictureRepository;

	@HystrixCommand(commandKey = "getPictureResource", fallbackMethod = "getResourceFallback")
	@Override
	public GridFsResource getResource(String id) {
		return pictureRepository.getResource(id).orElseThrow(PictureNotFoundException::new);
	}

	private GridFsResource getResourceFallback(String id) throws IOException {
		final GridFSFile file = new GridFSFile(
				new BsonString(id),
				"book.jpg",
				61286L,
				1024,
				new Date(),
				"C69F253E6E5E5D6F4AE0B0F7BA994CD8",
				new Document("_contentType", "image/jpeg")
		);

		return new GridFsResource(file, new ClassPathResource("book.jpg").getInputStream());
	}

	@HystrixCommand(commandKey = "getPicturesByBookID", fallbackMethod = "getByBookIdFallback")
	@Override
	public Collection<PictureMetadataDto> getByBookId(String bookId) {
		return pictureRepository.findByBookId(bookId).stream()
				.map(PictureTransformer::toPictureMetadataDto)
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, Collection<PictureMetadataDto>> getAllLastUploadedByBookIds(Collection<String> bookIds) {
		final HashMap<String, Collection<PictureMetadataDto>> result = new HashMap<>(bookIds.size());

		pictureRepository.findLastUploadedByBookIds(bookIds)
				.forEach(gridFSFile -> {
					if (gridFSFile.getMetadata() != null && gridFSFile.getMetadata().containsKey("bookId")) {
						final String bookId = gridFSFile.getMetadata().get("bookId").toString();
						if (!result.containsKey(bookId)) {
							result.put(bookId, new ArrayList<>());
						}
						result.get(bookId).add(PictureTransformer.toPictureMetadataDto(gridFSFile));
					}
				});

		return result;
	}

	private Collection<PictureMetadataDto> getByBookIdFallback(String bookId) {
		return Collections.emptyList();
	}

	@HystrixCommand(commandKey = "savePicture", fallbackMethod = "saveFallback")
	@Override
	public PictureMetadataDto save(MultipartFile multipartFile, String bookId) throws IOException {
		final ObjectId id = pictureRepository.save(
				new Picture(
						multipartFile.getInputStream(),
						multipartFile.getOriginalFilename(),
						multipartFile.getContentType(),
						new Document("bookId", bookId)
				)
		);

		return new PictureMetadataDto(id.toString(), multipartFile.getOriginalFilename(), new Date().toString());
	}

	private PictureMetadataDto saveFallback(MultipartFile multipartFile, String bookId) {
		return new PictureMetadataDto(null, multipartFile.getOriginalFilename(), new Date().toString());
	}

	@HystrixCommand(commandKey = "deletePicture", fallbackMethod = "deleteFallback")
	@Override
	public void delete(String id) {
		pictureRepository.delete(id);
	}

	@HystrixCommand(commandKey = "deletePictureByBookId", fallbackMethod = "deleteFallback")
	@Override
	public void deleteByBookId(String bookId) {
		pictureRepository.deleteByBookId(bookId);
	}

	private void deleteFallback(String id) {
	}
}
