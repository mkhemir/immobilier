package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String nom;

    private String prenom;

    private String telephone;

    private String email;

    private double salaire;

    private boolean married;

    private int nbrEnfants;
}
