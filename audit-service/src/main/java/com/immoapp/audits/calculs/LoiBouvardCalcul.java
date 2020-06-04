package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.utile.BouvardConstants;
import com.immoapp.audits.utile.CommonConstants;

public class LoiBouvardCalcul {

    public double calculerEconomyImpots(double prixTTC, int dureeCredit) {
        return (CommonConstants.getPrixHT(prixTTC) * BouvardConstants.COEFF_REDUCTION) / dureeCredit;
    }

    public double calculerTvaRecuperee(double prixTTC) {
        return (prixTTC - CommonConstants.getPrixHT(prixTTC));
    }

    public double calculerEffortEpagneSansTVA(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, double taeg, double apport) {
        return CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue(), dureeCredit, taeg, apport, true)
                - calculerEconomyImpots(produitImmobilierDTO.getPrix().doubleValue(), dureeCredit) - produitImmobilierDTO.getLoyerEstime();
    }

    public double calculerEffortEpagne(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, double taeg, double apport) {
        return (calculerEconomyImpots(produitImmobilierDTO.getPrix().doubleValue(), dureeCredit) + produitImmobilierDTO.getLoyerEstime()
                + (calculerTvaRecuperee(produitImmobilierDTO.getPrix().doubleValue()) / dureeCredit)
                - CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue(), dureeCredit, taeg, apport, true));
    }
}
