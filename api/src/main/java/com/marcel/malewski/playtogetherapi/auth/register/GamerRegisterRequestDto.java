package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidateDateFormat;
import com.marcel.malewski.playtogetherapi.validation.pastorpresent.ValidateStringPastOrPresent;
import com.marcel.malewski.playtogetherapi.validation.playingtime.ValidatePlayingTime;
import com.marcel.malewski.playtogetherapi.validation.timeformat.ValidateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

import static com.marcel.malewski.playtogetherapi.consts.DateUtils.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.consts.DateUtils.TIME_FORMAT;

//TODO dodać jakies wieksze wymagania co do hasla api/front
//TODO dodać wymagania co do wieku api/front i wtedy data startowa na front to nie today tylko ten minmalny wiek
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
	@ValidateStringPastOrPresent
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
	List<Long> platforms
) {
}
