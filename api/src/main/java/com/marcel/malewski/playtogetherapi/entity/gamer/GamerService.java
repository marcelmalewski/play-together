package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamerService {
	private final GamerRepository gamerRepository;
	private final GamerMapper gamerMapper;

	public GamerService(GamerRepository gamerRepository, GamerMapper gamerMapper) {
		this.gamerRepository = gamerRepository;
		this.gamerMapper = gamerMapper;
	}

	public List<GamerPublicResponseDto> findAllGamers() {
		return gamerRepository.findAll().stream().map(gamerMapper::toGamerPublicResponseDto).toList();
	}

	public GamerPublicResponseDto getGamerPublicInfo(long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));
		return gamerMapper.toGamerPublicResponseDto(gamer);
	}

	public GamerPrivateResponseDto getGamerPrivateInfo(long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));
		return gamerMapper.toGamerPrivateResponseDto(gamer);
	}

	public GamerPrivateResponseDto updateGamerProfile(@NotNull GamerUpdateProfileRequestDto updateProfileDto, long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));
		return null;
	}

	public GamerPrivateResponseDto updateGamerAuth(@NotNull GamerUpdateAuthRequestDto updateAuthDto, long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));
		return null;
	}

}
