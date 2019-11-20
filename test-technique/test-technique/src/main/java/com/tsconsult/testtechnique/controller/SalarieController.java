package com.tsconsult.testtechnique.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Salarie> listerSalaries(@RequestParam("salaries") MultipartFile salaries, @RequestParam String critere)
			throws CustomException {

		try {

			return salarieService.dedoubloneSalarie(salaries, critere);
		} catch (CustomException e) {
			throw e;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}