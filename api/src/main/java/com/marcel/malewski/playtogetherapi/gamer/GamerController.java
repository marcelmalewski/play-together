package com.marcel.malewski.playtogetherapi.gamer;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class GamerController {
   @GetMapping
   @Operation(summary = "Get all gamers")
   public ResponseEntity<String> getAllGamers() {
      String result = "All gamers22222";
      return new ResponseEntity<>(result, HttpStatus.OK);
   }
}
