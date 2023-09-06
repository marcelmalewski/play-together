package com.marcel.malewski.playtogetherapi.security.register;

import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidateDateFormat;
import com.marcel.malewski.playtogetherapi.validation.minage.ValidateMinAge;
import com.marcel.malewski.playtogetherapi.validation.playingtime.ValidatePlayingTime;
import com.marcel.malewski.playtogetherapi.validation.timeformat.ValidateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

import static com.marcel.malewski.playtogetherapi.constants.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.constants.DateConstants.TIME_FORMAT;
import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidation.LOGIN_MAX_SIZE;
import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidation.LOGIN_MIN_SIZE;

//TODO dodać większe wymagania hasła
@ValidatePlayingTime
public record GamerRegisterRequestDto(
	@Size(min = LOGIN_MIN_SIZE, max = LOGIN_MAX_SIZE)
	@NotBlank
	String login,
	@Size(min = 8, max = 20)
	@NotBlank
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
	@UniqueElements(message = "must only contain unique platformsIds")
	@NotNull
	List<Long> platformsIds
) {
}
