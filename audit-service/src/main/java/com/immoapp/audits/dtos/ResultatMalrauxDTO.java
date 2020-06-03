package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultatMalrauxDTO {

    private double tauxReductionImpots;

    private double coutTravaux;

    private double economyImpots;

    private int dureeTravaux;

    private double effortEpargne;

    private List<Double> listEffortEpargnes;


}
