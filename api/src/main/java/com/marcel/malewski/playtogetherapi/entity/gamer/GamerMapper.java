package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GamerMapper {

    public GamerPrivateResponseDto toGamerPrivateResponseDto(Gamer gamer) {
        if (gamer == null) {
            return null;
        }

        List<String> platformsNames = getPlatformNames(gamer);
        List<String> gamesNames = getGamesNames(gamer);
        List<String> genresNames = getGenresNames(gamer);

        return new GamerPrivateResponseDto(
                gamer.getId(),
                gamer.getLogin(),
                gamer.getEmail(),
                gamer.getBirthdate(),
                gamer.getBio(),
                gamer.getAvatarUrl(),
                gamer.getPlayingTimeStart(),
                gamer.getPlayingTimeEnd(),
                platformsNames,
                gamesNames,
                genresNames
        );
    }

    public GamerPublicResponseDto toGamerPublicResponseDto(Gamer gamer) {
        if (gamer == null) {
            return null;
        }

        List<String> platformsNames = getPlatformNames(gamer);
        List<String> gamesNames = getGamesNames(gamer);
        List<String> genresNames = getGenresNames(gamer);
        int age = getAge(gamer);

        return new GamerPublicResponseDto(
                gamer.getId(),
                gamer.getLogin(),
                age,
                gamer.getBio(),
                gamer.getAvatarUrl(),
                gamer.getPlayingTimeStart(),
                gamer.getPlayingTimeEnd(),
                platformsNames,
                gamesNames,
                genresNames
        );
    }

    private List<String> getNames(List<?> items) {
        return items.stream().map(Object::toString).toList();
    }

    private List<String> getPlatformNames(Gamer gamer) {
        return getNames(gamer.getPlatforms());
    }

    private List<String> getGamesNames(Gamer gamer) {
        return getNames(gamer.getFavouriteGames());
    }

    private List<String> getGenresNames(Gamer gamer) {
        return getNames(gamer.getFavouriteGenres());
    }

    private int getAge(Gamer gamer) {
        LocalDate currentDay = LocalDate.now();
        return Period.between(gamer.getBirthdate(), currentDay).getYears();
    }
}
