package com.immoapp.audits.controllers;


import com.immoapp.audits.dtos.*;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.services.AuditService;
import com.immoapp.audits.services.DbService;
import com.immoapp.audits.services.OptimisationFiscaleService;
import com.immoapp.audits.services.ProduitImmobilierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuditController {
    @Autowired
    private ProduitImmobilierService produitImmobilierService;
    @Autowired
    private DbService dbService;
    @Autowired
    AuditService auditService;
    @Autowired
    private OptimisationFiscaleService optimisationFiscaleService;

    private static final Logger logger = LoggerFactory.getLogger(AuditController.class);

    @CrossOrigin
    @GetMapping(value = "/dossiers")
    public List<DossierSimulationDTO> getImmobilierMessage() {
        String msg = "le produit immobilier a dit : ";
        List<DossierSimulationDTO> listDossiers = new ArrayList<>();
        try {
            List<ProduitImmobilierDTO> listProduits = dbService.getProduitImmobilier();
            listProduits.stream().forEach(p -> {
                DossierSimulationDTO dossierSimulationDTO = new DossierSimulationDTO();
                dossierSimulationDTO.setResultatLoiPinel6DTO(auditService.getPinel(TypePinel.PINEL6ANS, p, null, null, null));
                dossierSimulationDTO.setResultatLoiPinel9DTO(auditService.getPinel(TypePinel.PINEL9ANS, p, null, null, null));
                dossierSimulationDTO.setResultatLoiPinel12DTO(auditService.getPinel(TypePinel.PINEL12ANS, p, null, null, null));
                dossierSimulationDTO.setProduitImmobilierDTO(p);
                listDossiers.add(dossierSimulationDTO);
            });
            return listDossiers;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/dossier/{id}",consumes = {"text/plain;charset=UTF-8", MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
    public DossierSimulationDTO getSimulation(@PathVariable(value = "id") Long id) {
        DossierSimulationDTO dossierSimulationDTO = new DossierSimulationDTO();
        double salaire = 2500;
        ProduitImmobilierDTO produitImmobilierDTO = dbService.getProduitImmobilierById(id);
        dossierSimulationDTO.setProduitImmobilierDTO(produitImmobilierDTO);
        InformationBanqueDTO banqueInfo = optimisationFiscaleService.getInfoBanques().stream().findFirst().get();
        dossierSimulationDTO.setResultatLoiPinel6DTO(auditService.getPinel(TypePinel.PINEL6ANS, produitImmobilierDTO, null, null, null));
        dossierSimulationDTO.setResultatLoiPinel9DTO(auditService.getPinel(TypePinel.PINEL9ANS, produitImmobilierDTO, null, null, null));
        dossierSimulationDTO.setResultatLoiPinel12DTO(auditService.getPinel(TypePinel.PINEL12ANS, produitImmobilierDTO, null, null, null));
        dossierSimulationDTO.setResultatLmnpReelDto(auditService.getLmnpReel(produitImmobilierDTO));
        dossierSimulationDTO.setResultatLmnpMicroDto(auditService.getLmnpMicro(produitImmobilierDTO));
        dossierSimulationDTO.setResultatBouvardDTO(auditService.getBouvard(produitImmobilierDTO, null, 0));
        dossierSimulationDTO.setResultatMalrauxDTO(auditService.getMalraux(produitImmobilierDTO, 25, 0));
        dossierSimulationDTO.setResultatMhDto(auditService.getMh(produitImmobilierDTO, 25, 0, salaire));
        dossierSimulationDTO.setDeficitFoncierDTO(auditService.getDeficitFincier(produitImmobilierDTO));
        return dossierSimulationDTO;
    }
}
