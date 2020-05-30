package ru.otus.pictureservice.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import ru.otus.pictureservice.dto.PictureMetadataDto;

public class PictureTransformer {

	static PictureMetadataDto toPictureMetadataDto(GridFSFile gridFSFile) {
		return new PictureMetadataDto(
				gridFSFile.getId().asObjectId().getValue().toHexString(),
				gridFSFile.getFilename(),
				gridFSFile.getUploadDate().toString()
		);
	}
}
