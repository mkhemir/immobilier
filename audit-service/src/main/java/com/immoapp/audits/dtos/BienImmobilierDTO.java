package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BienImmobilierDTO {

    private double prix;
    private double revenusFoncierAnnuel;
    private double interetEmprunt;
    private double autresCharges;
    private Date dateFinCredit;
    private double coutTravaux;
}
