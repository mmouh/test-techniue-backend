package com.tsconsult.testtechnique;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tsconsult.testtechnique.controller.SalarieController;


@SpringBootTest
//@ExtendWith(SpringExtension.class)
class TestTechniqueApplicationTests {
	

	@Autowired
	private SalarieController salarieController;
	

	@Test
	void contextLoads() {
		assertThat(salarieController).isNotNull();
		
	}

}
