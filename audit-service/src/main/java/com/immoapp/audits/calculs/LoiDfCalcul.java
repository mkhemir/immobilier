package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.DeficitFoncierDTO;
import com.immoapp.audits.utile.CommonConstants;

public class LoiDfCalcul {

    private double reportSurRevenus;
    private double tmi;

    public LoiDfCalcul(double salaire) {
        this.tmi = CommonConstants.getTmi(salaire);
    }

    public Double calculerDeficitFoncier(double revenusLoyer, double interetEmprunt, double chargesNonFinanciere) {
        if ((revenusLoyer - interetEmprunt - chargesNonFinanciere) > 0) {
            return calculerDfCase1(revenusLoyer, interetEmprunt, chargesNonFinanciere);
        } else if ((revenusLoyer - interetEmprunt) > 0 && (revenusLoyer - interetEmprunt - chargesNonFinanciere) <= 0 && (revenusLoyer - interetEmprunt - chargesNonFinanciere) >= -10700) {
            return calculerDfCase2(revenusLoyer, interetEmprunt, chargesNonFinanciere);
        } else if ((revenusLoyer - interetEmprunt) <= 0 && (revenusLoyer - interetEmprunt - chargesNonFinanciere) >= -10700) {
            return calculerDfCase3(revenusLoyer, interetEmprunt, chargesNonFinanciere);
        } else if ((revenusLoyer - interetEmprunt) > 0 && (revenusLoyer - interetEmprunt - chargesNonFinanciere) < -10700) {
            return calculerDfCase4(revenusLoyer, interetEmprunt, chargesNonFinanciere);
        } else {
            return null;
        }

    }

    public double calculerDfCase1(double revenusLoyer, double interetEmprunt, double chargesNonFinanciere) {
        if ((chargesNonFinanciere + interetEmprunt + this.reportSurRevenus) < revenusLoyer) {
            double oldRevenusFoncier = this.reportSurRevenus;
            this.reportSurRevenus = 0;
            return (this.tmi + CommonConstants.PRELEVEMENT_SOCIAUX) * oldRevenusFoncier;
        } else {
            this.reportSurRevenus = interetEmprunt + chargesNonFinanciere + this.reportSurRevenus - revenusLoyer;
            return (this.tmi + CommonConstants.PRELEVEMENT_SOCIAUX) * (interetEmprunt + chargesNonFinanciere - revenusLoyer);
        }
    }

    public double calculerDfCase2(double revenusLoyer, double interetEmprunt, double chargesNonFinanciere) {
        return (revenusLoyer - chargesNonFinanciere - interetEmprunt) * this.tmi;
    }

    public double calculerDfCase3(double revenusLoyer, double interetEmprunt, double chargesNonFinanciere) {
        this.reportSurRevenus += interetEmprunt - revenusLoyer;
        return chargesNonFinanciere * this.tmi;
    }

    public double calculerDfCase4(double revenusLoyer, double interetEmprunt, double chargesNonFinanciere) {
        double deficit = chargesNonFinanciere + interetEmprunt - revenusLoyer;
        this.reportSurRevenus += deficit - 10700;
        return 10700 * this.tmi;
    }
}
