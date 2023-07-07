package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPublicResponseDto;
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

	public GamerPublicResponseDto getGamerPublicInfo(Long id) {
		return gamerMapper.toGamerPublicResponseDto(gamerRepository.findById(id).get());
	}

	public GamerPrivateResponseDto getGamerPrivateInfo(Long id) {
		return gamerMapper.toGamerPrivateResponseDto(gamerRepository.findById(id).get());
	}
}
