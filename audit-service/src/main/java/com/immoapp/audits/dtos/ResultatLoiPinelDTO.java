package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResultatLoiPinelDTO {
    private double loyerMaximum;
    private double reductionImpots;
    private double montantEmprunt;
    private double economyImpots;
    private double mensualiteCredit;
    private double fraisAnnexe;
    private double effortEpargne;
}
