package com.immoapp.audits.utile;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;

import java.time.Year;

public class CommonConstants {

    public static final double LOYER_ESTIME_1P = 800;
    public static final double LOYER_ESTIME_2P = 1200;
    public static final double LOYER_ESTIME_3P = 1600;
    public static final double LOYER_ESTIME_4P = 2000;
    public static final double LOYER_ESTIME_5P = 2400;
    public static final double LOYER_ESTIME_XP = 3000;
    public final static int DEFAULT_DUREE_CREDIT = 300;
    public final static double TAEG = 0.015;
    public final static double COEFF_PRIX_HT = 1.2;
    public final static double PRELEVEMENT_SOCIAUX = 0.155;
    public final static double SALAIRE_DEFAUT = 50000;
    public final static double COEFF_FN_ANCIEN = 0.08;

    public static double calculerMontantEmprunt(double prixAchat, Double apport, boolean estNeuf) {
        double coef = estNeuf ? PinelConstants.COEFF_FN : COEFF_FN_ANCIEN;
        apport = apport != null ? apport : prixAchat * PinelConstants.COEFF_APPORT_DEFAULT;
        return prixAchat + (prixAchat * coef) - apport;
    }

    public static double calculerMensulaiteCredit(double prixAchat, Integer dureeCredit, Double taeg, double apport, boolean estNeuf) {
        double montantEmprunt = calculerMontantEmprunt(prixAchat, apport, estNeuf);
        double coefTaegBrut = taeg != null ? taeg : TAEG;
        dureeCredit = dureeCredit != null ? dureeCredit : DEFAULT_DUREE_CREDIT;
        double coef = coefTaegBrut / 12;
        return (montantEmprunt * coef) / (1 - Math.pow(1 + coef, -dureeCredit));
    }

    public static double getPrixHT(double prix) {
        return prix / COEFF_PRIX_HT;

    }


    public static double getChargesNonFinancieres(ProduitImmobilierDTO produitImmobilierDTO) {
        return 7000;
    }

    public static double getInteretEmprunt(double montant , double taeg) {
        return montant * taeg;
    }

    public static double estimerMontantLoyer(ProduitImmobilierDTO produitImmobilierDTO) {
        return 416;
    }

    public static int getCurrentYear() {
        return Year.now().getValue();
    }

    public static double checkApprt(ProduitImmobilierDTO produitImmobilierDTO) {
        return produitImmobilierDTO.getPrix().doubleValue() * 0.1;
    }

    public static int checkDureeCredit() {
        return DEFAULT_DUREE_CREDIT;
    }

    public static double checkTaeg() {
        return TAEG;
    }
}
