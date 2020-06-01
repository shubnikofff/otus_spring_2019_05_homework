package ru.otus.userregistry.service;

import ru.otus.userregistry.dto.ProfileDto;
import ru.otus.userregistry.model.Profile;

public class ProfileTransformer {

	public static ProfileDto toDto(Profile profile) {
		return new ProfileDto(
				profile.getFirstName(),
				profile.getLastName(),
				profile.getEmail()
		);
	}
}
