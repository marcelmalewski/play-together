package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPrivateResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GamerMapper {
	GamerPrivateResponseDto toGamerPrivateResponseDto(Gamer gamer);
}
