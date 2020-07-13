package com.immoapp.audits;

import com.immoapp.audits.calculs.LoiBouvardCalcul;
import com.immoapp.audits.calculs.LoiLmnpCalcul;
import com.immoapp.audits.calculs.LoiMalrauxCalcul;
import com.immoapp.audits.calculs.LoiPinelCalcul;
import com.immoapp.audits.dtos.DeficitFoncierDTO;
import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.services.AuditService;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.PinelConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class test {

    private static final Logger logger = LoggerFactory.getLogger(test.class);
    public static void main(String[] args) {

       // System.out.println(CommonConstants.calculerMensulaiteCredit(130000,300,0.0365));
       // LoiBouvardCalcul loiBouvardCalcul = new LoiBouvardCalcul(p);
      //  System.out.println("+++"+loiBouvardCalcul.calculerEconomyImpots(130000));
        ProduitImmobilierDTO produitImmobilierDTO = new ProduitImmobilierDTO();
        produitImmobilierDTO.setPrix(new BigDecimal(100000));
        produitImmobilierDTO.setSurface(40);
        produitImmobilierDTO.setCoutTravaux(120000);
        produitImmobilierDTO.setLoyerEstime(420);
        LoiPinelCalcul loiPinelCalcul = new LoiPinelCalcul();
        loiPinelCalcul.calculerEffortEpargne(produitImmobilierDTO , TypePinel.PINEL6ANS, 240 , 0.0, 0.0);
       // loiPinelCalcul.calculerMensulaiteCredit(120000,180,0.02);
     //   System.out.println("-- "+loiBouvardCalcul.calculerEffortEpagne(produitImmobilierDTO,300, 0.0365));
        LoiLmnpCalcul loiLmnpCalcul = new LoiLmnpCalcul(50000.0, false, 0, 5000, 0.0);
        loiLmnpCalcul.getRegimeReel(produitImmobilierDTO, 0.04, 180);
        LoiMalrauxCalcul loiMalrauxCalcul = new LoiMalrauxCalcul();
        loiMalrauxCalcul.calculerLoiMalraux(produitImmobilierDTO, 240, 0.04, 0.0, 50000, false, 0);
    }


}
