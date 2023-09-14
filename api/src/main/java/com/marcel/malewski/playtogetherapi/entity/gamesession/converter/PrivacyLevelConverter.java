package com.marcel.malewski.playtogetherapi.entity.gamesession.converter;

import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.PrivacyLevel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PrivacyLevelConverter implements AttributeConverter<PrivacyLevel, String> {
	@Override
	public String convertToDatabaseColumn(PrivacyLevel privacyLevel) {
		if(privacyLevel == null) {
			return null;
		}
		return privacyLevel.name();
	}

	@Override
	public PrivacyLevel convertToEntityAttribute(String name) {
		if(name == null) {
			return null;
		}
		return Stream.of(PrivacyLevel.values())
			.filter(privacyLevel -> privacyLevel.name().equals(name))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
