package com.immoapp.audits.calculs;

import com.immoapp.audits.controllers.AuditController;
import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.exceptions.PinelException;
import com.immoapp.audits.utile.PinelConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LoiPinelCalcul {

    private static final Logger logger = LoggerFactory.getLogger(LoiPinelCalcul.class);

    public BigDecimal calculerReductionImpots(ProduitImmobilierDTO produitImmobilierDTO, TypePinel typePinel) {
        return ((produitImmobilierDTO.getPrix().multiply(new BigDecimal(typePinel.getDiscount()))).divide(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(typePinel.getDuree()))));

    }

    public double calculerLoyerMax(ProduitImmobilierDTO produitImmobilierDTO) {
        double surfaceUtile = getSurfaceUtile(produitImmobilierDTO);
        double coeffMultiplicateur = (PinelConstants.COEF_PINEL_MULP / surfaceUtile) + PinelConstants.COEF_PINEL_ADDITIF < PinelConstants.COEF_MULT_MAX
                ? (PinelConstants.COEF_PINEL_MULP / surfaceUtile) + PinelConstants.COEF_PINEL_ADDITIF : PinelConstants.COEF_MULT_MAX;
        return surfaceUtile * coeffMultiplicateur * getBareme();
    }

    public double getSurfaceUtile(ProduitImmobilierDTO produitImmobilierDTO) {
        double surfaceAnnexeBrut = produitImmobilierDTO.getSurfaceBalcon() + produitImmobilierDTO.getSurfaceTerrasse() + produitImmobilierDTO.getSurfaceLogias();
        double surfaceAnnexe = surfaceAnnexeBrut / 2 < PinelConstants.surfaceAnnexMax ? surfaceAnnexeBrut / 2 : PinelConstants.surfaceAnnexMax;
        return produitImmobilierDTO.getSurface() + surfaceAnnexe;
    }

    // TO DO
    public double getBareme() {
        return PinelConstants.BAREME_PINEL_A;
    }

    public double calculerMontantEmprunt(double prixAchat, Double apport) {
        apport = apport != null ? apport : prixAchat * PinelConstants.COEFF_APPORT_DEFAULT;
        return prixAchat + (prixAchat * PinelConstants.COEFF_FN) - apport;
    }

    public double calculerMensulaiteCredit(double montantEmprunt, Integer dureeCredit, Double taeg) {
        double coefTaegBrut = taeg != 0.0 ? taeg : PinelConstants.TAEG;
        dureeCredit = dureeCredit != null ? dureeCredit : PinelConstants.DEFAULT_DUREE_CREDIT;
       // double coef = (coefTaegBrut + PinelConstants.COEF_ASS) / 12;
        double coef = coefTaegBrut / 12;
        logger.info("MSC:"+(montantEmprunt * coef) / (1 - Math.pow(1 + coef, -dureeCredit)));
        return (montantEmprunt * coef) / (1 - Math.pow(1 + coef, -dureeCredit));
    }

    public double calculerEconomieImpot(double prixAchat, TypePinel pinel, int annee) {
        double coeffImpots = annee <= 9 ? 0.02 : 0.0175;
        double totalAchat = getFraisNotaire(prixAchat) + prixAchat;
        return ((totalAchat * coeffImpots));
    }

    public double getFraisNotaire(double prixAchat) {
        return prixAchat * PinelConstants.COEFF_FN;
    }


    private double calculerLoyerSurPeriodeCredit(ProduitImmobilierDTO produitImmobilierDTO, double loyerMax,
                                                 TypePinel pinel, int dureeCredit) throws PinelException{
        if (TypePinel.PINEL6ANS == pinel) {
            return (loyerMax * 71 + (produitImmobilierDTO.getLoyerEstime() * (dureeCredit - 72))) / dureeCredit;
        } else if (TypePinel.PINEL9ANS == pinel) {
            return (loyerMax * 108 + (produitImmobilierDTO.getLoyerEstime() * (dureeCredit - 108))) / dureeCredit;
        } else if (TypePinel.PINEL9ANS == pinel) {
            return (loyerMax * 144 + (produitImmobilierDTO.getLoyerEstime() * (dureeCredit - 144))) / dureeCredit;
        } else {
            throw new PinelException();
        }
    }

    //TOFIX enlever les frais comme dans les autres lois
    public double calculerEffortEpargne(ProduitImmobilierDTO produitImmobilierDTO, TypePinel pinel, Integer dureeCredit, Double apport, Double taeg, int annee) {
        double loyerMax = calculerLoyerMax(produitImmobilierDTO);
        double loyerMoyen = 0.0;
        double montantEmprunt = calculerMontantEmprunt(produitImmobilierDTO.getPrix().doubleValue(), apport);
      /*  double economyImpots = pinel == TypePinel.PINEL12ANS ? ((calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 9) * 9)
                + (calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 12) * 3)) / 144 :
                calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 6) / 12;*/
        double economyImpots = pinel == TypePinel.PINEL6ANS || pinel == TypePinel.PINEL9ANS ? calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 6) :
                calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 12);

        double mensualiteCredits = calculerMensulaiteCredit(montantEmprunt, dureeCredit, taeg);
        double fraisAnnexe = loyerMoyen / 4;
        try {
            loyerMoyen = calculerLoyerSurPeriodeCredit(produitImmobilierDTO, loyerMax, pinel, dureeCredit);
        } catch (PinelException p) {
        }
        return (loyerMoyen + economyImpots - mensualiteCredits); // - fraisAnnexe);
    }
}
