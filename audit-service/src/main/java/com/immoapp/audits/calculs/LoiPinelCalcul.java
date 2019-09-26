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

    public double getSurfaceUtile(ProduitImmobilierDTO produitImmobilierDTO){
        double surfaceAnnexeBrut = produitImmobilierDTO.getSurfaceBalcon()+produitImmobilierDTO.getSurfaceTerrasse()+produitImmobilierDTO.getSurfaceLogias();
        double surfaceAnnexe = surfaceAnnexeBrut / 2 < PinelConstants.surfaceAnnexMax ? surfaceAnnexeBrut / 2 : PinelConstants.surfaceAnnexMax;
        return produitImmobilierDTO.getSurface() + surfaceAnnexe;
    }

    // TO DO
    public double getBareme(){
        return PinelConstants.BAREME_PINEL_A;
    }
}
