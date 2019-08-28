package com.immoapp.produit.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HELLOcONTROLLER {
    private static final Logger logger = LoggerFactory.getLogger(HELLOcONTROLLER.class);
    @GetMapping(value = "/hello")
    public ResponseEntity<String> welcome(){
        logger.info("++++++++++++++++++++++++++++++++++++++++++++reaching it");

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
