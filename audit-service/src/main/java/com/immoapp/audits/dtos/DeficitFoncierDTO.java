package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DeficitFoncierDTO {

    private double revenusLoyer;
    private double interetEmprunt;
    private double chargesNonFinanciere;
    private double deficit;
    private double economyImpots;
    private double effortEpargne;
    private double mensualiteCredit;
    private Map<Integer, Double> gainImpots;
    private Map<Integer, Double> listEffortEpargne;
}
