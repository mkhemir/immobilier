package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatLmnpDto;
import com.immoapp.audits.enums.LoyerEstimation;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.InfoTmi;
import com.immoapp.audits.utile.LmnpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class LoiLmnpCalcul {

    private double tmi;
    private double apport;
    private static final Logger logger = LoggerFactory.getLogger(LoiPinelCalcul.class);

    public LoiLmnpCalcul(double revenus, boolean estCouple, int nbrEnfants, double revenusLoyer, double apport) {
        tmi = new InfoTmi(revenus + revenusLoyer, estCouple, nbrEnfants).getTmi();
        this.apport = apport;
    }

    public ResultatLmnpDto getRegimeReel(ProduitImmobilierDTO produitImmobilier, double taeg, int dureeCredit) {
        ResultatLmnpDto resultatLmnpDto = new ResultatLmnpDto();
        double loyer = produitImmobilier.getLoyerEstime();
        double amortissementImmobilier = (produitImmobilier.getPrix().doubleValue() * (1 - LmnpConstants.COEFF_VALEUR_TERRAIN)) / 30;
        double amortissementMobilier = LmnpConstants.COUT_MOBILIER / LmnpConstants.DUREE_MOBILIER;
        double mensualiteCredit = CommonConstants.calculerMensulaiteCredit(produitImmobilier.getPrix().doubleValue() , dureeCredit, taeg, this.apport, produitImmobilier.isEstNeuf());
        resultatLmnpDto.setAbbatement50(0);
        resultatLmnpDto.setAmortissementImmobilier(adaptNumber(amortissementImmobilier *1));
        resultatLmnpDto.setLoyerAnnuel(adaptNumber(loyer * 12));
        resultatLmnpDto.setAmortissementMobilier(adaptNumber((LmnpConstants.COUT_MOBILIER / LmnpConstants.DUREE_MOBILIER)));
        resultatLmnpDto.setInteretEmprunt(adaptNumber(amortissementMobilier *1));
        resultatLmnpDto.setAutresCharges(LmnpConstants.AUTRES_CHARGES);
        resultatLmnpDto.setTotalCharges(adaptNumber(resultatLmnpDto.getAmortissementImmobilier() + resultatLmnpDto.getAmortissementMobilier() +
                resultatLmnpDto.getAutresCharges() + resultatLmnpDto.getAbbatement50() + resultatLmnpDto.getInteretEmprunt()));
        double resultatBrut = resultatLmnpDto.getLoyerAnnuel() - resultatLmnpDto.getTotalCharges();
        resultatLmnpDto.setResultat(adaptNumber(resultatBrut));
        double totalImpots = resultatBrut > 0 ? resultatBrut * (this.tmi + LmnpConstants.COEFF_CSG) : 0.0;
        resultatLmnpDto.setTotalImpots(adaptNumber(totalImpots));
        double economyImpot = (produitImmobilier.getLoyerEstime() * (this.tmi + LmnpConstants.COEFF_CSG)) - (totalImpots / 12);
        resultatLmnpDto.setEconomyImpots(adaptNumber(economyImpot));

        resultatLmnpDto.setMensualiteCredit(adaptNumber(mensualiteCredit));
        resultatLmnpDto.setEffortEpargne(adaptNumber(produitImmobilier.getLoyerEstime() + economyImpot - mensualiteCredit));

        logger.info("amort immobilier :"+ resultatLmnpDto.getAmortissementImmobilier());
        logger.info("mensualites credits :"+ mensualiteCredit);
        logger.info("interet emprunt  :"+ resultatLmnpDto.getInteretEmprunt());
        logger.info("amort mobilier :"+ resultatLmnpDto.getAmortissementMobilier());
        logger.info("resultatBrut :"+ resultatBrut);
        logger.info("total charges" + resultatLmnpDto.getTotalCharges());
        logger.info("totalImpots" + totalImpots);
        logger.info("economy Impots" + economyImpot);
        logger.info("impots loyer" + produitImmobilier.getLoyerEstime() * 12 * (this.tmi + LmnpConstants.COEFF_CSG));
        return resultatLmnpDto;
    }

    public ResultatLmnpDto getRegimeMicro(ProduitImmobilierDTO produitImmobilier, int dureeCredit, double taeg) {
        ResultatLmnpDto resultatLmnpDto = new ResultatLmnpDto();
        double loyer = produitImmobilier.getLoyerEstime();
        double mensualiteCredit = CommonConstants.calculerMensulaiteCredit(produitImmobilier.getPrix().doubleValue() , dureeCredit, taeg, this.apport, produitImmobilier.isEstNeuf());
        resultatLmnpDto.setLoyerAnnuel(adaptNumber(loyer * 12));
        resultatLmnpDto.setAbbatement50(adaptNumber(loyer * 6));
        resultatLmnpDto.setAmortissementImmobilier(0);
        resultatLmnpDto.setAmortissementMobilier(0);
        resultatLmnpDto.setInteretEmprunt(0);
        resultatLmnpDto.setAutresCharges(0);
        resultatLmnpDto.setTotalCharges(adaptNumber(loyer * 6));
        double resultatBrut = resultatLmnpDto.getLoyerAnnuel() - resultatLmnpDto.getTotalCharges();
        double totalImpots = resultatBrut * (this.tmi + LmnpConstants.COEFF_CSG);
        resultatLmnpDto.setResultat(adaptNumber(resultatBrut));
        resultatLmnpDto.setTotalImpots(adaptNumber(totalImpots));
        double economyImpot = (loyer * (this.tmi + LmnpConstants.COEFF_CSG)) - (totalImpots / 12);
        resultatLmnpDto.setEconomyImpots(adaptNumber(economyImpot));
        resultatLmnpDto.setMensualiteCredit(adaptNumber(mensualiteCredit));
        resultatLmnpDto.setEffortEpargne(adaptNumber(loyer + economyImpot - mensualiteCredit));
        return resultatLmnpDto;
    }

    public double adaptNumber(double n) {
        return Math.round(n * 100.0) / 100.0;
    }
}
