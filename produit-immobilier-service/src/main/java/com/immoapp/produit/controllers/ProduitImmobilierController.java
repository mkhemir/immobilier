package com.immoapp.produit.controllers;

import com.immoapp.produit.dtos.ProduitImmobilierDTO;
import com.immoapp.produit.services.DbService;
import com.immoapp.produit.services.ProduitImmobilierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProduitImmobilierController {

    @Autowired
    ProduitImmobilierService produitImmobilierService;
    @Autowired
    DbService dbService;

    private static final Logger logger = LoggerFactory.getLogger(ProduitImmobilierController.class);
    @GetMapping(value = "/produits")
    @CrossOrigin
    public List<ProduitImmobilierDTO> welcome(){
        logger.info("++++++++++++++++++++++++++++++++++++++++++++reaching it");

        return dbService.getProduitImmobilier();
    }

    @PostMapping(value = "/ajout")
    @CrossOrigin
    public void saveProduct(@RequestBody ProduitImmobilierDTO produitImmobilierDTO){
        logger.info("++++++++++++++++++++++++++++++++++++++++++++reaching it");

         dbService.saveProduitImmobilier(produitImmobilierDTO);
    }
}
