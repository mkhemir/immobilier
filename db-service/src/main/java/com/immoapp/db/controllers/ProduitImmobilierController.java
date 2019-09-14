package com.immoapp.db.controllers;

import com.immoapp.db.dtos.ProduitImmobilierDTO;
import com.immoapp.db.services.ProduitImmobilierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/produits"})
public class ProduitImmobilierController {

    @Autowired
    ProduitImmobilierService produitImmobilierService;

    @GetMapping
    public List<ProduitImmobilierDTO> findAll(){
        return produitImmobilierService.findAllProducts();
    }
}
