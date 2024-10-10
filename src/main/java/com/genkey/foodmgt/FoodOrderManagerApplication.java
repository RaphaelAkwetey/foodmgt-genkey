package com.genkey.foodmgt;



import com.genkey.foodmgt.util.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class FoodOrderManagerApplication implements CommandLineRunner {
	@Autowired
	Converter converter;

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderManagerApplication.class, args);


	}


	@Override
	public void run(String... args) throws Exception {
		converter.deleteBothFolderAndFile();
		converter.init();
	}
}
