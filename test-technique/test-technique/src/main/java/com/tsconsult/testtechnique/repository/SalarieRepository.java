/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsconsult.testtechnique.repository;

import java.util.List;

import com.tsconsult.testtechnique.Model.Salarie;

/**
 *
 * @author localAdmin
 */
public interface SalarieRepository {
	
	public List<Salarie> findAll();
	
	public Salarie findById(int theId);
	
	public Salarie save(Salarie theSalarie);
	
	public int deleteById(int theId);
    
}
