package com.immoapp.audits.services;

import com.immoapp.audits.calculs.*;
import com.immoapp.audits.dtos.*;
import com.immoapp.audits.enums.LoyerEstimation;
import com.immoapp.audits.enums.TypePinel;
import com.immoapp.audits.utile.BouvardConstants;
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

    }

    public ResultatLoiPinelDTO getPinel(TypePinel typePinel, ProduitImmobilierDTO p, double apport, Integer dureeCredit, Double taeg) {
        ResultatLoiPinelDTO resultatLoiPinelDTO = new ResultatLoiPinelDTO();
        LoiPinelCalcul loiPinelCalcul = new LoiPinelCalcul();
        resultatLoiPinelDTO.setLoyerMaximum(adaptNumber(loiPinelCalcul.calculerLoyerMax(p)));
        resultatLoiPinelDTO.setReductionImpots(adaptNumber(loiPinelCalcul.calculerReductionImpots(p, typePinel).doubleValue()));
        resultatLoiPinelDTO.setMontantEmprunt(adaptNumber(CommonConstants.calculerMontantEmprunt(p.getPrix().doubleValue(), apport, p.isEstNeuf())));
        resultatLoiPinelDTO.setEconomyImpots(adaptNumber(loiPinelCalcul.calculerEconomieImpot(p.getPrix().doubleValue(), typePinel, PinelConstants.NBR_ANNEE)));
        resultatLoiPinelDTO.setMensualiteCredit(adaptNumber(CommonConstants.calculerMensulaiteCredit(resultatLoiPinelDTO.getMontantEmprunt(), dureeCredit, taeg, apport, p.isEstNeuf())));
        resultatLoiPinelDTO.setFraisAnnexe(adaptNumber(resultatLoiPinelDTO.getLoyerMaximum() / 4));
        resultatLoiPinelDTO.setEffortEpargne(adaptNumber(loiPinelCalcul.calculerEffortEpargne(p, typePinel, dureeCredit, apport, taeg)));
        return resultatLoiPinelDTO;
    }

    public ResultatBouvardDTO getBouvard(ProduitImmobilierDTO produitImmobilierDTO, Integer dureeCredit, double taeg, double apport) {
        LoiBouvardCalcul loiBouvardCalcul = new LoiBouvardCalcul(produitImmobilierDTO, dureeCredit, taeg, apport);
        ResultatBouvardDTO resultatBouvardDTO = new ResultatBouvardDTO();
        resultatBouvardDTO.setMensualiteCredit(adaptNumber(loiBouvardCalcul.getMensualiteCredit()));
        resultatBouvardDTO.setEconomyImpots(adaptNumber(loiBouvardCalcul.calculerEconomyImpots(produitImmobilierDTO.getPrix().doubleValue(), dureeCredit)));
        resultatBouvardDTO.setGainImpots9Ans(adaptNumber(CommonConstants.getPrixHT(produitImmobilierDTO.getPrix().doubleValue()) * BouvardConstants.COEFF_REDUCTION));
        resultatBouvardDTO.setMontantTvaRecuperee(adaptNumber(loiBouvardCalcul.calculerTvaRecuperee(produitImmobilierDTO.getPrix().doubleValue())));
        resultatBouvardDTO.setEffortEpargne(adaptNumber(loiBouvardCalcul.calculerEffortEpagne(produitImmobilierDTO, dureeCredit, taeg, apport)));
        resultatBouvardDTO.setEffortEpargneSansTVA(adaptNumber(loiBouvardCalcul.calculerEffortEpagneSansTVA(produitImmobilierDTO, dureeCredit, taeg, apport)));
        return resultatBouvardDTO;
    }

    public ResultatLmnpDto getLmnpReel(ProduitImmobilierDTO produitImmobilierDTO, double taeg, double apport,
                                       double revenus, boolean estCouple, int nbrEnfants, int dureeCredit) {
        this.lmnpCalcul = this.lmnpCalcul == null ? new LoiLmnpCalcul(revenus, estCouple, nbrEnfants,
                produitImmobilierDTO.getLoyerEstime() * 12 , apport) : this.lmnpCalcul;
        return lmnpCalcul.getRegimeReel(produitImmobilierDTO, taeg, dureeCredit);
    }

    public ResultatLmnpDto getLmnpMicro(ProduitImmobilierDTO produitImmobilierDTO, double taeg, double apport,
                                        double revenus, boolean estCouple, int nbrEnfants, int dureeCredit) {
        this.lmnpCalcul = this.lmnpCalcul == null ? new LoiLmnpCalcul(revenus, estCouple, nbrEnfants,
                produitImmobilierDTO.getLoyerEstime() * 12 , apport) : this.lmnpCalcul;
        return lmnpCalcul.getRegimeMicro(produitImmobilierDTO, dureeCredit, taeg);
    }

    public ResultatMalrauxDTO getMalraux(ProduitImmobilierDTO produitImmobilierDTO, int duree, double taeg, double apport,
                                         double revenus, boolean estCouple, int nbrEnfants) {
        LoiMalrauxCalcul loiMalraux = new LoiMalrauxCalcul();
        return loiMalraux.calculerLoiMalraux(produitImmobilierDTO, duree, taeg, apport, revenus, estCouple, nbrEnfants);
    }

    public ResultatMhDTO getMh(ProduitImmobilierDTO produitImmobilierDTO, int duree, double taeg, double revenus, boolean estCouple, int nbreEnfants, double apport) {
        LoiMhCalcul loiMh = new LoiMhCalcul();
        return loiMh.calculerLoiMh(produitImmobilierDTO, duree, taeg, revenus, estCouple, nbreEnfants, apport);
    }

    // TOFIX LES CHARGES FINANCIERES ET NON FINANCIERES URGENT
    public DeficitFoncierDTO getDeficitFincier(ProduitImmobilierDTO produitImmobilierDTO, boolean estCouple, int nbreEnfants, double revenus, int dureeCredit, double taeg, double apport) {
        DeficitFoncierDTO deficitFoncierDTO = new DeficitFoncierDTO();
        LoiDfCalcul loiDf = new LoiDfCalcul(revenus, estCouple, nbreEnfants, deficitFoncierDTO);
        Map<Integer, Double> mapDeficits = new HashMap<>();
        int currentYear = CommonConstants.getCurrentYear();
        deficitFoncierDTO.setChargesNonFinanciere(adaptNumber((produitImmobilierDTO.getCoutTravaux() * 12) / dureeCredit));
        deficitFoncierDTO.setInteretEmprunt(adaptNumber(CommonConstants.getInteretEmprunt(produitImmobilierDTO.getPrix().doubleValue() + produitImmobilierDTO.getCoutTravaux(), taeg)));
        deficitFoncierDTO.setRevenusLoyer(adaptNumber(produitImmobilierDTO.getLoyerEstime() * 12));
        double economyImpots = loiDf.calculerEconoImpots(produitImmobilierDTO.getLoyerEstime() * 12, deficitFoncierDTO.getInteretEmprunt(),
                deficitFoncierDTO.getChargesNonFinanciere(), revenus, estCouple, nbreEnfants);
        deficitFoncierDTO.setEconomyImpots(adaptNumber(economyImpots));
        double mensualiteCredit = CommonConstants.calculerMensulaiteCredit(produitImmobilierDTO.getPrix().doubleValue()
                + produitImmobilierDTO.getCoutTravaux() , dureeCredit, taeg, apport, false);
        deficitFoncierDTO.setMensualiteCredit(adaptNumber(mensualiteCredit));
        deficitFoncierDTO.setEffortEpargne(adaptNumber(produitImmobilierDTO.getLoyerEstime() + economyImpots
                - mensualiteCredit));
        for (int i = currentYear; i < currentYear + 5; i++) {
            mapDeficits.put(i, adaptNumber(loiDf.calculerDeficitFoncier(deficitFoncierDTO.getRevenusLoyer(),
                    deficitFoncierDTO.getInteretEmprunt(), deficitFoncierDTO.getChargesNonFinanciere())));
        }
        deficitFoncierDTO.setGainImpots(mapDeficits);
        return deficitFoncierDTO;
    }

    public ResultatDenormandieDTO getDenormandie(double apport, int dureeCredit, double taeg,
                                                 ProduitImmobilierDTO produitImmobilierDTO, double tmi) {
        ResultatDenormandieDTO denormandie = new ResultatDenormandieDTO();
        LoiDenormandieCalcul denormandieCalcul = new LoiDenormandieCalcul(denormandie,
                produitImmobilierDTO.getPrix().doubleValue() + produitImmobilierDTO.getCoutTravaux(), dureeCredit, taeg, produitImmobilierDTO, apport);
        denormandieCalcul.calculerEffortEpargne(tmi);
        return denormandie;

    }

    public double adaptNumber(double n) {
        return Math.round(n * 100.0) / 100.0;
    }

}
