package com.immoapp.audits.calculs;

import com.immoapp.audits.controllers.AuditController;
import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.exceptions.PinelException;
import com.immoapp.audits.utile.CommonConstants;
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


    public double calculerEconomieImpot(double prixAchat, TypePinel pinel, int annee) {
        double coeffImpots = annee <= 9 ? 0.02 : 0.01;
        double totalAchat = getFraisNotaire(prixAchat) + prixAchat;
        return ((totalAchat * coeffImpots)) / 12;
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
    public double calculerEffortEpargne(ProduitImmobilierDTO produitImmobilierDTO, TypePinel pinel, Integer dureeCredit, Double apport, Double taeg) {
        double loyerMax = calculerLoyerMax(produitImmobilierDTO);
        double loyerMoyen = 0.0;
      /*  double economyImpots = pinel == TypePinel.PINEL12ANS ? ((calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 9) * 9)
                + (calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 12) * 3)) / 144 :
                calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 6) / 12;*/
        double economyImpots = pinel == TypePinel.PINEL6ANS || pinel == TypePinel.PINEL9ANS ? calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 6) :
                calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel, 12);

        double mensualiteCredits = CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue(), dureeCredit, taeg, apport , produitImmobilierDTO.isEstNeuf());
        double fraisAnnexe = loyerMoyen / 4;
        try {
            loyerMoyen = calculerLoyerSurPeriodeCredit(produitImmobilierDTO, loyerMax, pinel, dureeCredit);
        } catch (PinelException p) {
        }
        logger.info("loyer estime"+produitImmobilierDTO.getLoyerEstime());
        logger.info("mensualiteCredits"+ mensualiteCredits);
        logger.info("economy"+ ( economyImpots ));
        logger.info("loyer moyen"+loyerMoyen);
        return (loyerMoyen + economyImpots - mensualiteCredits); // - fraisAnnexe);
    }
}
