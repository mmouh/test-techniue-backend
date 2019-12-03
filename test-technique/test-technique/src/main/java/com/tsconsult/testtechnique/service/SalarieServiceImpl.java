package com.tsconsult.testtechnique.service;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsconsult.testtechnique.Model.Salarie;
import com.tsconsult.testtechnique.commun.CustomException;
import com.tsconsult.testtechnique.repository.SalarieRepository;

//1 - A revoir car pas besoin d'utilisation de File, juste une traitement sur List<Salarie> pour supprimer les doublements selon un critere.
//2 Afin de mieux respecter le pattern MVC faut passer par des interfaces (interface SalarieService et class SalarieServiceImpl qui implemente la première).
//3 Bonus utilise un LOGGER pour les exceptions techniques et CustomException lorsque on souhaite remonter un message customisé aua Front.

@Service
public class SalarieServiceImpl implements SalarieService{
	
	@Autowired
	private SalarieRepository salarieRepository;
	
	Field field = null;
	CustomException ce = null;
        
	@Override
	public List<Salarie> dedoubloneSalarie(List<Salarie> salaries, String critere) throws CustomException, Exception {
		List<Salarie> salariesDedoublounes = new ArrayList<Salarie>();
		List<Object> critereList = new ArrayList<Object>();
		try{
		field = Salarie.class.getDeclaredField(critere);
                
		field.setAccessible(true);
		salaries.stream().forEach(s -> {
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
		}catch(NoSuchFieldException ex){
                    throw new CustomException("le critère '" + critere + "' est incorrect");
                }

		return salariesDedoublounes;
	}

	@Override
	@Transactional
	public List<Salarie> findAll() {
		return salarieRepository.findAll();
	}

	@Override
	@Transactional
	public Salarie findById(int theId) {
		return salarieRepository.findById(theId);
	}

	@Override
	@Transactional
	public Salarie save(Salarie theSalarie) {
		return salarieRepository.save(theSalarie);
		
	}

	@Override
        @Transactional
	public int deleteById(int theId) {
		return salarieRepository.deleteById(theId);
		
	}

	
}
