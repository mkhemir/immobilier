package com.immoapp.db.controllers;

import com.immoapp.db.dtos.ProduitImmobilierDTO;
import com.immoapp.db.services.ProduitImmobilierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProduitImmobilierController {

    @Autowired
    ProduitImmobilierService produitImmobilierService;

    private static final Logger logger = LoggerFactory.getLogger(ProduitImmobilierController.class);
  //  @CrossOrigin
    @GetMapping({"/produits"})
    public List<ProduitImmobilierDTO> findAll(){
        return produitImmobilierService.findAllProducts();
    }

    @PostMapping({"/ajout"})
    public void addProduct(@RequestBody ProduitImmobilierDTO produitImmobilierDTO){
        produitImmobilierService.saveProduct(produitImmobilierDTO);
    }
   // @CrossOrigin
    @GetMapping({"/produit/{id}"})
    public ProduitImmobilierDTO findProduit(@PathVariable(value = "id") Long id){
        logger.info("///////////////////.............................. db a recu l'appel : "+id);
        ProduitImmobilierDTO p= produitImmobilierService.getProduitImmobilier(id);
        logger.info("/////////////////// retournr : "+p);
        return  p;
    }
}
