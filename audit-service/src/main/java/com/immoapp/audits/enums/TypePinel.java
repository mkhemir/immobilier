package com.immoapp.audits.enums;


import com.immoapp.audits.utile.PinelConstants;

public enum TypePinel {

    PINEL6ANS(PinelConstants.duree6), PINEL9ANS(PinelConstants.duree9), PINEL12ANS(PinelConstants.duree12);

    private int duree;


    private TypePinel(int duree) {
        this.duree = duree;
    }

    public int getDiscount() {
        switch (this) {
            case PINEL6ANS:
                return PinelConstants.discount12;
            case PINEL9ANS:
                return PinelConstants.discount18;
            case PINEL12ANS:
                return PinelConstants.discount21;
            default:
                return 0;
        }
    }

    public int getDuree(){
        return this.duree;
    }
}
