package com.marcel.malewski.playtogetherapi.entity.gamesession.dto;

import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.PrivacyLevel;
import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidDateFormat;
import com.marcel.malewski.playtogetherapi.validation.enums.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidationConstants.DATE_EXAMPLE;
import static com.marcel.malewski.playtogetherapi.validation.ValidationConstants.AT_LEAST_ONE_PLATFORM_ID;
import static com.marcel.malewski.playtogetherapi.validation.ValidationConstants.UNIQUE_ELEMENTS_MESSAGE;

public record GameSessionCreateOrUpdateRequestDto(
	@Size(min = 3, max = 20)
	@NotBlank
	String name,
	@ValidEnum(enumClass = PrivacyLevel.class)
	String visibilityTypeAsString,
	@NotNull
	Boolean isCompetitive,
	@ValidEnum(enumClass = PrivacyLevel.class)
	String accessTypeAsString,

//	@FutureOrPresent //TODO dodać dla stringa
	@Schema(example = DATE_EXAMPLE, format = DATE_FORMAT)
	@ValidDateFormat
	@NotNull
	String dateAsString,
	@Min(value = 1)
	@Max(value = 1000)
	@NotNull
	Integer maxMembers,//TODO handle not valid int exception
	@Min(value = 1)
	@Max(value = 200)
	@NotNull
	Integer minAge,//TODO handle not valid int exception
	String description,
	@NotNull
	Long gameId,//TODO handle not valid long exception
	@Size(min = 1, message = AT_LEAST_ONE_PLATFORM_ID)
	@UniqueElements(message = UNIQUE_ELEMENTS_MESSAGE + "platforms ids")
	@NotNull
	List<Long> platformsIds
) {
}
