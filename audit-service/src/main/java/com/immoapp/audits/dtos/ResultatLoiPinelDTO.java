package com.immoapp.audits.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResultatLoiPinelDTO {
    private double loyerMaximum;
    private double reductionImpots6ans;
    private double reductionImpots9ans;
    private double reductionImpots12ans;
}
