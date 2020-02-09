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
    private Map<Integer, Double> gainImpots;
    private Map<Integer, Double> effortEpargne;
}
