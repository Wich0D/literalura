package com.wich0D.BibliotecaAPI;

import com.wich0D.BibliotecaAPI.principal.Main;
import com.wich0D.BibliotecaAPI.repository.AuthorRepository;
import com.wich0D.BibliotecaAPI.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Scanner;

@SpringBootApplication
public class BibliotecaApiApplication implements CommandLineRunner {

	@Autowired
	private Repository repository;
	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository, authorRepository);
		main.menu();
	}
}
