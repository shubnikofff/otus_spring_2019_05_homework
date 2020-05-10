package ru.otus.pictureservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PictureMetadataDto {

	private String id;

	private String name;

	private String uploadDate;
}
