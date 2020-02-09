package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatMhDTO;
import com.immoapp.audits.dtos.UserDTO;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.MalrauxConstants;

public class LoiMhCalcul {

    public ResultatMhDTO calculerLoiMh(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, double taeg, double salaire){
        ResultatMhDTO resultatMhDTO = new ResultatMhDTO();
        resultatMhDTO.setTmi(getTmi(salaire));
        resultatMhDTO.setMensualiteCredit(CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue()
                +produitImmobilierDTO.getCoutTravaux(),dureeCredit, taeg));
        resultatMhDTO.setDureeTravaux(MalrauxConstants.DUREE_TRAVAUX_MAX);
        resultatMhDTO.setCoutTravaux(produitImmobilierDTO.getCoutTravaux());
        resultatMhDTO.setEconomyImpotsInvest(produitImmobilierDTO.getCoutTravaux() * getTmi(salaire));
        resultatMhDTO.setEconomyImpotsHabit(0.5 * produitImmobilierDTO.getCoutTravaux() * getTmi(salaire));
        /* a revoir les frais de notaire 8% pour l'ancien et 2.5% pour le neuf qui sont calculé que sur le foncier (PRIX APPART)
          pour les lois malraux et monument historique
         */
        resultatMhDTO.setMoyenneEffortEpargneInvest(resultatMhDTO.getMensualiteCredit()
                - resultatMhDTO.getEconomyImpotsInvest()/dureeCredit - estimerMontantLoyer(produitImmobilierDTO));

        resultatMhDTO.setMoyenneEffortEpargneHabit(resultatMhDTO.getMensualiteCredit()
                - resultatMhDTO.getEconomyImpotsHabit()/dureeCredit - estimerMontantLoyer(produitImmobilierDTO));

        return resultatMhDTO;
    }

    public double getTmi(double salaire){
        if(salaire < 9964){
            return 0;
        } else if(salaire > 9964 && salaire < 27519){
            return 0.14;
        }else if(salaire > 27519 && salaire < 73779) {
            return 0.3;
        }else if(salaire > 73779 && salaire < 156244) {
            return 0.41;
        }else {
            return 0.45;
        }
    }

    //TODO
    public double estimerMontantLoyer(ProduitImmobilierDTO produitImmobilierDTO) {
        return 700;
    }
}
