package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    private String nom;

    private String prenom;

    private String telephone;

    private String email;

    private double revenusBrut;

    private boolean married;

    private int nbrEnfants;

    private double apport;

    private List<BienImmobilierDTO> autresBiens;
}
