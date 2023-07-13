package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.validation.ValidatePlayingTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

//TODO przerobic na zwykla klase i konstruktor gdzie daty to najpierw stringi
//TODO dodać jakies wieksze wymagania co do hasla
//TODO zeby nadpisac walidacje dat potrzebuje zwyklej klasy ktora przyjmuje string w konstruktorze i patrzy czy to poprawne daty i je parsuje
// chyba wtedy muzse dodac reczną walidacje wszystkiego
//TODO notnull nie pomaga tym datom trzeba ja jakos inaczej obsluzyc
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
	String birthDate,
	@Schema(example = "20:00", format = "HH:mm")
	@NotNull
	String playingTimeStart,
	@Schema(example = "22:00", format = "HH:mm")
	@NotNull
	String playingTimeEnd,
	@Size(min = 1, message = "you have to add at least one platform")
	@NotNull
	@UniqueElements(message = "must only contain unique platforms")
	List<Long> platforms
) {
	public GamerRegisterRequestDto {
//		if(birthDate == null) {
//			throw new IllegalArgumentException("Invalid birth date format. Please use the format yyyy-MM-dd.");
			throw new GamerNotFoundException(12L);
//		}
	}
}
