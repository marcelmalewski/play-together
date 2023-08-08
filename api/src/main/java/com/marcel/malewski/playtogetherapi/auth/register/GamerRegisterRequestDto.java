package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidateDateFormat;
import com.marcel.malewski.playtogetherapi.validation.minage.ValidateMinAge;
import com.marcel.malewski.playtogetherapi.validation.playingtime.ValidatePlayingTime;
import com.marcel.malewski.playtogetherapi.validation.timeformat.ValidateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

import static com.marcel.malewski.playtogetherapi.constants.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.constants.DateConstants.TIME_FORMAT;

//TODO dodać większe wymagania hasła
//TODO czy spacje przejdą przez min?
@ValidatePlayingTime
public record GamerRegisterRequestDto(
	@Size(min = 3, max = 20)
	@NotNull
	String login,
	@Size(min = 8, max = 20)
	@NotNull
	String password,
	@Email
	@NotNull
	String email,
	@Schema(example = "2000-02-02", format = DATE_FORMAT)
	@ValidateDateFormat
	@ValidateMinAge
	@NotNull
	String birthdate,
	@Schema(example = "20:00", format = TIME_FORMAT)
	@ValidateTimeFormat
	@NotNull
	String playingTimeStart,
	@Schema(example = "22:00", format = TIME_FORMAT)
	@ValidateTimeFormat
	@NotNull
	String playingTimeEnd,
	@Size(min = 1, message = "you have to add at least one platform")
	@UniqueElements(message = "must only contain unique platforms")
	@NotNull
	List<Long> platformsIds
) {
}
