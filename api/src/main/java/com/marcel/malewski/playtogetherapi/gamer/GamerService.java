package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerResponseDto;

import java.util.Collections;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamerService {
   private final GamerRepository gamerRepository;

   public GamerService(GamerRepository gamerRepository) {
      this.gamerRepository = gamerRepository;
   }

   public List<GamerResponseDto> getAllGamers() {
      List<Gamer> allGamers = this.gamerRepository.findAll();
      return Collections.emptyList();
   }

   public GamerResponseDto getGamer(Long id) {
      Gamer gamer = this.gamerRepository.findById(id).get();
      return null;
   }

   public GamerResponseDto createGamer() {
      //Gamer createdGamer = this.gamerRepository.save();
      return null;
   }

   public GamerResponseDto updateGamer() {
      return null;
   }

   public void deleteGamer(Long id) {
   }
}
