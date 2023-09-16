package com.marcel.malewski.playtogetherapi.entity.gamesession.dto;

import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.PrivacyLevel;
import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidDateFormat;
import com.marcel.malewski.playtogetherapi.validation.enums.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.List;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidationConstants.DATE_EXAMPLE;

public record GameSessionCreateOrUpdateRequestDto(
	@Size(min = 3, max = 20)
	@NotBlank
	String name,
	@ValidEnum(enumClass = PrivacyLevel.class)
	String visibilityTypeAsString,
	boolean isCompetitive, //TODO co jak dam null?
	@ValidEnum(enumClass = PrivacyLevel.class)
	String accessTypeAsString,

//	@FutureOrPresent
	@Schema(example = DATE_EXAMPLE, format = DATE_FORMAT)
	@ValidDateFormat
	@NotNull
	String dateAsString,
	@Min(value = 1)
	@Max(value = 1000)
	int maxMembers,
	@Min(value = 1)
	@Max(value = 200)
	int minAge,
	String description,
	long gameId,
	List<Long> platformsIds
) {
}
