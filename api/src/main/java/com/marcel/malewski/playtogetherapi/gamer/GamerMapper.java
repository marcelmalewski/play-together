package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerCreateRequestDto;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerResponseDto;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerUpdateRequestDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GamerMapper {
   GamerResponseDto mapToGamerResponseDto(Gamer gamer);
   List<GamerResponseDto> mapToGamerList(List<Gamer> gamers);
   Gamer mapToGamer(GamerCreateRequestDto gamerCreateRequestDto);
   Gamer mapToGamer(GamerUpdateRequestDto gamerUpdateRequestDto);
}
