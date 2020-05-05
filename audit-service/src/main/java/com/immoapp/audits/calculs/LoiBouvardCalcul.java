package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.utile.BouvardConstants;
import com.immoapp.audits.utile.CommonConstants;

public class LoiBouvardCalcul {

    //TODO
    public double estimerMontantLoyer(ProduitImmobilierDTO produitImmobilierDTO) {
        return 700;
    }


    public double calculerEconomyImpots(double prixTTC) {
        return (CommonConstants.getPrixHT(prixTTC) * BouvardConstants.COEFF_REDUCTION) / BouvardConstants.DUREE_BOUVARD_JOURS;
    }

    public double calculerTvaRecuperee(double prixTTC) {
        return (prixTTC - CommonConstants.getPrixHT(prixTTC));
    }

    public double calculerEffortEpagne(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, double taeg) {
        return CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue(), dureeCredit, taeg)
                - calculerEconomyImpots(produitImmobilierDTO.getPrix().doubleValue()) - estimerMontantLoyer(produitImmobilierDTO);
    }

    public double calculerEffortEpagneTvaIncluse(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, double taeg) {
        return (CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue(), dureeCredit, taeg)
                - calculerEconomyImpots(produitImmobilierDTO.getPrix().doubleValue()) - estimerMontantLoyer(produitImmobilierDTO)
                - (calculerTvaRecuperee(produitImmobilierDTO.getPrix().doubleValue()) / dureeCredit));
    }
}
