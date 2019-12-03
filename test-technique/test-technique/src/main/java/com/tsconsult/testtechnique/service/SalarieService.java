package com.tsconsult.testtechnique.service;

import java.util.List;

import com.tsconsult.testtechnique.Model.Salarie;
import com.tsconsult.testtechnique.commun.CustomException;



public interface SalarieService {
	
	public List<Salarie> dedoubloneSalarie(List<Salarie> salaries, String critere) throws CustomException, Exception;
	
	public List<Salarie> findAll();
	
	public Salarie findById(int theId);
	
	public Salarie save(Salarie theSalarie);
	
	public int deleteById(int theId);
	
}
