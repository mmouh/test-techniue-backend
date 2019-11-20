package com.tsconsult.testtechnique.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import commun.CustomException;

@Service
public class SalarieService{
	Field field = null;
	CustomException ce = null;

	public List<Salarie> dedoubloneSalarie(MultipartFile salaries, String critere) throws CustomException,Exception{
		


		
		FileUtils.cleanDirectory(new File("src/main/resources/uploads"));
	
		
		String filePath = salaries.getOriginalFilename();
		   

	    File file = new File("src/main/resources/uploads/"+filePath);
	    
	    	OutputStream os = new FileOutputStream(file);
	    	os.write(salaries.getBytes());
	    	os.close();
	   
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<Salarie> salariesList = new ArrayList();
		List<Salarie> salariesDedoublounes = new ArrayList();
		List<Object> critereList = new ArrayList();
		
			try {
				salariesList = objectMapper.readValue(file, new TypeReference<List<Salarie>>(){});
				field = Salarie.class.getDeclaredField(critere);
				field.setAccessible(true);
				salariesList.stream().forEach(s -> {
					try {
						if(!critereList.contains(field.get(s))){
							critereList.add(field.get(s));
							salariesDedoublounes.add(s);
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						ce = new CustomException("Internal server error");
						
					//	throw new CustomException(e.getMessage());
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						ce = new CustomException("Internal server error");
						
					}
				});
				if(ce != null) {
					throw ce;
				}
			}catch (NoSuchFieldException e) {
				throw new CustomException("le critère '" + critere+"' est incorrect");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new CustomException("SecurityException : " + e.getMessage());
			} catch (JsonParseException e) {
				throw new CustomException("le format de fichier est incorrect, veillez choisir un fichier json contenant des salariés");
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new CustomException("erreur dans le fichier json chargé, les enregistrements contenant dans le ficheir Json ne correspondent pas a l'objet Salarie");
				//System.out.println("JsonMappingException : " + e.getMessage());
			} 
			
			 
			
		
		return salariesDedoublounes;
	}
	
}
