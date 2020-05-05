package com.immoapp.audits.enums;

import com.immoapp.audits.utile.CommonConstants;

public enum LoyerEstimation {


    P1(CommonConstants.LOYER_ESTIME_1P), P2(CommonConstants.LOYER_ESTIME_2P), P3(CommonConstants.LOYER_ESTIME_3P),
    P4(CommonConstants.LOYER_ESTIME_4P), P5(CommonConstants.LOYER_ESTIME_5P), PX(CommonConstants.LOYER_ESTIME_XP);
    public double loyer;

    private LoyerEstimation(double loyer) {
        this.loyer = loyer;
    }

    public static double getLoyer(int nbrPieces) {
        switch (nbrPieces) {
            case 1:
                return LoyerEstimation.P1.loyer;
            case 2:
                return LoyerEstimation.P2.loyer;
            case 3:
                return LoyerEstimation.P3.loyer;
            case 4:
                return LoyerEstimation.P4.loyer;
            case 5:
                return LoyerEstimation.P5.loyer;
            default:
                return LoyerEstimation.PX.loyer;
        }
    }
}
