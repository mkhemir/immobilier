package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatLmnpDto;
import com.immoapp.audits.enums.LoyerEstimation;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.InfoTmi;
import com.immoapp.audits.utile.LmnpConstants;

import java.math.BigDecimal;

public class LoiLmnpCalcul {

    private double tmi;
    private double apport;

    public LoiLmnpCalcul(double revenus, boolean estCouple, int nbrEnfants, double revenusLoyer, double apport) {
        tmi = new InfoTmi(revenus + revenusLoyer, estCouple, nbrEnfants).getTmi();
        this.apport = apport;
    }

    public ResultatLmnpDto getRegimeReel(ProduitImmobilierDTO produitImmobilier, double taeg, int dureeCredit) {
        ResultatLmnpDto resultatLmnpDto = new ResultatLmnpDto();
        double loyer = produitImmobilier.getLoyerEstime();
        resultatLmnpDto.setAbbatement50(0);
        resultatLmnpDto.setAmortissementImmobilier(produitImmobilier.getPrix().doubleValue() * (1 - LmnpConstants.COEFF_VALEUR_TERRAIN));
        resultatLmnpDto.setLoyerAnnuel(loyer * 12);
        resultatLmnpDto.setAmortissementMobilier(LmnpConstants.COUT_MOBILIER / LmnpConstants.DUREE_MOBILIER);
        resultatLmnpDto.setInteretEmprunt((produitImmobilier.getPrix().doubleValue() - this.apport) * taeg);
        resultatLmnpDto.setAutresCharges(LmnpConstants.AUTRES_CHARGES);
        resultatLmnpDto.setTotalCharges(resultatLmnpDto.getAmortissementImmobilier() + resultatLmnpDto.getAmortissementMobilier() +
                resultatLmnpDto.getAutresCharges() + resultatLmnpDto.getAbbatement50() + resultatLmnpDto.getInteretEmprunt());
        double resultatBrut = resultatLmnpDto.getLoyerAnnuel() - resultatLmnpDto.getTotalCharges();
        resultatLmnpDto.setResultat(resultatBrut);
        double totalImpots = resultatBrut > 0 ? resultatBrut * (this.tmi + LmnpConstants.COEFF_CSG) : 0.0;
        resultatLmnpDto.setTotalImpots(totalImpots);
        double economyImpot = (produitImmobilier.getLoyerEstime() * (this.tmi + LmnpConstants.COEFF_CSG)) - (totalImpots / 12);
        resultatLmnpDto.setEconomyImpots(economyImpot);
        resultatLmnpDto.setEffortEpargne(produitImmobilier.getLoyerEstime() + economyImpot -
                CommonConstants.calculerMensulaiteCredit(produitImmobilier.getPrix().doubleValue(), dureeCredit, taeg, this.apport, false));
        return resultatLmnpDto;
    }

    public ResultatLmnpDto getRegimeMicro(ProduitImmobilierDTO produitImmobilier, int dureeCredit, double taeg) {
        ResultatLmnpDto resultatLmnpDto = new ResultatLmnpDto();
        double loyer = produitImmobilier.getLoyerEstime();
        resultatLmnpDto.setLoyerAnnuel(loyer * 12);
        resultatLmnpDto.setAbbatement50(loyer * 6);
        resultatLmnpDto.setAmortissementImmobilier(0);
        resultatLmnpDto.setAmortissementMobilier(0);
        resultatLmnpDto.setInteretEmprunt(0);
        resultatLmnpDto.setAutresCharges(0);
        resultatLmnpDto.setTotalCharges(loyer * 6);
        double resultatBrut = resultatLmnpDto.getLoyerAnnuel() - resultatLmnpDto.getTotalCharges();
        double totalImpots = resultatBrut * (this.tmi + LmnpConstants.COEFF_CSG);
        resultatLmnpDto.setResultat(resultatBrut);
        resultatLmnpDto.setTotalImpots(totalImpots);
        double economyImpot = (loyer * (this.tmi + LmnpConstants.COEFF_CSG)) - (totalImpots / 12);
        resultatLmnpDto.setEconomyImpots(economyImpot);
        resultatLmnpDto.setEffortEpargne(loyer + economyImpot -
                CommonConstants.calculerMensulaiteCredit(produitImmobilier.getPrix().doubleValue(), dureeCredit, taeg, this.apport, false));
        return resultatLmnpDto;
    }
}
