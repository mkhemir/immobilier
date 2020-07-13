package com.immoapp.tax.controllers;

import com.immoapp.tax.dtos.InformationBanqueDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BanqueController {

    private static final Logger logger = LoggerFactory.getLogger(BanqueController.class);

  //  @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/banques")
    public List<InformationBanqueDTO> getImmobilierMessage() {
        List<InformationBanqueDTO> listBanques = new ArrayList<>();
        InformationBanqueDTO informationBanqueDTO = new InformationBanqueDTO();
        informationBanqueDTO.setNom("SOCIETE GENERALE");
        informationBanqueDTO.setTaeg(0.015);
        listBanques.add(informationBanqueDTO);
        return listBanques;
    }
}
