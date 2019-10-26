package com.immoapp.audits.calculs;

import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.utile.PinelConstants;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LoiPinelCalcul {


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

    public double calculerMensulaiteCredit(double montantEmprunt, Integer dureeCredit, Double taeg){
        double coefTaegBrut = taeg != null ? taeg : PinelConstants.TAEG;
        dureeCredit = dureeCredit != null ? dureeCredit : PinelConstants.DEFAULT_DUREE_CREDIT;
        double coef = (coefTaegBrut + PinelConstants.COEF_ASS) / 12;
        return (montantEmprunt * coef) / (1 - Math.pow(1 + coef, - dureeCredit));
    }

    public double calculerEconomieImpot(double prixAchat, TypePinel pinel){
        double coeffImpots = pinel.getDuree() <= 9 ? 0.02 : 0.01;
        double sommeAchat = prixAchat * PinelConstants.COEFF_FN;
        return (sommeAchat * coeffImpots) / 12;
    }

    public  double calculerFraisAnnexes(ProduitImmobilierDTO produitImmobilierDTO){
        return calculerLoyerMax(produitImmobilierDTO) * 0.25;
    }

    public double calculerEffortEpargne(ProduitImmobilierDTO produitImmobilierDTO, TypePinel pinel , Integer dureeCredit, Double apport , Double taeg){
        double montantEmprunt = calculerMontantEmprunt(produitImmobilierDTO.getPrix().doubleValue(), apport);
    return calculerLoyerMax(produitImmobilierDTO)+ calculerEconomieImpot(produitImmobilierDTO.getPrix().doubleValue(), pinel)
            - calculerMensulaiteCredit(montantEmprunt, dureeCredit, taeg) - calculerFraisAnnexes(produitImmobilierDTO);

    }
}
