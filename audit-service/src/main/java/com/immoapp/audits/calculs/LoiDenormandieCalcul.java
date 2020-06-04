package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatDenormandieDTO;
import com.immoapp.audits.utile.CommonConstants;

public class LoiDenormandieCalcul {

    private ResultatDenormandieDTO denormandie;
    private ProduitImmobilierDTO produitImmobilierDTO;

    public LoiDenormandieCalcul(ResultatDenormandieDTO denormandie, double montantCredit, int dureeCredit, double taeg,
                                ProduitImmobilierDTO produit, double apport) {
        this.denormandie = denormandie;
        this.produitImmobilierDTO = produit;
        denormandie.setMensualiteCredit(CommonConstants.calculerMensulaiteCredit(montantCredit, dureeCredit, taeg, apport, false));
    }

    public double calculerReductionImpots9Ans() {
        this.denormandie.setReductionImpots9AnsPinel(((0.25 * this.produitImmobilierDTO.getCoutTravaux())
                + this.produitImmobilierDTO.getPrix().doubleValue()) * 0.02);
        return this.denormandie.getReductionImpots9AnsPinel();
    }

    public double calculerReductionImpots3Ans() {
        this.denormandie.setReductionImpots3dAnsPinel(((0.25 * this.produitImmobilierDTO.getCoutTravaux())
                + this.produitImmobilierDTO.getPrix().doubleValue()) * 0.01);
        return this.denormandie.getReductionImpots3dAnsPinel();
    }

    public double calculerReductionImpotsDF(double tmi) {
        this.denormandie.setReductionImpotsDeficitFoncier((0.75 * this.produitImmobilierDTO.getCoutTravaux()) * tmi);
        return this.denormandie.getReductionImpotsDeficitFoncier();
    }

    public double calculerEconomyImpots(double tmi) {
        double reduction9ans = calculerReductionImpots9Ans();
        double reduction3ans = calculerReductionImpots3Ans();
        double reductionDeficit = calculerReductionImpotsDF(tmi);
        double economyImpots = (reduction3ans + reduction9ans + reductionDeficit) / 144; //12ans
        this.denormandie.setEconomyImpots(economyImpots);
        return economyImpots;
    }

    public void calculerEffortEpargne(double tmi) {
       double economyImpots = calculerEconomyImpots(tmi);
       double effortEpargne = this.produitImmobilierDTO.getLoyerEstime() + economyImpots - this.denormandie.getMensualiteCredit();
       this.denormandie.setEffortEpargne(effortEpargne);
    }
}
