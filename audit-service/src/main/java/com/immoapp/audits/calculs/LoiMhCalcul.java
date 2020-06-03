package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatMhDTO;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.InfoTmi;
import com.immoapp.audits.utile.MalrauxConstants;

public class LoiMhCalcul {

    public ResultatMhDTO calculerLoiMh(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, double taeg,
                                       double revenus, boolean estCouple, int nbrEnfants, double apport) {
        ResultatMhDTO resultatMhDTO = new ResultatMhDTO();
        InfoTmi infoTmiInv = new InfoTmi(revenus + (produitImmobilierDTO.getLoyerEstime() * 12), estCouple, nbrEnfants);
        InfoTmi infoTmiHab = new InfoTmi(revenus, estCouple, nbrEnfants);
        resultatMhDTO.setTmiInv(infoTmiInv.getTmi());
        resultatMhDTO.setTmiHab(infoTmiHab.getTmi());
        resultatMhDTO.setMensualiteCredit(CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue()
                + produitImmobilierDTO.getCoutTravaux(), dureeCredit, taeg));
        resultatMhDTO.setDureeTravaux(MalrauxConstants.DUREE_TRAVAUX_MAX);
        resultatMhDTO.setCoutTravaux(produitImmobilierDTO.getCoutTravaux());
        resultatMhDTO.setEconomyImpotsInvest(calculerEconomyImpots(infoTmiInv.getImpots(),
                produitImmobilierDTO.getCoutTravaux() * infoTmiInv.getTmi(), dureeCredit, true, resultatMhDTO));
        resultatMhDTO.setEconomyImpotsHabit(calculerEconomyImpots(infoTmiHab.getImpots(),
                0.5 * produitImmobilierDTO.getCoutTravaux() * infoTmiHab.getTmi(), dureeCredit, false, resultatMhDTO));
        /* a revoir les frais de notaire 8% pour l'ancien et 2.5% pour le neuf qui sont calculé que sur le foncier (PRIX APPART)
          pour les lois malraux et monument historique
         */
        resultatMhDTO.setEffortEpargne(resultatMhDTO.getEconomyImpotsInvest() / dureeCredit + produitImmobilierDTO.getLoyerEstime()
        - CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue() +
                produitImmobilierDTO.getCoutTravaux() - apport, dureeCredit, taeg));

        resultatMhDTO.setMoyenneEffortEpargneHabit(resultatMhDTO.getEconomyImpotsHabit() / dureeCredit + produitImmobilierDTO.getLoyerEstime()
        - CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue() +
                produitImmobilierDTO.getCoutTravaux() - apport, dureeCredit, taeg));

        return resultatMhDTO;
    }

    private double calculerEconomyImpots(double impotAnnuel, double gainImpotsPossible, int dureeCredit, boolean estInvest, ResultatMhDTO resultatMh) {
        // la d2duction peut etre reporte sur 6 ans max
        if (gainImpotsPossible > impotAnnuel * 6) {
            calculerEconomyImpots(impotAnnuel, impotAnnuel * 6, dureeCredit, estInvest, resultatMh);
        } else {
            if (estInvest) {
                resultatMh.setDureeXenorationImpotInv(Math.ceil(gainImpotsPossible / impotAnnuel));
            } else {
                resultatMh.setDureeXenorationImpotHab(Math.ceil(gainImpotsPossible / impotAnnuel));
            }
            return gainImpotsPossible / dureeCredit;
        }
        return 0.0;
    }


}
