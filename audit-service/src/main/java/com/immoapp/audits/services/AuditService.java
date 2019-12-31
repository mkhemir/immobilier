package com.immoapp.audits.services;

import com.immoapp.audits.calculs.LoiLmnpCalcul;
import com.immoapp.audits.calculs.LoiPinelCalcul;
import com.immoapp.audits.dtos.*;
import com.immoapp.audits.enums.LoyerEstimation;
import com.immoapp.audits.enums.TypePinel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        resultatLoiPinelDTO.setEconomyImpots(loiPinelCalcul.calculerEconomieImpot(p.getPrix().doubleValue(), typePinel));
        resultatLoiPinelDTO.setMensualiteCredit(loiPinelCalcul.calculerMensulaiteCredit(resultatLoiPinelDTO.getMontantEmprunt(), dureeCredit, taeg));
        resultatLoiPinelDTO.setFraisAnnexe(loiPinelCalcul.calculerFraisAnnexes(p));
        resultatLoiPinelDTO.setEffortEpargne(loiPinelCalcul.calculerEffortEpargne(p, typePinel, dureeCredit, apport, taeg));
        return resultatLoiPinelDTO;
    }

    public ResultatBouvardDTO getBouvard() {
        ResultatBouvardDTO resultatBouvardDTO = new ResultatBouvardDTO();
        return resultatBouvardDTO;
    }

    public ResultatLmnpDto getLmnpReel(ProduitImmobilierDTO produitImmobilierDTO) {
        checkTaeg();
        return lmnpCalcul.getRegimeReel(produitImmobilierDTO.getLoyerEstime() != 0 ? produitImmobilierDTO.getLoyerEstime() : LoyerEstimation.getLoyer(produitImmobilierDTO.getNbrPiece()),
                produitImmobilierDTO.getPrix().doubleValue(), taeg);
    }

    public ResultatLmnpDto getLmnpMicro(ProduitImmobilierDTO produitImmobilierDTO) {
        checkTaeg();
        return lmnpCalcul.getRegimeMicro(produitImmobilierDTO.getLoyerEstime() != 0 ? produitImmobilierDTO.getLoyerEstime() : LoyerEstimation.getLoyer(produitImmobilierDTO.getNbrPiece()),
                produitImmobilierDTO.getPrix().doubleValue(), taeg);
    }

    private void checkTaeg(){
        if(this.taeg == 0){
            this.taeg = optimisationFiscaleService.getInfoBanques().stream().findFirst().get().getTaeg();
        }
    }
}
