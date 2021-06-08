package com.lopezsoto.learningwithmykids;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LearningWithMyKidsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningWithMyKidsApplication.class, args);
	}

	/**
	 * Utility to map Entity objects to model objects.
	 * @return Model Mapper instance
	 */
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}

}
