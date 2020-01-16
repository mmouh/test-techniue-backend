package com.tsconsult.testtechnique.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsconsult.testtechnique.Model.Salarie;
import com.tsconsult.testtechnique.commun.CustomException;
import com.tsconsult.testtechnique.service.SalarieService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SalarieController {

    @Autowired
    private SalarieService salarieService;

	// 1 - le web service doit prendre en param une List<Salarie> que le Front doit lui fournir et non MultipartFile salaries.
	// 2 - l'exception est mal gerée de plus d'une manière général aucun traitement ne doit apparaître dans le controller, donc le try catch doit se faire côté service.
	// 3 - la réponse doit être encapsulée ResponseEntity<List<Salarie>> et non directement List<Salarie> si tu veux justement utiliser ta class RestResponseEntityExceptionHandler.


    @PostMapping(value = "/salariesDedoublonnes",produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Salarie>> DedoublonneSalaries(@RequestBody List<Salarie> salaries, @RequestParam String critere) throws CustomException, Exception
    {
    	List<Salarie> sals = salarieService.dedoubloneSalarie(salaries, critere);
    	return new ResponseEntity<List<Salarie>>(sals, HttpStatus.OK);
    	
	
    }
        
    @GetMapping(value = "/salaries",produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Salarie>> listSalaries()throws CustomException, Exception{
         return new ResponseEntity<> (salarieService.findAll(), HttpStatus.OK);
    } 
    
    @DeleteMapping(value = "/salaries/{id}",produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> deleteSalarie(@PathVariable ("id") Integer id) throws CustomException, Exception{
        return new ResponseEntity<>(salarieService.deleteById(id),HttpStatus.OK);
    }  
    
    @PostMapping(value = "/salaries",produces = "application/json;charset=UTF-8")
    public ResponseEntity<Salarie> addSalarie(@RequestBody Salarie salarie)throws CustomException, Exception{
        return new ResponseEntity<>(salarieService.save(salarie),HttpStatus.OK);
    }
    
    @PutMapping(value = "/salaries",produces = "application/json;charset=UTF-8")
    public ResponseEntity<Salarie> updateSalarie(@RequestBody Salarie salarie) throws CustomException, Exception{
    	return new ResponseEntity<>(salarieService.save(salarie),HttpStatus.OK);
    }
   
    
    

    
}
