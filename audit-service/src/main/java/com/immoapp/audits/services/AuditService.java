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

    private LoiLmnpCalcul lmnpCalcul;


    public AuditService() {
        this.lmnpCalcul = new LoiLmnpCalcul();
    }

    public ResultatLoiPinelDTO getPinel(TypePinel typePinel, ProduitImmobilierDTO p, double apport, Integer dureeCredit, Double taeg) {
        ResultatLoiPinelDTO resultatLoiPinelDTO = new ResultatLoiPinelDTO();
        LoiPinelCalcul loiPinelCalcul = new LoiPinelCalcul();
        resultatLoiPinelDTO.setLoyerMaximum(loiPinelCalcul.calculerLoyerMax(p));
        resultatLoiPinelDTO.setReductionImpots(loiPinelCalcul.calculerReductionImpots(p, typePinel).doubleValue());
        resultatLoiPinelDTO.setMontantEmprunt(loiPinelCalcul.calculerMontantEmprunt(p.getPrix().doubleValue(), apport));
        resultatLoiPinelDTO.setEconomyImpots(loiPinelCalcul.calculerEconomieImpot(p.getPrix().doubleValue(), typePinel, PinelConstants.NBR_ANNEE));
        resultatLoiPinelDTO.setMensualiteCredit(loiPinelCalcul.calculerMensulaiteCredit(resultatLoiPinelDTO.getMontantEmprunt(), dureeCredit, taeg));
        resultatLoiPinelDTO.setFraisAnnexe(loiPinelCalcul.calculerFraisAnnexes(p));
        resultatLoiPinelDTO.setEffortEpargne(loiPinelCalcul.calculerEffortEpargne(p, typePinel, dureeCredit, apport, taeg, PinelConstants.NBR_ANNEE));
        return resultatLoiPinelDTO;
    }

    public ResultatBouvardDTO getBouvard(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, double taeg) {
        LoiBouvardCalcul loiBouvardCalcul = new LoiBouvardCalcul();
        ResultatBouvardDTO resultatBouvardDTO = new ResultatBouvardDTO();
        resultatBouvardDTO.setEconomyImpots(loiBouvardCalcul.calculerEconomyImpots(produitImmobilierDTO.getPrix().doubleValue()));
        resultatBouvardDTO.setMontantTvaRecuperee(loiBouvardCalcul.calculerTvaRecuperee(produitImmobilierDTO.getPrix().doubleValue()));
        resultatBouvardDTO.setEffortEpargne(loiBouvardCalcul.calculerEffortEpagne(produitImmobilierDTO, dureeCredit, taeg));
        resultatBouvardDTO.setEffortEpargneTvaIncluse(loiBouvardCalcul.calculerEffortEpagne(produitImmobilierDTO, dureeCredit, taeg));
        return resultatBouvardDTO;
    }

    public ResultatLmnpDto getLmnpReel(ProduitImmobilierDTO produitImmobilierDTO, double taeg) {
        return lmnpCalcul.getRegimeReel(produitImmobilierDTO.getLoyerEstime() != 0 ? produitImmobilierDTO.getLoyerEstime() : LoyerEstimation.getLoyer(produitImmobilierDTO.getNbrPiece()),
                produitImmobilierDTO.getPrix().doubleValue(), taeg);
    }

    public ResultatLmnpDto getLmnpMicro(ProduitImmobilierDTO produitImmobilierDTO, double taeg) {
        return lmnpCalcul.getRegimeMicro(produitImmobilierDTO.getLoyerEstime() != 0 ? produitImmobilierDTO.getLoyerEstime() : LoyerEstimation.getLoyer(produitImmobilierDTO.getNbrPiece()),
                produitImmobilierDTO.getPrix().doubleValue(), taeg);
    }

    public ResultatMalrauxDTO getMalraux(ProduitImmobilierDTO produitImmobilierDTO, int duree, double taeg) {
        LoiMalrauxCalcul loiMalraux = new LoiMalrauxCalcul();
        return loiMalraux.calculerLoiMalraux(produitImmobilierDTO, duree, taeg);
    }

    public ResultatMhDTO getMh(ProduitImmobilierDTO produitImmobilierDTO, int duree, double taeg, double salaire) {
        LoiMhCalcul loiMh = new LoiMhCalcul();
        return loiMh.calculerLoiMh(produitImmobilierDTO, duree, taeg, salaire);
    }

    // TOFIX LES CHARGES FINANCIERES ET NON FINANCIERES URGENT
    public DeficitFoncierDTO getDeficitFincier(ProduitImmobilierDTO produitImmobilierDTO, boolean estCouple, int nbreEnfants, double revenus, int dureeCredit, double taeg) {
        DeficitFoncierDTO deficitFoncierDTO = new DeficitFoncierDTO();
        LoiDfCalcul loiDf = new LoiDfCalcul(revenus, estCouple, nbreEnfants, deficitFoncierDTO);
        Map<Integer, Double> mapDeficits = new HashMap<>();
        int currentYear = CommonConstants.getCurrentYear();
        deficitFoncierDTO.setChargesNonFinanciere((produitImmobilierDTO.getCoutTravaux() * 12) / dureeCredit);
        deficitFoncierDTO.setInteretEmprunt(CommonConstants.getInteretEmprunt(produitImmobilierDTO.getPrix().doubleValue() + produitImmobilierDTO.getCoutTravaux(), taeg));
        deficitFoncierDTO.setRevenusLoyer(produitImmobilierDTO.getLoyerEstime() * 12);
        double economyImpots = loiDf.calculerEconoImpots(produitImmobilierDTO.getLoyerEstime() * 12, deficitFoncierDTO.getInteretEmprunt(),
                deficitFoncierDTO.getChargesNonFinanciere(), revenus, estCouple, nbreEnfants);
        deficitFoncierDTO.setEconomyImpots(economyImpots);
        deficitFoncierDTO.setEffortEpargne(produitImmobilierDTO.getLoyerEstime() + economyImpots -
                CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue() + produitImmobilierDTO.getCoutTravaux() ,dureeCredit, taeg));
        for (int i = currentYear; i < currentYear + 5; i++) {
            mapDeficits.put(i, loiDf.calculerDeficitFoncier(deficitFoncierDTO.getRevenusLoyer(),
                    deficitFoncierDTO.getInteretEmprunt(), deficitFoncierDTO.getChargesNonFinanciere()));
        }
        deficitFoncierDTO.setGainImpots(mapDeficits);
        return deficitFoncierDTO;
    }

    public ResultatDenormandieDTO getDenormandie(double apport, int dureeCredit, double taeg,
                                                 ProduitImmobilierDTO produitImmobilierDTO, double tmi) {
        ResultatDenormandieDTO denormandie = new ResultatDenormandieDTO();
        LoiDenormandieCalcul denormandieCalcul = new LoiDenormandieCalcul(denormandie,
                produitImmobilierDTO.getPrix().doubleValue() - apport, dureeCredit, taeg, produitImmobilierDTO);
        denormandieCalcul.calculerEffortEpargne(tmi);
        return denormandie;

    }

}
