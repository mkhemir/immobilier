package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProduitImmobilierDTO {

    /**
     * the ID of the product.
     */
    private Long id;
    /**
     * the type of the product.
     */
    private String type;
    /**
     * the phone number to contact.
     */
    private String telephone;
    /**
     * the number of lots.
     */
    private String nbr_lots;
    /**
     * does it contain a parking.
     */
    private Boolean isParking;
    /**
     * does it contain a lift.
     */
    private Boolean isLift;
    /**
     * the dpe.
     */
    private char dpe;
    /**
     * charges copropriété.
     */
    private BigDecimal chargesCoprop;
    /**
     * taxes fonciaires.
     */
    private String taxeFonciaire;
    /**
     * date de construction.
     */
    private Date dateConstruction;
    /**
     * THE PRODUCT ADDRESS.
     */
    private String adresse;
    /**
     * THE PRODUCT PRICE.
     */
    private BigDecimal prix;

    private Double surface;

    private Double surfaceBalcon;

    private Double surfaceTerrasse;

    private Double surfaceVerandas;

    private Double surfaceSousSol;

    private Double surfaceCave;

    private Double surfaceLogias;

    private Double autreSurfaceAnnexe;

}

