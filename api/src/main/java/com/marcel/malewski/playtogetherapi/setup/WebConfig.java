package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.entity.gamesession.converter.StringToGameSessionSortOption;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToGameSessionSortOption());
	}
}
