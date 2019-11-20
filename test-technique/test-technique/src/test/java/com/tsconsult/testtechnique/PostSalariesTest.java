package com.tsconsult.testtechnique;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsconsult.testtechnique.Model.Salarie;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestTechniqueApplication.class)
@WebAppConfiguration
public class PostSalariesTest {

	protected MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	String uri;
	String critere;
	File file;

	protected void setUp(File fileParam, String critereParam) {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		uri = "/salaries";
		file = fileParam;
		file.setReadOnly();
		critere = critereParam;

	}

	protected <T> T mapFromJson(String json, TypeReference<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	public void listerSalaries() throws Exception {
		setUp(new File("src/test/resources/salaries.json"), "adresse");

		InputStream is = null;
		is = new FileInputStream(file);
		MockMultipartFile salaries = new MockMultipartFile("salaries", "salaries.json",
				MediaType.APPLICATION_JSON_VALUE, is);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.multipart(uri).file(salaries).param("critere", critere)
						.contentType(MediaType.APPLICATION_JSON_VALUE))

				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
		System.out.println("resultat : " + result.getResponse().getContentAsString());
		Assert.assertEquals(2,
				mapFromJson(result.getResponse().getContentAsString(), new TypeReference<List<Salarie>>() {
				}).size());

	}

	@Test
	public void listerSalaries2() throws Exception {
		setUp(new File("src/test/resources/salaries2.json"), "age");
		InputStream is = null;
		is = new FileInputStream(file);
		MockMultipartFile salaries = new MockMultipartFile("salaries", "salaries2.json",
				MediaType.APPLICATION_JSON_VALUE, is);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.multipart(uri).file(salaries).param("critere", critere)
						.contentType(MediaType.APPLICATION_JSON_VALUE))

				.andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
		Assert.assertEquals(result.getResponse().getContentAsString(), "le critère '" + critere + "' est incorrect");
	}

	@Test
	public void listerSalaries3() throws Exception {
		setUp(new File("src/test/resources/salaries3.json"), "adresse");
		InputStream is = null;
		is = new FileInputStream(file);
		MockMultipartFile salaries = new MockMultipartFile("salaries", "salaries3.json",
				MediaType.APPLICATION_JSON_VALUE, is);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.multipart(uri).file(salaries).param("critere", critere)
						.contentType(MediaType.APPLICATION_JSON_VALUE))

				.andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
		Assert.assertEquals(result.getResponse().getContentAsString(),
				"erreur dans le fichier json chargé, les enregistrements contenant dans le ficheir Json ne correspondent pas a l'objet Salarie");
	}

	@Test
	public void listerSalaries4() throws Exception {
		setUp(new File("src/test/resources/testTxt.txt"), "adresse");
		InputStream is = null;
		is = new FileInputStream(file);
		MockMultipartFile salaries = new MockMultipartFile("salaries", "testTxt.txt", MediaType.APPLICATION_JSON_VALUE,
				is);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.multipart(uri).file(salaries).param("critere", critere)
						.contentType(MediaType.APPLICATION_JSON_VALUE))

				.andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
		Assert.assertEquals(result.getResponse().getContentAsString(),
				"le format de fichier est incorrect, veillez choisir un fichier json contenant des salariés");
	}

	@Test
	public void listerSalaries5() throws Exception {
		setUp(new File("src/test/resources/salaries5.json"), "prenom");

		InputStream is = null;
		is = new FileInputStream(file);
		MockMultipartFile salaries = new MockMultipartFile("salaries", "salaries5.json",
				MediaType.APPLICATION_JSON_VALUE, is);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.multipart(uri).file(salaries).param("critere", critere)
						.contentType(MediaType.APPLICATION_JSON_VALUE))

				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
		System.out.println("resultat : " + result.getResponse().getContentAsString());
		Assert.assertEquals(4,
				mapFromJson(result.getResponse().getContentAsString(), new TypeReference<List<Salarie>>() {
				}).size());

	}

}
