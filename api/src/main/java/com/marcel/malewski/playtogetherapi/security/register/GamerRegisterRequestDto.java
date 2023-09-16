package com.marcel.malewski.playtogetherapi.security.register;

import com.marcel.malewski.playtogetherapi.interfaces.EntityWithPlayingTimeAsString;
import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidDateFormat;
import com.marcel.malewski.playtogetherapi.validation.minage.ValidMinAge;
import com.marcel.malewski.playtogetherapi.validation.playingtime.ValidPlayingTime;
import com.marcel.malewski.playtogetherapi.validation.timeformat.ValidTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.constant.DateConstants.TIME_FORMAT;
import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidationConstants.*;

@ValidPlayingTime
public record GamerRegisterRequestDto(
	@Size(min = LOGIN_MIN_SIZE, max = LOGIN_MAX_SIZE)
	@NotBlank
	String login,
	//TODO dodać większe wymagania hasła
	@Size(min = 8, max = 30)
	@NotBlank
	String password,
	@Email
	@NotNull
	String email,
	@Schema(example = DATE_EXAMPLE, format = DATE_FORMAT)
	@ValidDateFormat
	@ValidMinAge
	@NotNull
	String birthdateAsString,
	@Schema(example = TIME_START_EXAMPLE, format = TIME_FORMAT)
	@ValidTimeFormat
	@NotNull
	String playingTimeStartAsString,
	@Schema(example = TIME_END_EXAMPLE, format = TIME_FORMAT)
	@ValidTimeFormat
	@NotNull
	String playingTimeEndAsString,
	@Size(min = 1, message = "must contain at least one platform")
	@UniqueElements(message = "must only contain unique platformsIds")
	@NotNull
	List<Long> platformsIds
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
