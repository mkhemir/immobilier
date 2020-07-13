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
        double mensualitesCredit = CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue() +
                produitImmobilierDTO.getCoutTravaux(), dureeCredit, taeg, apport, produitImmobilierDTO.isEstNeuf());
        resultatMhDTO.setTmiInv(infoTmiInv.getTmi());
        resultatMhDTO.setTmiHab(infoTmiHab.getTmi());
        resultatMhDTO.setMensualiteCredit(adaptNumber(mensualitesCredit));
        resultatMhDTO.setDureeTravaux(MalrauxConstants.DUREE_TRAVAUX_MAX);
        resultatMhDTO.setCoutTravaux(adaptNumber(produitImmobilierDTO.getCoutTravaux()));
        resultatMhDTO.setEconomyImpotsInvest(adaptNumber(calculerEconomyImpots(infoTmiInv.getImpots(),
                produitImmobilierDTO.getCoutTravaux() * infoTmiInv.getTmi(), dureeCredit, true, resultatMhDTO)));
        resultatMhDTO.setEconomyImpotsHabit(adaptNumber(calculerEconomyImpots(infoTmiHab.getImpots(),
                0.5 * produitImmobilierDTO.getCoutTravaux() * infoTmiHab.getTmi(), dureeCredit, false, resultatMhDTO)));
        /* a revoir les frais de notaire 8% pour l'ancien et 2.5% pour le neuf qui sont calculé que sur le foncier (PRIX APPART)
          pour les lois malraux et monument historique
         */
        resultatMhDTO.setEffortEpargne(adaptNumber(resultatMhDTO.getEconomyImpotsInvest() / dureeCredit + produitImmobilierDTO.getLoyerEstime()
        - mensualitesCredit));

        resultatMhDTO.setMoyenneEffortEpargneHabit(adaptNumber(resultatMhDTO.getEconomyImpotsHabit() / dureeCredit + produitImmobilierDTO.getLoyerEstime()
        - mensualitesCredit));

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

    public double adaptNumber(double n) {
        return Math.round(n * 100.0) / 100.0;
    }
}
