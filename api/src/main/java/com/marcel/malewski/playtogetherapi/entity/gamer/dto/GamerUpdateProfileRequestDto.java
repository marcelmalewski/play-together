package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidDateFormat;
import com.marcel.malewski.playtogetherapi.validation.minage.ValidMinAge;
import com.marcel.malewski.playtogetherapi.validation.timeformat.ValidTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.constants.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.constants.DateConstants.TIME_FORMAT;
import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidation.*;

//TODO bio i avatarUrl może być nullem, ale nie może być pustym stringiem
public record GamerUpdateProfileRequestDto(
	@Size(min = LOGIN_MIN_SIZE, max = LOGIN_MAX_SIZE)
	@NotBlank
	String login,
	@Schema(example = DATE_EXAMPLE, format = DATE_FORMAT)
	@ValidDateFormat
	@ValidMinAge
	@NotNull
	LocalDate birthdate,
	String bio,
	String avatarUrl,
	@Schema(example = TIME_START_EXAMPLE, format = TIME_FORMAT)
	@ValidTimeFormat
	@NotNull
	LocalTime playingTimeStart,
	@Schema(example = TIME_END_EXAMPLE, format = TIME_FORMAT)
	@ValidTimeFormat
	@NotNull
	LocalTime playingTimeEnd,
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
