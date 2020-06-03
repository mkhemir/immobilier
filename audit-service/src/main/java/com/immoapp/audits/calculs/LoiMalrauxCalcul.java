package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatMalrauxDTO;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.MalrauxConstants;

public class LoiMalrauxCalcul {

    public ResultatMalrauxDTO calculerLoiMalraux(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, Double taeg, double apport) {
        ResultatMalrauxDTO resultatMalrauxDTO = new ResultatMalrauxDTO();
        resultatMalrauxDTO.setDureeTravaux(MalrauxConstants.DUREE_TRAVAUX_MAX);
        resultatMalrauxDTO.setCoutTravaux(produitImmobilierDTO.getCoutTravaux());
        resultatMalrauxDTO.setEconomyImpots(produitImmobilierDTO.getCoutTravaux() * getTauxReduction(produitImmobilierDTO));
        resultatMalrauxDTO.setTauxReductionImpots(getTauxReduction(produitImmobilierDTO));
        /* a revoir les frais de notaire 8% pour l'ancien et 2.5% pour le neuf qui sont calculé que sur le foncier (PRIX APPART)
          pour les lois malraux et monument historique
         */
        resultatMalrauxDTO.setEffortEpargne(resultatMalrauxDTO.getEconomyImpots() / dureeCredit + produitImmobilierDTO.getLoyerEstime()
                - CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue()
                + produitImmobilierDTO.getCoutTravaux() - apport, dureeCredit, taeg));

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
