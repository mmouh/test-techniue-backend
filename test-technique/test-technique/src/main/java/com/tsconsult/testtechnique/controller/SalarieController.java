package com.tsconsult.testtechnique.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tsconsult.testtechnique.Model.Salarie;
import com.tsconsult.testtechnique.service.SalarieService;

import commun.CustomException;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SalarieController {
	
	@Autowired
	private SalarieService salarieService;

	@PostMapping(value =  "/salaries")
	@ResponseBody
	public List<Salarie> listerSalaries(@RequestParam("salaries") MultipartFile salaries, @RequestParam String critere) throws CustomException{
	
	
    
    try {
    
	return salarieService.dedoubloneSalarie(salaries, critere);
    }catch (CustomException e) {
		throw e;
	}catch (Exception e) {
		throw new CustomException(e.getMessage());
	}
		
	}
	
	
}
