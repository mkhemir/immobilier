package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultatLmnpDto {

    private double loyerAnnuel;
    private double amortissementImmobilier;
    private double amortissementMobilier;
    private double InteretEmprunt;
    private double autresCharges;
    private double abbatement50;
    private double impots;
    private double totalCharges;
    private double totalImpots;
    private double Resultat;
    private double economyImpots;
    private double effortEpargne;

}
