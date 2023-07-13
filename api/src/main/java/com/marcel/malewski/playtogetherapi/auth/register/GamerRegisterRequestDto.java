package com.marcel.malewski.playtogetherapi.auth.register;

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
//	@NotNull
	@Schema(name = "birthDate", example = "2000-02-02", format = "YYYY-MM-DD")
	@NotNull
	String birthDate,
	String playingTimeStart,
	String playingTimeEnd,
	@Size(min = 1, message = "you have to add at least one platform")
	@NotNull
	@UniqueElements(message = "must only contain unique platforms")
	List<Long> platforms
) {
}
//@Getter
//@Setter
//@ValidatePlayingTime
//public class GamerRegisterRequestDto {
//	String login;
//	String password;
//	@Email
//	@NotNull
//	String email;
//	@PastOrPresent
//	LocalDate birthDate;
//	LocalTime playingTimeStart;
//	LocalTime playingTimeEnd;
//	@Size(min = 1, message = "you have to add at least one platform")
//	@NotNull
//	@UniqueElements(message = "must only contain unique platforms")
//	List<Long> platforms;
//
//	//TODO validator size nie dziala
//	public GamerRegisterRequestDto(
//		@Size(min = 3, max = 20)
//		@NotNull
//		String login,
//		@Size(min = 8, max = 20)
//		@NotNull
//		String password,
//		@NotNull
//		String email,
//		String birthDate,
//		LocalTime playingTimeStart,
//		LocalTime playingTimeEnd,
//		@NotNull
//		List<Long> platforms) {
//
////		boolean yes = false;
////		if(yes == false) throw new IllegalArgumentException("Invalid birth date format. Please use the format yyyy-MM-dd.");
//
//		this.login = login;
//		this.password = password;
//		this.email = email;
//		this.birthDate = LocalDate.now();
//		this.playingTimeStart = playingTimeStart;
//		this.playingTimeEnd = playingTimeEnd;
//		this.platforms = platforms;
//	}
//}
