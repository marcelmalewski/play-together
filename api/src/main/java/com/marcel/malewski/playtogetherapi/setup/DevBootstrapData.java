package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.gamesession.GameSessionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DevBootstrapData implements CommandLineRunner {
	private final GamerRepository gamerRepository;
	private final GameSessionRepository gameSessionRepository;

	public DevBootstrapData(GamerRepository gamerRepository, GameSessionRepository gameSessionRepository) {
		this.gamerRepository = gamerRepository;
		this.gameSessionRepository = gameSessionRepository;
	}

	@Override
	public void run(String... args) {
		//Test Gamers
		Gamer john = new Gamer();
		john.setLogin("john");
		john.setPassword("john");

//		gamerRepository.save(john);
//
//		Gamer eric = new Gamer();
//		eric.setLogin("eric");
//		eric.setPassword("eric");
//
//		//Test Game sessions
//		GameSession gameSession1 = new GameSession();
//		gameSession1.setName("game session");
//
//		GameSession gameSession2 = new GameSession();
//		gameSession2.setName("game session 2");
//
//		gamerRepository.save(john);
//		gameSessionRepository.save(gameSession1);
//
//		gamerRepository.save(eric);
//		gameSessionRepository.save(gameSession2);

//		Gamer johnSaved = gamerRepository.save(john);
//		GameSession gameSession1Saved = gameSessionRepository.save(gameSession1);
//
//		Gamer ericSaved = gamerRepository.save(eric);
//		GameSession gameSession2Saved = gameSessionRepository.save(gameSession2);
//		gameSession1Saved.getGamers().add(johnSaved);
//		gameSession2Saved.getGamers().add(ericSaved);
//		gameSessionRepository.save(gameSession1Saved);
//		gameSessionRepository.save(gameSession2Saved);
	}
}
