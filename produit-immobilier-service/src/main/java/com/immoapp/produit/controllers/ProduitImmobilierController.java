package com.immoapp.produit.controllers;

import com.immoapp.produit.dtos.ProduitImmobilierDTO;
import com.immoapp.produit.dtos.Search;
import com.immoapp.produit.services.DbService;
import com.immoapp.produit.services.ProduitImmobilierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PostMapping(value = "/produits",consumes = {"text/plain;charset=UTF-8", MediaType.APPLICATION_JSON_VALUE})
 //  @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Search welcome(@RequestBody Search search){
        logger.info("...................................................CONTROLLER PRODUITIMMOBILIERSERVICE CA PASSE");

        int page = 1;
        int pageSize = 10;
        List<ProduitImmobilierDTO> result = dbService.getProduitImmobilier();
        ProduitImmobilierDTO[] array = result.toArray(new ProduitImmobilierDTO[result.size()]);
        search.setResult(array);

        return search;
    }

    @PostMapping(value = "/ajout")
  //  @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void saveProduct(@RequestBody ProduitImmobilierDTO produitImmobilierDTO){
        logger.info("++++++++++++++++++++++++++++++++++++++++++++reaching it");

         dbService.saveProduitImmobilier(produitImmobilierDTO);
    }
}
