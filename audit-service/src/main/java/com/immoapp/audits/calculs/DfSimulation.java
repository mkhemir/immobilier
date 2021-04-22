package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.BienImmobilierDTO;
import com.immoapp.audits.dtos.DeficitFoncierDTO;
import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.UserDTO;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.InfoTmi;

import java.time.Year;

public class DfSimulation {

    DeficitFoncierDTO deficitFoncierDTO;
    double taeg;
    private UserDTO userDTO;
    private int currentYear;
    private ProduitImmobilierDTO produitImmobilierDTO;
    private int dureeCredit;

    public DfSimulation(DeficitFoncierDTO deficitFoncierDTO, double taeg, UserDTO userDTO, ProduitImmobilierDTO produitImmobilierDTO, int dureeCredit) {
        this.deficitFoncierDTO = deficitFoncierDTO;
        this.taeg = taeg;
        this.userDTO = userDTO;
        this.currentYear = Year.now().getValue();
        this.produitImmobilierDTO = produitImmobilierDTO;
        this.dureeCredit = dureeCredit;
        this.deficitFoncierDTO.setMensualiteCredit(CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue(),
                dureeCredit, taeg, userDTO.getApport(), false));
    }

    public double calculetTotalRevenusFoncier() {
        double revenusFoncier = produitImmobilierDTO.getLoyerEstime() * 12;
        if (checkPeriodeTravaux()) {
            this.deficitFoncierDTO.setTotalRevenusFoncier(userDTO.getAutresBiens().stream().mapToDouble(BienImmobilierDTO::getRevenusFoncierAnnuel).sum());
        } else {
            this.deficitFoncierDTO.setTotalRevenusFoncier(revenusFoncier + userDTO.getAutresBiens().stream()
                    .mapToDouble(BienImmobilierDTO::getRevenusFoncierAnnuel).sum());
        }
        return this.deficitFoncierDTO.getTotalRevenusFoncier();
    }

    public double calculetTotalInteretEmprunt() {
        double montantEmprunt = CommonConstants.calculerMontantEmprunt(this.produitImmobilierDTO.getPrix().doubleValue() + this.produitImmobilierDTO.getCoutTravaux(),
                this.userDTO.getApport(), this.produitImmobilierDTO.isEstNeuf());
        this.deficitFoncierDTO.setMontantEmprunt(montantEmprunt);
        double interetEmprunt = CommonConstants.getInteretEmprunt(montantEmprunt, this.taeg);
        this.deficitFoncierDTO.setInteretEmprunt(interetEmprunt);
        return interetEmprunt;

    }

    private boolean checkPeriodeTravaux() {
        if (this.currentYear + this.produitImmobilierDTO.getDureeTravaux() > this.deficitFoncierDTO.getAnnee()) {
            return true;
        }
        return false;
    }

    private double calculerImpots(double montant) {
        InfoTmi infoTmi = new InfoTmi(montant, userDTO.isMarried(), userDTO.getNbrEnfants());
        return infoTmi.getImpots();
    }

    public double calculerDeficitFoncier(double report) {
        double revenusFoncier = calculetTotalRevenusFoncier();
        double interetEmprunt = calculetTotalInteretEmprunt();
        double firstMarge = revenusFoncier - interetEmprunt;
        double revenusImposable= userDTO.getRevenusBrut();
        double coutTravauxAnnuel = this.produitImmobilierDTO.getCoutTravaux() / this.produitImmobilierDTO.getDureeTravaux();
        double impotsAvantReduction = revenusFoncier * CommonConstants.PRELEVEMENT_SOCIAUX + calculerImpots(revenusImposable + revenusFoncier);
        double impots = 0;
        double reportRevF = 0;
        double deficit = 0;
        if (firstMarge  > 0 ) {
            deficit = firstMarge - coutTravauxAnnuel;
            if(deficit  >= 0 ) {
                if(deficit - report  >= 0 ) {
                    deficit = deficit - report;
                } else {
                    deficit = 0;
                    reportRevF = Math.abs(deficit - report);
                }
                impots = deficit * CommonConstants.PRELEVEMENT_SOCIAUX + calculerImpots(revenusImposable + deficit);
            } else if ((deficit < 0) && (deficit >= -10700)) {
                impots = calculerImpots(revenusImposable + deficit);
            } else {
                impots = calculerImpots(revenusImposable - 10700);
                reportRevF += Math.abs(deficit) - 10700;
            }
        } else {
            reportRevF += Math.abs(firstMarge);
            deficit = -coutTravauxAnnuel;
            if (coutTravauxAnnuel >= 10700) {
                impots = calculerImpots(revenusImposable - 10700);
                deficit = -10700;
                reportRevF += coutTravauxAnnuel - 10700;
            } else {
                impots = calculerImpots(revenusImposable + deficit);
            }
        }
        this.deficitFoncierDTO.setDeficit(deficit);
        this.deficitFoncierDTO.setInteretEmprunt(interetEmprunt);
        this.deficitFoncierDTO.setTotalRevenusFoncier(revenusFoncier);
        this.deficitFoncierDTO.setTotalImpotsApresReduction(impots);
        this.deficitFoncierDTO.setEconomyImpots(impotsAvantReduction - impots);
        this.deficitFoncierDTO.setTotalImpotsAvantReduction(impotsAvantReduction);
        return impots;
    }
}
