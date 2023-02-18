package com.marcel.malewski.playtogetherapi.gamer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/gamers")
@Tag(name = "Meetings", description = "Meetings API")
public class GamerController {
   @GetMapping
   @Operation(summary = "Get all gamers")
   public ResponseEntity<String> getAllGamers() {
      String result = "All gamers3333";
      return new ResponseEntity<>(result, HttpStatus.OK);
   }

   @GetMapping("/{id}")
   @Operation(summary = "Get gamer by id")
   public ResponseEntity<String> getGamer(@PathVariable String id) {
      String result = "gamer13213";
      return new ResponseEntity<>(result, HttpStatus.OK);
   }

   @PostMapping
   @Operation(summary = "Create new gamer")
   public ResponseEntity<String> createGamer() {
      String result = "gamer";
      return new ResponseEntity<>(result, HttpStatus.OK);
   }

   @PutMapping
   @Operation(summary = "Update gamer")
   public ResponseEntity<String> updateGamer() {
      String result = "gamer1";
      return new ResponseEntity<>(result, HttpStatus.OK);
   }

   @PatchMapping
   @Operation(summary = "Update gamer")
   public ResponseEntity<String> partiallyUpdateGamer() {
      String result = "gamer2";
      return new ResponseEntity<>(result, HttpStatus.OK);
   }
}
