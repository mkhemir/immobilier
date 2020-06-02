package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultatDenormandieDTO {

    private double economyImpots;
    private double mensualiteCredit;
    private double effortEpargne;
    private double reductionImpots9AnsPinel;
    private double reductionImpots3dAnsPinel;
    private double reductionImpotsDeficitFoncier;
}
