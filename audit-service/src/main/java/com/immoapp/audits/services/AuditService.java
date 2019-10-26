package com.immoapp.audits.services;

import com.immoapp.audits.calculs.LoiPinelCalcul;
import com.immoapp.audits.dtos.ProduitImmobilierDTO;
import com.immoapp.audits.dtos.ResultatLoiPinel6DTO;
import com.immoapp.audits.dtos.ResultatLoiPinelDTO;
import com.immoapp.audits.enums.TypePinel;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    public ResultatLoiPinelDTO getResultat(TypePinel typePinel, ProduitImmobilierDTO p, Double apport, Integer dureeCredit, Double taeg){
        ResultatLoiPinelDTO resultatLoiPinelDTO = new ResultatLoiPinelDTO();
        LoiPinelCalcul loiPinelCalcul = new LoiPinelCalcul();
        resultatLoiPinelDTO.setLoyerMaximum(loiPinelCalcul.calculerLoyerMax(p));
        resultatLoiPinelDTO.setReductionImpots(loiPinelCalcul.calculerReductionImpots(p, typePinel).doubleValue());
        resultatLoiPinelDTO.setMontantEmprunt(loiPinelCalcul.calculerMontantEmprunt(p.getPrix().doubleValue(), apport));
        resultatLoiPinelDTO.setEconomyImpots(loiPinelCalcul.calculerEconomieImpot(p.getPrix().doubleValue(), typePinel));
        resultatLoiPinelDTO.setMensualiteCredit(loiPinelCalcul.calculerMensulaiteCredit(resultatLoiPinelDTO.getMontantEmprunt(), dureeCredit, taeg ));
        resultatLoiPinelDTO.setFraisAnnexe(loiPinelCalcul.calculerFraisAnnexes(p));
        resultatLoiPinelDTO.setEffortEpargne(loiPinelCalcul.calculerEffortEpargne(p, typePinel, dureeCredit, apport, taeg));
        return resultatLoiPinelDTO;
    }
}
