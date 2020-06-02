package com.immoapp.db.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "IMAGE_PRODUIT")
@Entity
public class ImageProduit {

    /**
     * the ID of the product.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "NAME")
    String name;
    @Column(name = "FILE")
    @Lob
    byte[] file;
}
