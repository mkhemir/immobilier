package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultatMhDTO {

    private double tmiInv;

    private double tmiHab;

    // nombre d4annee de deduction sur les impots des charges fonciers li2s aux travaux
    private double dureeXenorationImpotInv;

    private double dureeXenorationImpotHab;

    private double mensualiteCredit;

    private double coutTravaux;

    private double economyImpotsInvest;

    private double economyImpotsHabit;

    private int dureeTravaux;

    private double moyenneEffortEpargneInvest;

    private double moyenneEffortEpargneHabit;


}
