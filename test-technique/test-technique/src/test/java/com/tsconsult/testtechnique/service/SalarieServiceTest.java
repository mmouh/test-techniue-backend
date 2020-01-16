package com.tsconsult.testtechnique.service;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tsconsult.testtechnique.Model.Salarie;
import com.tsconsult.testtechnique.commun.CustomException;
import com.tsconsult.testtechnique.repository.SalarieRepository;

@DisplayName("test de la classe SalarieServiceImpl")

//@ExtendWith(SpringExtension.class)
@SpringBootTest
class SalarieServiceTest {
	
	@InjectMocks
	SalarieServiceImpl salarieService;
	
	@MockBean
	SalarieRepository salarieRepository;
	
	static List<Salarie> salaries = new ArrayList<Salarie>();
	static List<Salarie> salarieDedoublonnes = new ArrayList<Salarie>();
	
	@BeforeAll
	static void init() {
		salaries.add(new Salarie(7, "Marie", "Chef de projet", 6,"Paris", 4000, LocalDate.of(1989, 10, 1)));
		salaries.add(new Salarie(9, "Pierre", "Chef de projet", 5,"Paris", 4000, LocalDate.of(1990, 5, 5)));
		salaries.add(new Salarie(11, "Marie", "Développeur", 6,"Paris", 3500, LocalDate.of(1990, 8, 25)));
		
		salarieDedoublonnes.add(salaries.get(0));
		salarieDedoublonnes.add(salaries.get(2));

	}

	@Test
	@DisplayName("test dedoublonnement d'une liste de 3 salaries et critère correcte")
	void testDedoubloneSalarie() throws CustomException, Exception {
		//SalarieService salarieService = new SalarieServiceImpl();
		
		assertEquals(salarieDedoublonnes, salarieService.dedoubloneSalarie(salaries, "fonction"));

	}
	// Tu test quoi au juste ici ?!!
	/*
	@Test
	@DisplayName("test avec liste vide retourne une liste vide")
	void testDedoubloneSalarie1() throws CustomException, Exception{
		assertEquals(new ArrayList<Salarie>(), new ArrayList<Salarie>());
	}
	*/

	// Voici un exemple de test  "best practice"
	@Test
	@DisplayName("test avec liste vide retourne une liste vide")
	void testDedoubloneSalarie1() throws CustomException, Exception{
		assertEquals(new ArrayList<Salarie>(), salarieService.dedoubloneSalarie(new ArrayList<Salarie>(),"fonction"));
	}
	
	@Test
	@DisplayName("test avec critere erroné retourne une exception")
	void testDedoubloneSalarie2() {
		Assertions.assertThrows(CustomException.class, () -> {
			salarieService.dedoubloneSalarie(salaries,"fonct");
		});
	}

}
