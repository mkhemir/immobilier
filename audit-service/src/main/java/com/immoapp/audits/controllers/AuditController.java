package com.immoapp.audits.controllers;

import com.immoapp.audits.calculs.LoiPinelCalcul;
import com.immoapp.audits.dtos.DossierPinelDTO;
import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatLoiPinelDTO;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.services.DbService;
import com.immoapp.audits.services.ProduitImmobilierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuditController {
    @Autowired
    ProduitImmobilierService produitImmobilierService;
    @Autowired
    DbService dbService;

    private static final Logger logger = LoggerFactory.getLogger(AuditController.class);

    @GetMapping(value = "/message")
    public List<DossierPinelDTO> getImmobilierMessage(){
    String msg = "le produit immobilier a dit : ";
    List<DossierPinelDTO> listDossiers = new ArrayList<>();
        LoiPinelCalcul loiPinelCalcul = new LoiPinelCalcul();
  //  logger.debug("********************************************"+ dbService.getProduitImmobilier());
    try {
        List<ProduitImmobilierDTO> listProduits = dbService.getProduitImmobilier();
        listProduits.stream().forEach(p -> {
            DossierPinelDTO dossierPinelDTO = new DossierPinelDTO();
            ResultatLoiPinelDTO resultatLoiPinelDTO = new ResultatLoiPinelDTO();
            resultatLoiPinelDTO.setLoyerMaximum(loiPinelCalcul.calculerLoyerMax(p));
            resultatLoiPinelDTO.setReductionImpots6ans(loiPinelCalcul.calculerReductionImpots(p, TypePinel.PINEL6ANS).doubleValue());
            resultatLoiPinelDTO.setReductionImpots9ans(loiPinelCalcul.calculerReductionImpots(p, TypePinel.PINEL9ans).doubleValue());
            resultatLoiPinelDTO.setReductionImpots12ans(loiPinelCalcul.calculerReductionImpots(p, TypePinel.PINEL12ANS).doubleValue());
            dossierPinelDTO.setResultatLoiPinelDTO(resultatLoiPinelDTO);
            dossierPinelDTO.setProduitImmobilierDTO(p);
            listDossiers.add(dossierPinelDTO);
        });
        return listDossiers;
    }
    catch (Exception e){
        return null;
    }
    }
}
