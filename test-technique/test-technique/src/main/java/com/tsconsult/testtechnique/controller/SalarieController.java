package com.tsconsult.testtechnique.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tsconsult.testtechnique.Model.Salarie;
import com.tsconsult.testtechnique.commun.CustomException;
import com.tsconsult.testtechnique.service.SalarieService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SalarieController {

	@Autowired
	private SalarieService salarieService;

	@PostMapping(value = "/salaries")
	@ResponseBody
	public ResponseEntity<List<Salarie>> listerSalaries(@RequestParam("salaries") MultipartFile salaries, @RequestParam String critere) throws CustomException, Exception
			{
			return new ResponseEntity<>(salarieService.dedoubloneSalarie(salaries, critere),HttpStatus.OK);
	
	}

}
