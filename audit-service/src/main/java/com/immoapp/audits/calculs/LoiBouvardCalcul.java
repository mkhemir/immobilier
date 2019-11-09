package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.utile.BouvardConstants;

public class LoiBouvardCalcul {

    public double calculerTvaImmobilier(double prixProduit){
        return prixProduit * BouvardConstants.COEFF_TVA_IMMOBILIER;
    }

    //TODO
    public double estimerMontantLoeyr(ProduitImmobilierDTO produitImmobilierDTO){
        return 800;
    }

    public double calculerMontantRecupere(double prixHt){
        return prixHt * BouvardConstants.COEFF_REDUCTION;
    }

    public double calculerCoutFinalInvestissement(double prixHt){
        return prixHt * (1 - BouvardConstants.COEFF_REDUCTION);
    }
}
