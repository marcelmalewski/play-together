package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import com.marcel.malewski.playtogetherapi.interfaces.EntityWithPlayingTimeAsString;
import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidDateFormat;
import com.marcel.malewski.playtogetherapi.validation.minage.ValidMinAge;
import com.marcel.malewski.playtogetherapi.validation.notblankifexist.TrimmedSize;
import com.marcel.malewski.playtogetherapi.validation.playingtime.ValidPlayingTime;
import com.marcel.malewski.playtogetherapi.validation.timeformat.ValidTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.constant.DateConstants.TIME_FORMAT;
import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidationConstants.*;
import static com.marcel.malewski.playtogetherapi.validation.ValidationConstants.AT_LEAST_ONE_PLATFORM;
import static com.marcel.malewski.playtogetherapi.validation.ValidationConstants.UNIQUE_ELEMENTS_MESSAGE;

@ValidPlayingTime
public record GamerUpdateProfileRequestDto(
	@Size(min = LOGIN_MIN_SIZE, max = LOGIN_MAX_SIZE)
	@NotBlank
	String login,
	@Schema(example = DATE_EXAMPLE, format = DATE_FORMAT)
	@ValidDateFormat
	@ValidMinAge
	@NotNull
	String birthdateAsString,
	@TrimmedSize(min = 3, max = 500)
	String bio,
	@TrimmedSize //TODO dodać jakąś lepszą walidacje url
	String avatarUrl,
	@Schema(example = TIME_START_EXAMPLE, format = TIME_FORMAT)
	@ValidTimeFormat
	@NotNull
	String playingTimeStartAsString,
	@Schema(example = TIME_END_EXAMPLE, format = TIME_FORMAT)
	@ValidTimeFormat
	@NotNull
	String playingTimeEndAsString,
	@Size(min = 1, message = AT_LEAST_ONE_PLATFORM)
	@UniqueElements(message = UNIQUE_ELEMENTS_MESSAGE + "platforms ids")
	@NotNull
	List<Long> platformsIds,
	@UniqueElements(message = UNIQUE_ELEMENTS_MESSAGE + "favourite games ids")
	@NotNull
	List<Long> favouriteGamesIds,
	@UniqueElements(message = UNIQUE_ELEMENTS_MESSAGE + "favourite genres ids")
	@NotNull
	List<Integer> favouriteGenresIds
) implements EntityWithPlayingTimeAsString {
	@Override
	public String getPlayingTimeStartAsString() {
		return playingTimeStartAsString;
	}

	@Override
	public String getPlayingTimeEndAsString() {
		return playingTimeEndAsString;
	}
}
