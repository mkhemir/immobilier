package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultatBouvardDTO {

    private double montantTvaRecuperee;
    private double EconomyImpots;
    private double gainImpots9Ans;
    private double mensualiteCredit;
    private double effortEpargne;
    private double effortEpargneSansTVA;
}
