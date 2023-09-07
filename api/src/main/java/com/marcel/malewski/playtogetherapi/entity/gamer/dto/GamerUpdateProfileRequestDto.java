package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidDateFormat;
import com.marcel.malewski.playtogetherapi.validation.minage.ValidMinAge;
import com.marcel.malewski.playtogetherapi.validation.notblankifexist.NotBlankIfExist;
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
import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidation.*;

@ValidPlayingTime
public record GamerUpdateProfileRequestDto(
	@Size(min = LOGIN_MIN_SIZE, max = LOGIN_MAX_SIZE)
	@NotBlank
	String login,
	@Schema(example = DATE_EXAMPLE, format = DATE_FORMAT)
	@ValidDateFormat
	@ValidMinAge
	@NotNull
	String birthdate,
	@Size(min = 3, max = 500)
	@NotBlankIfExist
	String bio,
	@NotBlankIfExist //TODO dodać jakąś lepszą walidacje
	String avatarUrl,
	@Schema(example = TIME_START_EXAMPLE, format = TIME_FORMAT)
	@ValidTimeFormat
	@NotNull
	String playingTimeStart,
	@Schema(example = TIME_END_EXAMPLE, format = TIME_FORMAT)
	@ValidTimeFormat
	@NotNull
	String playingTimeEnd,
	@Size(min = 1, message = "you have to add at least one platform")
	@UniqueElements(message = "must only contain unique platformsIds")
	@NotNull
	List<Long> platformsIds,
	@UniqueElements(message = "must only contain unique platformsIds")
	@NotNull
	List<Long> favouriteGamesIds,
	@UniqueElements(message = "must only contain unique platformsIds")
	@NotNull
	List<Long> favouriteGenresIds
) {
}
