package com.immoapp.db.dtos;


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
    private Integer nbr_lots;
    /**
     * does it contain a parking.
     */
    private Boolean isParking;
    /**
     * does it contain a lift.
     */
    private Boolean isLift;
    /**
     * rooms.
     */
    public int nbrPiece;
    /**
     * bedrooms.
     */
    public int nbrChambre;
    /**
     * the dpe.
     */
    private char dpe;
    /**
     * charges copropriété.
     */
    private double chargesCoprop;
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

    public String codePostal;

    public String ville;
    /**
     * THE PRODUCT PRICE.
     */
    private BigDecimal prix;

    private double surface;

    private double surfaceBalcon;

    private double surfaceTerrasse;

    private double surfaceVerandas;

    private double surfaceSousSol;

    private double surfaceCave;

    private double surfaceLogias;

    private double autreSurfaceAnnexe;

    private double loyerEstime;

}
