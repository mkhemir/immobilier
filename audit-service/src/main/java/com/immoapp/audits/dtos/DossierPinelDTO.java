package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DossierPinelDTO {

    private ProduitImmobilierDTO produitImmobilierDTO;

    private ResultatLoiPinelDTO resultatLoiPinelDTO;

}
