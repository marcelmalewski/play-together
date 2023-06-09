package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Profile("dev")
@Component
public class SetupDatabase implements CommandLineRunner {
	private final GamerRepository gamerRepository;

	public SetupDatabase(GamerRepository gamerRepository) {
		this.gamerRepository = gamerRepository;
	}

	@Override
	public void run(String... args) {
		if(!gamerRepository.existsByLogin("admin")) {
			Gamer admin = Ga
		}
	}
}
