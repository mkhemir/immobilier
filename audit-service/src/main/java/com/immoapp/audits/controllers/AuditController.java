package com.immoapp.audits.controllers;

import com.immoapp.audits.calculs.LoiPinelCalcul;
import com.immoapp.audits.dtos.DossierPinelDTO;
import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatLoiPinelDTO;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.services.AuditService;
import com.immoapp.audits.services.DbService;
import com.immoapp.audits.services.ProduitImmobilierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuditController {
    @Autowired
    ProduitImmobilierService produitImmobilierService;
    @Autowired
    DbService dbService;
    @Autowired
    AuditService auditService;

    private static final Logger logger = LoggerFactory.getLogger(AuditController.class);

    @CrossOrigin
    @GetMapping(value = "/dossiers")
    public List<DossierPinelDTO> getImmobilierMessage() {
        String msg = "le produit immobilier a dit : ";
        List<DossierPinelDTO> listDossiers = new ArrayList<>();
        try {
            List<ProduitImmobilierDTO> listProduits = dbService.getProduitImmobilier();
            listProduits.stream().forEach(p -> {
                DossierPinelDTO dossierPinelDTO = new DossierPinelDTO();
                dossierPinelDTO.setResultatLoiPinel6DTO(auditService.getResultat(TypePinel.PINEL6ANS, p, null, null, null));
                dossierPinelDTO.setResultatLoiPinel9DTO(auditService.getResultat(TypePinel.PINEL9ANS, p, null, null, null));
                dossierPinelDTO.setResultatLoiPinel12DTO(auditService.getResultat(TypePinel.PINEL12ANS, p, null, null, null));
                dossierPinelDTO.setProduitImmobilierDTO(p);
                listDossiers.add(dossierPinelDTO);
            });
            return listDossiers;
        } catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/dossier/{id}")
    public DossierPinelDTO getDossierPinel(@PathVariable(value = "id") Long id) {
        DossierPinelDTO dossierPinelDTO = new DossierPinelDTO();
        ProduitImmobilierDTO produitImmobilierDTO = dbService.getProduitImmobilierById(id);
        dossierPinelDTO.setResultatLoiPinel6DTO(auditService.getResultat(TypePinel.PINEL6ANS, produitImmobilierDTO, null, null, null));
        dossierPinelDTO.setResultatLoiPinel9DTO(auditService.getResultat(TypePinel.PINEL9ANS, produitImmobilierDTO, null, null, null));
        dossierPinelDTO.setResultatLoiPinel12DTO(auditService.getResultat(TypePinel.PINEL12ANS, produitImmobilierDTO, null, null, null));
        dossierPinelDTO.setProduitImmobilierDTO(produitImmobilierDTO);
        return dossierPinelDTO;
    }
}
