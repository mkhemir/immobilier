package com.immoapp.db.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name = "PRODUIT_IMMOBILIER")
@Entity
public class ProduitImmobilier {

    /**
     * the ID of the product.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * the type of the product.
     */
    @Column(name = "TYPE")
    private String type;
    /**
     * the phone number to contact.
     */
    @Column(name = "TELEPHONE")
    private String telephone;
    /**
     * the number of lots.
     */
    @Column(name = "NOMBRE_LOTS")
    private Integer nbr_lots;
    /**
     * does it contain a parking.
     */
    @Column(name = "PARKING")
    private Boolean isParking;
    /**
     * does it contain a lift.
     */
    @Column(name = "ASCENCEUR")
    private Boolean isLift;
    /**
     * the dpe.
     */
    @Column(name = "DPE")
    private char dpe;
    /**
     * charges copropriété.
     */
    @Column(name = "CHARGES_COPROP")
    private double chargesCoprop;
    /**
     * taxes fonciaires.
     */
    @Column(name = "TAXES_FONCIAIRES")
    private String taxeFonciaire;
    /**
     * date de construction.
     */
    @Column(name = "ANNEE_CONSTRUCTION")
    private Date dateConstruction;
    /**
     * THE PRODUCT ADDRESS.
     */
    @Column(name = "ADRESSE")
    private String adresse;
    /**
     * THE PRODUCT PRICE.
     */
    @Column(name = "PRIX")
    private BigDecimal prix;

    @Column(name = "SURFACE")
    private Double surface;

    @Column(name = "SURFACE_BALCON")
    private Double surfaceBalcon;

    @Column(name = "SURFACE_TERRASSE")
    private Double surfaceTerrasse;

    @Column(name = "SURFACE_VERANDAS")
    private Double surfaceVerandas;

    @Column(name = "SURFACE_SOUS_SOL")
    private Double surfaceSousSol;

    @Column(name = "SURFACE_CAVE")
    private Double surfaceCave;

    @Column(name = "SURFACE_LOGIAS")
    private Double surfaceLogias;

    @Column(name = "AUTRE_SURFACE_ANNEXE")
    private Double autreSurfaceAnnexe;

    @OneToMany(
            cascade = CascadeType.ALL , fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "IMAGE_ID")
    List<ImageProduit> images;

}
