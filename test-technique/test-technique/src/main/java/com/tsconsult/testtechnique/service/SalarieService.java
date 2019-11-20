package com.tsconsult.testtechnique.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsconsult.testtechnique.Model.Salarie;
import com.tsconsult.testtechnique.commun.CustomException;

//1 - A revoir car pas besoin d'utilisation de File, juste une traitement sur List<Salarie> pour supprimer les doublements selon un critere.
//2 Afin de mieux respecter le pattern MVC faut passer par des interfaces (interface SalarieService et class SalarieServiceImpl qui implemente la première).
//3 Bonus utilise un LOGGER pour les exceptions techniques et CustomException lorsque on souhaite remonter un message customisé aua Front.

@Service
public class SalarieService {
	Field field = null;
	CustomException ce = null;

	public List<Salarie> dedoubloneSalarie(MultipartFile salaries, String critere) throws CustomException, Exception {

		FileUtils.cleanDirectory(new File("src/main/resources/uploads"));

		String filePath = salaries.getOriginalFilename();

		File file = new File("src/main/resources/uploads/" + filePath);

		OutputStream os = new FileOutputStream(file);
		os.write(salaries.getBytes());
		os.close();

		ObjectMapper objectMapper = new ObjectMapper();
		List<Salarie> salariesList = new ArrayList<Salarie>();
		List<Salarie> salariesDedoublounes = new ArrayList<Salarie>();
		List<Object> critereList = new ArrayList<Object>();

		try {
			salariesList = objectMapper.readValue(file, new TypeReference<List<Salarie>>() {
			});
			field = Salarie.class.getDeclaredField(critere);
			field.setAccessible(true);
			salariesList.stream().forEach(s -> {
				try {
					if (!critereList.contains(field.get(s))) {
						critereList.add(field.get(s));
						salariesDedoublounes.add(s);
					}
				} catch (IllegalArgumentException e) {

					ce = new CustomException("Internal server error");

				} catch (IllegalAccessException e) {

					ce = new CustomException("Internal server error");

				}
			});
			if (ce != null) {
				throw ce;
			}
		} catch (NoSuchFieldException e) {
			throw new CustomException("le critère '" + critere + "' est incorrect");
		} catch (SecurityException e) {

			throw new CustomException("SecurityException : " + e.getMessage());
		} catch (JsonParseException e) {
			throw new CustomException(
					"le format de fichier est incorrect, veillez choisir un fichier json contenant des salariés");
		} catch (JsonMappingException e) {

			throw new CustomException(
					"erreur dans le fichier json chargé, les enregistrements contenant dans le ficheir Json ne correspondent pas a l'objet Salarie");

		}

		return salariesDedoublounes;
	}

}
