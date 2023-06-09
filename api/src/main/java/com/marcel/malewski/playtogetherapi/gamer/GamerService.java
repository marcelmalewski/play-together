package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPrivateResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO map toList vs collect
@Service
public class GamerService {
	private final GamerRepository gamerRepository;
	private final GamerMapper gamerMapper;

	public GamerService(GamerRepository gamerRepository, GamerMapper gamerMapper) {
		this.gamerRepository = gamerRepository;
		this.gamerMapper = gamerMapper;
	}

	public List<GamerPrivateResponseDto> findAllGamers() {
		return gamerRepository.findAll().stream().map(gamerMapper::toGamerPrivateResponseDto).toList();
	}

	public GamerPrivateResponseDto getGamer(Long id) {
		return gamerMapper.toGamerPrivateResponseDto(gamerRepository.findById(id).get());
	}
}
