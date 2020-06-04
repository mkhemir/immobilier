package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatMalrauxDTO;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.InfoTmi;
import com.immoapp.audits.utile.MalrauxConstants;

public class LoiMalrauxCalcul {

    public ResultatMalrauxDTO calculerLoiMalraux(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, Double taeg,
                                                 double apport, double revenus, boolean estCouple, int nbrEnfants) {
        ResultatMalrauxDTO resultatMalrauxDTO = new ResultatMalrauxDTO();
        double impotsPayes = new InfoTmi(revenus, estCouple , nbrEnfants).getImpots();
        resultatMalrauxDTO.setDureeTravaux(MalrauxConstants.DUREE_TRAVAUX_MAX);
        resultatMalrauxDTO.setCoutTravaux(produitImmobilierDTO.getCoutTravaux());
        double tauxReduction = getTauxReduction(produitImmobilierDTO);
        double montantReduction = (produitImmobilierDTO.getCoutTravaux() * tauxReduction) > 120 ? 120 :
                produitImmobilierDTO.getCoutTravaux() * tauxReduction;
        // TPFIX 4 ANS TRAVAUX + 3 ANS REPORT DE DEFICIT @ verfier
        double economyImpots = impotsPayes * 7 > montantReduction ? montantReduction / dureeCredit : (impotsPayes * 7) / dureeCredit;
        resultatMalrauxDTO.setEconomyImpots(economyImpots);
        resultatMalrauxDTO.setTauxReductionImpots(tauxReduction);
        /* a revoir les frais de notaire 8% pour l'ancien et 2.5% pour le neuf qui sont calculé que sur le foncier (PRIX APPART)
          pour les lois malraux et monument historique
         */
        // TOFIX LES PREMIERES ANNEES DES TRAVAUX IL YA PAS DES REVENUS FONCIER
        resultatMalrauxDTO.setEffortEpargne(economyImpots + produitImmobilierDTO.getLoyerEstime() // * ((1 - 2 / dureeCredit)
                - CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue()
                + produitImmobilierDTO.getCoutTravaux(), dureeCredit, taeg, apport, produitImmobilierDTO.isEstNeuf()));

        return resultatMalrauxDTO;
    }

    public double getTauxReduction(ProduitImmobilierDTO produitImmobilierDTO) {
        if (produitImmobilierDTO.isEstSitePSMV()) {
            return MalrauxConstants.TAUX_PSMV;
        } else if (produitImmobilierDTO.isEstSitePVAP()) {
            return MalrauxConstants.TAUX_PVAP;
        } else
            return 0;
    }
}
