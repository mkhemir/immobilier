package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdresseDTO {

    /**
     * THE PRODUCT ADDRESS.
     */
    private String adresse;

    private String codePostal;

    private String ville;

    private boolean estSitePSMV;

    private boolean estSitePVAR;


}
