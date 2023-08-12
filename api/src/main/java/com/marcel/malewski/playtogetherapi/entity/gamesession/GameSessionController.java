package com.marcel.malewski.playtogetherapi.entity.gamesession;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "v1")
@Tag(name = "Game sessions", description = "Game sessions API")
public class GameSessionController {
}
