package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.validation.ValidatePlayingTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

//		DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
//			try {
//			dateFormatter.parse(birthDate);
//			} catch (DateTimeParseException e) {
//			throw new FieldWithWrongDateFormatException("yes", "yes");
//			}

//TODO dodać jakies wieksze wymagania co do hasla
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
//	@PastOrPresent
	@Schema(example = "2000-02-02", format = "yyyy-MM-dd")
//	@ValidatePastPresentString
	String birthDate,
	@Schema(example = "20:00", format = "HH:mm")
//	@ValidateFormat
	String playingTimeStart,
	@Schema(example = "22:00", format = "HH:mm")
	String playingTimeEnd,
	@Size(min = 1, message = "you have to add at least one platform")
	@NotNull
	@UniqueElements(message = "must only contain unique platforms")
	List<Long> platforms
) {
}
