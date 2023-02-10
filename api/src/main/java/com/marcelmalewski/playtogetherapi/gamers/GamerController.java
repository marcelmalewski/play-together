package com.marcelmalewski.playtogetherapi.gamers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/gamers")
public class GamerController {
   @GetMapping
   @Operation(summary = "Get all gamers")
   public ResponseEntity<String> getAllGamers() {
      String result = "All gamers";
      return new ResponseEntity<>(result, HttpStatus.OK);
   }
}
