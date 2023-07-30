package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
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
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));
		return gamerMapper.toGamerPublicResponseDto(gamer);
	}

	public GamerPrivateResponseDto getGamerPrivateInfo(Long id) {
		Gamer gamer = gamerRepository.findById(id).orElseThrow(() -> new GamerNotFoundException(id));
		return gamerMapper.toGamerPrivateResponseDto(gamer);
	}
}
