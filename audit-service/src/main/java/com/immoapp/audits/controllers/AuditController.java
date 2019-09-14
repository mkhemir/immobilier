package com.immoapp.audits.controllers;

import com.immoapp.audits.services.DbService;
import com.immoapp.audits.services.ProduitImmobilierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {
    @Autowired
    ProduitImmobilierService produitImmobilierService;
    @Autowired
    DbService dbService;

    private static final Logger logger = LoggerFactory.getLogger(AuditController.class);

    @GetMapping(value = "/message")
    public String getImmobilierMessage(){
    String msg = "le produit immobilier a dit : ";
    logger.debug("********************************************"+ produitImmobilierService.getProduitImmobilier()+ ":" +dbService.getProduitImmobilier().get(0).getType()+" and expensive :"+ dbService.getProduitImmobilier().get(0).getPrix());
    try {
        return msg+ produitImmobilierService.getProduitImmobilier();
    }
    catch (Exception e){
        return msg+"exception occured";
    }
    }
}
