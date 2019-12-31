package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DossierSimulationDTO {

    private ProduitImmobilierDTO produitImmobilierDTO;

    private ResultatLoiPinelDTO resultatLoiPinel6DTO;

    private ResultatLoiPinelDTO resultatLoiPinel9DTO;

    private ResultatLoiPinelDTO resultatLoiPinel12DTO;

    private ResultatLmnpDto resultatLmnpReelDto;

    private ResultatLmnpDto resultatLmnpMicroDto;

//    private ResultatBouvardDTO resultatBouvardDTO;

}
