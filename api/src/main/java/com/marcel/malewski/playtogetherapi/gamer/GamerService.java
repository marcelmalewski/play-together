package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamerService {
   private final GamerRepository gamerRepository;
   private final GamerMapper gamerMapper;

   @Autowired
   public GamerService(GamerRepository gamerRepository,
                       GamerMapper gamerMapper) {
      this.gamerRepository = gamerRepository;
      this.gamerMapper = gamerMapper;
   }

   public List<GamerResponseDto> getAllGamers() {
      List<Gamer> allGamers = this.gamerRepository.findAll();
      return gamerMapper.mapToGamerList(allGamers);
   }

   public GamerResponseDto getGamer(Long id) {
      Gamer gamer = this.gamerRepository.findById(id).get();
      return this.gamerMapper.mapToGamerResponseDto(gamer);
   }

   public GamerResponseDto createGamer() {
//      Gamer createdGamer = this.gamerRepository.save();
//      return this.gamerMapper.gamerToGamerResponseDto(createdGamer);
      return null;
   }

   public GamerResponseDto updateGamer() {
      //uzyc .update ?
//      Gamer updatedGamer = this.gamerRepository.save();
//      return this.gamerMapper.gamerToGamerResponseDto(updatedGamer);
      return null;
   }

   public void deleteGamer(Long id) {
   }
}
