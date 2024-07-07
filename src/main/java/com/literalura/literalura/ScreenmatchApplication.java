package com.literalura.literalura;

import com.literalura.literalura.principal.Principal;

import com.literalura.literalura.repository.IAuthorRepository;
import com.literalura.literalura.repository.IbookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	@Autowired
	private IbookRepository bookRepository;
	@Autowired
	private IAuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(bookRepository, authorRepository);
		principal.muestraElMenu();

	}
}