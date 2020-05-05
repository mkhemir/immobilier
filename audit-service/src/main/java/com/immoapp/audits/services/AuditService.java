package com.immoapp.audits.services;

import com.immoapp.audits.calculs.*;
import com.immoapp.audits.dtos.*;
import com.immoapp.audits.enums.LoyerEstimation;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.utile.CommonConstants;
import com.immoapp.audits.utile.PinelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuditService {

    @Autowired
    OptimisationFiscaleService optimisationFiscaleService;

    private double taeg;

    private LoiLmnpCalcul lmnpCalcul;


    public AuditService() {
        this.lmnpCalcul = new LoiLmnpCalcul();
    }

    public ResultatLoiPinelDTO getPinel(TypePinel typePinel, ProduitImmobilierDTO p, Double apport, Integer dureeCredit, Double taeg) {
        ResultatLoiPinelDTO resultatLoiPinelDTO = new ResultatLoiPinelDTO();
        LoiPinelCalcul loiPinelCalcul = new LoiPinelCalcul();
        resultatLoiPinelDTO.setLoyerMaximum(loiPinelCalcul.calculerLoyerMax(p));
        resultatLoiPinelDTO.setReductionImpots(loiPinelCalcul.calculerReductionImpots(p, typePinel).doubleValue());
        resultatLoiPinelDTO.setMontantEmprunt(loiPinelCalcul.calculerMontantEmprunt(p.getPrix().doubleValue(), apport));
        resultatLoiPinelDTO.setEconomyImpots(loiPinelCalcul.calculerEconomieImpot(p.getPrix().doubleValue(), typePinel, PinelConstants.NBR_ANNEE));
        resultatLoiPinelDTO.setMensualiteCredit(loiPinelCalcul.calculerMensulaiteCredit(resultatLoiPinelDTO.getMontantEmprunt(), dureeCredit, checkTaeg(taeg)));
        resultatLoiPinelDTO.setFraisAnnexe(loiPinelCalcul.calculerFraisAnnexes(p));
        resultatLoiPinelDTO.setEffortEpargne(loiPinelCalcul.calculerEffortEpargne(p, typePinel, dureeCredit, apport, checkTaeg(taeg), PinelConstants.NBR_ANNEE));
        return resultatLoiPinelDTO;
    }

    public ResultatBouvardDTO getBouvard(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, double taeg) {
        LoiBouvardCalcul loiBouvardCalcul = new LoiBouvardCalcul();
        ResultatBouvardDTO resultatBouvardDTO = new ResultatBouvardDTO();
        resultatBouvardDTO.setEconomyImpots(loiBouvardCalcul.calculerEconomyImpots(produitImmobilierDTO.getPrix().doubleValue()));
        resultatBouvardDTO.setMontantTvaRecuperee(loiBouvardCalcul.calculerTvaRecuperee(produitImmobilierDTO.getPrix().doubleValue()));
        resultatBouvardDTO.setEffortEpargne(loiBouvardCalcul.calculerEffortEpagne(produitImmobilierDTO, dureeCredit, checkTaeg(taeg)));
        resultatBouvardDTO.setEffortEpargneTvaIncluse(loiBouvardCalcul.calculerEffortEpagne(produitImmobilierDTO, dureeCredit, checkTaeg(taeg)));
        return resultatBouvardDTO;
    }

    public ResultatLmnpDto getLmnpReel(ProduitImmobilierDTO produitImmobilierDTO) {
        return lmnpCalcul.getRegimeReel(produitImmobilierDTO.getLoyerEstime() != 0 ? produitImmobilierDTO.getLoyerEstime() : LoyerEstimation.getLoyer(produitImmobilierDTO.getNbrPiece()),
                produitImmobilierDTO.getPrix().doubleValue(), checkTaeg(this.taeg));
    }

    public ResultatLmnpDto getLmnpMicro(ProduitImmobilierDTO produitImmobilierDTO) {
        return lmnpCalcul.getRegimeMicro(produitImmobilierDTO.getLoyerEstime() != 0 ? produitImmobilierDTO.getLoyerEstime() : LoyerEstimation.getLoyer(produitImmobilierDTO.getNbrPiece()),
                produitImmobilierDTO.getPrix().doubleValue(), checkTaeg(this.taeg));
    }

    public ResultatMalrauxDTO getMalraux(ProduitImmobilierDTO produitImmobilierDTO, int duree, double taeg) {
        LoiMalrauxCalcul loiMalraux = new LoiMalrauxCalcul();
         return loiMalraux.calculerLoiMalraux(produitImmobilierDTO, duree, taeg);
    }

    public ResultatMhDTO getMh(ProduitImmobilierDTO produitImmobilierDTO, int duree, double taeg, double salaire) {
        LoiMhCalcul loiMh = new LoiMhCalcul();
        return loiMh.calculerLoiMh(produitImmobilierDTO, duree, taeg, salaire);
    }

    public DeficitFoncierDTO getDeficitFincier(ProduitImmobilierDTO produitImmobilierDTO){
       DeficitFoncierDTO deficitFoncierDTO = new DeficitFoncierDTO();
       LoiDfCalcul loiDf = new LoiDfCalcul(CommonConstants.SALAIRE_DEFAUT);
        Map<Integer, Double> mapDeficits = new HashMap<>();
       int currentYear = CommonConstants.getCurrentYear();
       deficitFoncierDTO.setChargesNonFinanciere(CommonConstants.getChargesNonFinancieres(produitImmobilierDTO));
       deficitFoncierDTO.setInteretEmprunt(CommonConstants.getInteretEmprunt(produitImmobilierDTO));
       deficitFoncierDTO.setRevenusLoyer(CommonConstants.estimerMontantLoyer(produitImmobilierDTO) * 12);
       for (int i = currentYear; i < currentYear +5 ; i++ ){
           mapDeficits.put(i, loiDf.calculerDeficitFoncier(deficitFoncierDTO.getRevenusLoyer(), deficitFoncierDTO.getInteretEmprunt(), deficitFoncierDTO.getChargesNonFinanciere()));
       }
       deficitFoncierDTO.setGainImpots(mapDeficits);
       return deficitFoncierDTO;
    }
    private double checkTaeg(double taeg){
        this.taeg = taeg;
        if(this.taeg == 0){
            this.taeg = 1.5;//optimisationFiscaleService.getInfoBanques().stream().findFirst().get().getTaeg();
        }
        return this.taeg;
    }
}
