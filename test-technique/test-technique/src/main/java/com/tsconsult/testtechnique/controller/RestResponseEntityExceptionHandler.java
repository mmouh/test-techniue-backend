package com.tsconsult.testtechnique.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tsconsult.testtechnique.commun.CustomException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.HttpMessageNotReadableException;

// 1 - Il faut ajouter les autres @ExceptionHandler qui ne sont pas couverts par CustomException s'ils y en a.


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(RestResponseEntityExceptionHandler.class);
    
	@ExceptionHandler({ CustomException.class})
	protected ResponseEntity<?> CustomExceptionHandler(CustomException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
                logger.log(Level.WARN, bodyOfResponse);
		return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
          
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<?> otherExceptionHandler(Exception ex, WebRequest request){
         String bodyResponse = "Internal Server Error";
         logger.log(Level.WARN,"Exception levé : " + ex.getClass()+", " +  ex.getMessage());
         return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        
}
