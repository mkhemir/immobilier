package com.immoapp.audits;

import com.immoapp.audits.calculs.*;
import com.immoapp.audits.dtos.BienImmobilierDTO;
import com.immoapp.audits.dtos.DeficitFoncierDTO;
import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.UserDTO;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.services.AuditService;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.PinelConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class test {

    private static final Logger logger = LoggerFactory.getLogger(test.class);
    public  static double taeg =0.015;
    public static int dureeCredit = 240;
    public static void main(String[] args) {

       // System.out.println(CommonConstants.calculerMensulaiteCredit(130000,300,0.0365));
       // LoiBouvardCalcul loiBouvardCalcul = new LoiBouvardCalcul(p);
      //  System.out.println("+++"+loiBouvardCalcul.calculerEconomyImpots(130000));
        ProduitImmobilierDTO produitImmobilierDTO = new ProduitImmobilierDTO();
        produitImmobilierDTO.setPrix(new BigDecimal(100000));
        produitImmobilierDTO.setSurface(40);
        produitImmobilierDTO.setCoutTravaux(120000);
        produitImmobilierDTO.setDureeTravaux(4);
        produitImmobilierDTO.setLoyerEstime(420);
        LoiPinelCalcul loiPinelCalcul = new LoiPinelCalcul();
    //    loiPinelCalcul.calculerEffortEpargne(produitImmobilierDTO , TypePinel.PINEL6ANS, 240 , 0.0, 0.0);
       // loiPinelCalcul.calculerMensulaiteCredit(120000,180,0.02);
     //   System.out.println("-- "+loiBouvardCalcul.calculerEffortEpagne(produitImmobilierDTO,300, 0.0365));
     //   LoiLmnpCalcul loiLmnpCalcul = new LoiLmnpCalcul(50000.0, false, 0, 5000, 0.0);
   //     loiLmnpCalcul.getRegimeReel(produitImmobilierDTO, 0.04, 180);
   //     LoiMalrauxCalcul loiMalrauxCalcul = new LoiMalrauxCalcul();
   //     loiMalrauxCalcul.calculerLoiMalraux(produitImmobilierDTO, 240, 0.04, 0.0, 50000, false, 0);
        testerDeficitFoncier(produitImmobilierDTO);
    }

    public static void testerDeficitFoncier(ProduitImmobilierDTO produitImmobilierDTO) {
        UserDTO user = new UserDTO();
        user.setApport(20000);
        user.setMarried(true);
        user.setNbrEnfants(2);
        user.setRevenusBrut(60000);
        BienImmobilierDTO bienImmobilierDTO = new BienImmobilierDTO();
        List<BienImmobilierDTO> autresBiens = new ArrayList<>();
        BienImmobilierDTO bien = new BienImmobilierDTO();
        bien.setRevenusFoncierAnnuel(5000);
        autresBiens.add(bien);
        user.setAutresBiens(autresBiens);

        DeficitFoncierDTO deficitFoncierDTO = new DeficitFoncierDTO();
        deficitFoncierDTO.setAnnee(2022);
        DfSimulation df = new DfSimulation(deficitFoncierDTO, taeg, user, produitImmobilierDTO, dureeCredit);
        df.calculerDeficitFoncier(0);
        logger.info("ANNEE --- " +deficitFoncierDTO.getAnnee());
        logger.info("DEFICIT --- " +deficitFoncierDTO.getDeficit());
        logger.info("eCONOMY IMPOTS --- " +deficitFoncierDTO.getEconomyImpots());
        logger.info("MENSUALITES CDREDIT --- " +deficitFoncierDTO.getMensualiteCredit());
        logger.info("INTERET EMPRUNT --- " +deficitFoncierDTO.getInteretEmprunt());
        logger.info("IMPOTS APR7S REDUCTION --- " +deficitFoncierDTO.getTotalImpotsApresReduction());
        logger.info("IMPOTS AVANT REDUCTION --- " +deficitFoncierDTO.getTotalImpotsAvantReduction());
    }


}
