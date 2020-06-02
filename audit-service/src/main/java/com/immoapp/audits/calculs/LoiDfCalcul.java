package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.DeficitFoncierDTO;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.InfoTmi;

public class LoiDfCalcul {

    private double reportSurRevenus;
    private double tmi;
    private DeficitFoncierDTO deficitFoncierDTO;

    public LoiDfCalcul(double salaire, boolean estCouple , int nbreEnfants, DeficitFoncierDTO deficit) {
        this.tmi = new InfoTmi(salaire, estCouple, nbreEnfants).getTmi();
        this.deficitFoncierDTO = deficit;
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

    public double calculerEconoImpots(double revenusLoyer, double interetEmprunt, double chargesNonFinanciere, double revenus , boolean estCouple , int nbrEnfants) {
        double deficit = chargesNonFinanciere + interetEmprunt - revenusLoyer;
        if (deficit > 10700) {
            deficit = 10700;
        }
        this.deficitFoncierDTO.setDeficit(deficit);
        double oldTmi = new InfoTmi(revenus + revenusLoyer , estCouple , nbrEnfants).getTmi();
        double newTmi = new InfoTmi(revenus - deficit , estCouple , nbrEnfants).getTmi();
        double normalTaxe = (revenus + revenusLoyer) * oldTmi + revenusLoyer * CommonConstants.PRELEVEMENT_SOCIAUX;
        double newTaxe = 0.0;
        if (deficit > 0) {
            newTaxe = (revenus - deficit) * newTmi;
        } else {
            newTaxe = (revenus - deficit) * newTmi - deficit * CommonConstants.PRELEVEMENT_SOCIAUX;
        }
        return (normalTaxe - newTaxe) / 12;
    }
}
