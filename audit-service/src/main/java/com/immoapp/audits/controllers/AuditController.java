package com.immoapp.audits.controllers;

import com.immoapp.audits.clients.ProduitImmobilierClient;
import com.immoapp.audits.services.DiscoveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {
    @Autowired
    ProduitImmobilierClient produitImmobilierClient;
    private static final Logger logger = LoggerFactory.getLogger(AuditController.class);

    @GetMapping(value = "/message")
    public String getImmobilierMessage(){
    String msg = "le produit immobilier a dit : ";
    logger.debug("********************************************"+produitImmobilierClient.getProduitImmobilier());
    try {
        return msg+produitImmobilierClient.getProduitImmobilier();
    }
    catch (Exception e){
        return msg+"exception occured";
    }
    }
}
