package com.tsconsult.testtechnique.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.junit.MockitoJUnitRunner;

import com.tsconsult.testtechnique.service.SalarieService;
import com.tsconsult.testtechnique.service.SalarieServiceImpl;
import com.tsconsult.testtechnique.Model.Salarie;
import com.tsconsult.testtechnique.TestTechniqueApplication;
import org.springframework.web.context.WebApplicationContext;

//Le projet est de type springBoot
// les tests aussi doivent etre de même il faut donc une class TestTechniqueApplicationTest afin de bootStrapper les tests de springBootTest
// avec les annotation suivantes
//@RunWith(SpringRunner.class)
//@SpringBootTest


// Cette class n'appartient pas a springBoot
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(controllers = SalarieController.class)
@DisplayName("test de la classe SalarieController")
class SalarieControllerTest {


	@MockBean
	SalarieService salarieService;

	@Autowired
	private MockMvc mvc;

	protected ObjectMapper objectMapper;
	
	static List<Salarie> salaries = new ArrayList<Salarie>();
	
	static List<Salarie> salarieDedoublonnes = new ArrayList<Salarie>();
	
	@BeforeAll
	static void setUp() {
		salaries.add(new Salarie(7, "Marie", "Chef de projet", 6,"Paris", 4000, LocalDate.of(1989, 10, 1)));
		salaries.add(new Salarie(9, "Pierre", "Chef de projet", 5,"Paris", 4000, LocalDate.of(1990, 5, 5)));
		salaries.add(new Salarie(11, "Marie", "Développeur", 6,"Paris", 3500, LocalDate.of(1990, 8, 25)));
		
		salarieDedoublonnes.add(salaries.get(0));
		salarieDedoublonnes.add(salaries.get(2));
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
 		
		JavaTimeModule module = new JavaTimeModule();
		LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		ObjectMapper mapper = new ObjectMapper();
		mapper = Jackson2ObjectMapperBuilder.json()
				.modules(module)
				.simpleDateFormat("yyyy-MM-dd")
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.serializerByType(LocalDateTime.class, localDateTimeSerializer)
				.build();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(obj);
		return  requestJson;
	}


	protected <T> T mapFromJson(String json, TypeReference <T> clazz)
			throws IOException {
		objectMapper = new ObjectMapper();
		JavaTimeModule module = new JavaTimeModule();
		LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
		objectMapper = Jackson2ObjectMapperBuilder.json()
				.modules(module)
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.build();

		return objectMapper.readValue(json, clazz);
	}

	@Test
	@DisplayName("test de lister tous les salaries")
	void testListSalaries() throws Exception {

		when(salarieService.findAll()).thenReturn(salaries);
		MvcResult result =mvc.perform(MockMvcRequestBuilders
				.get("/salaries")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				).andExpect(status().isOk())
				.andReturn();
				List<Salarie> listSalaries = mapFromJson( result.getResponse().getContentAsString(), new TypeReference<List<Salarie>>() {
				});
		assertEquals(3,listSalaries.size());
		assertEquals(7,listSalaries.get(0).getId());
		assertEquals(9,listSalaries.get(1).getId());
		assertEquals(11,listSalaries.get(2).getId());
	}

	@Test
	@DisplayName("test de dédoulonnement da salariés")
	void testDedoblonneSalaries() throws Exception{

		when(salarieService.dedoubloneSalarie(salaries,"fonction")).thenReturn(salarieDedoublonnes);
		MvcResult result =mvc.perform(MockMvcRequestBuilders
				.post("/salariesDedoublonnes")
				.param("critere","fonction")
				.contentType("application/json")
				.characterEncoding("UTF-8")
				.content(mapToJson(salaries))
				.accept("application/json")
		).andExpect(status().isOk())
				.andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		List<Salarie> listSalaries = mapFromJson( result.getResponse().getContentAsString(), new TypeReference<List<Salarie>>() {
		});
		
		assertEquals(2, listSalaries.size());
		assertEquals(listSalaries.get(0).getFonction(), "Chef de projet");
		assertEquals(listSalaries.get(1).getFonction(), "Développeur");

	}

}
