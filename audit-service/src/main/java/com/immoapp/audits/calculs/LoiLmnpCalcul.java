package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ResultatLmnpDto;
import com.immoapp.audits.enums.LoyerEstimation;
import com.immoapp.audits.utile.LmnpConstants;

import java.math.BigDecimal;

public class
LoiLmnpCalcul {

    public ResultatLmnpDto getRegimeReel(double loyer, double prixProduit, double tauxCredit) {
        ResultatLmnpDto resultatLmnpDto = new ResultatLmnpDto();
        resultatLmnpDto.setAbbatement50(0);
        resultatLmnpDto.setAmortissementImmobilier(prixProduit * (1 - LmnpConstants.COEFF_VALEUR_TERRAIN));
        resultatLmnpDto.setLoyerAnnuel(loyer * 12);
        resultatLmnpDto.setAmortissementMobilier(LmnpConstants.COUT_MOBILIER / LmnpConstants.DUREE_MOBILIER);
        resultatLmnpDto.setInteretEmprunt(prixProduit * tauxCredit);
        resultatLmnpDto.setAutresCharges(LmnpConstants.AUTRES_CHARGES);
        resultatLmnpDto.setTotalCharges(resultatLmnpDto.getAmortissementImmobilier() + resultatLmnpDto.getAmortissementMobilier() +
                resultatLmnpDto.getAutresCharges() + resultatLmnpDto.getAbbatement50() + resultatLmnpDto.getInteretEmprunt());
        resultatLmnpDto.setResultat(resultatLmnpDto.getLoyerAnnuel() - resultatLmnpDto.getTotalCharges());
        resultatLmnpDto.setTotalImpots(resultatLmnpDto.getLoyerAnnuel() * (LmnpConstants.TAUX_IMPOSITION + LmnpConstants.COEFF_CSG));
        return resultatLmnpDto;
    }

    public ResultatLmnpDto getRegimeMicro(double loyer, double prixProduit, double tauxCredit) {
        ResultatLmnpDto resultatLmnpDto = new ResultatLmnpDto();
        resultatLmnpDto.setLoyerAnnuel(loyer * 12);
        resultatLmnpDto.setAbbatement50(loyer * 6);
        resultatLmnpDto.setAmortissementImmobilier(0);
        resultatLmnpDto.setAmortissementMobilier(0);
        resultatLmnpDto.setInteretEmprunt(0);
        resultatLmnpDto.setAutresCharges(0);
        resultatLmnpDto.setTotalCharges(loyer * 6);
        resultatLmnpDto.setResultat(resultatLmnpDto.getLoyerAnnuel() - resultatLmnpDto.getTotalCharges());
        resultatLmnpDto.setTotalImpots(resultatLmnpDto.getLoyerAnnuel() * (LmnpConstants.TAUX_IMPOSITION + LmnpConstants.COEFF_CSG));
        return resultatLmnpDto;
    }
}
