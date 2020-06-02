package com.immoapp.audits.utile;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InfoTmi {
    double tmi;
    double ceilingAmount;
    double impots;

    public InfoTmi(double revenus, boolean estCouple, int nbreEnfants) {
        this.tmi = getTmi(revenus);
        this.impots = getImpots(this.tmi, revenus, estCouple, nbreEnfants);
    }

    public static double getTmi(double revenus) {
        if (revenus <= 10064) {
            return 0;
        } else if (revenus > 10064 && revenus <= 25659) {
            return 0.11;
        } else if (revenus > 25659 && revenus <= 73369) {
            return 0.3;
        } else if (revenus > 73369 && revenus <= 157806) {
            return 0.41;
        } else {
            return 0.45;
        }
    }

    public double getImpots(double tmi, double revenus, boolean estCouple, int nbreEnfants) {
        double quotientFamilial = estCouple ? 2 + 0.5 * nbreEnfants : 1 + 0.5 * nbreEnfants;
        double revenusFam = revenus / quotientFamilial;
        if (tmi == 0) {
            return 10064;
        } else if (tmi == 0.11) {
            return (revenusFam - 10064) * 0.11;
        } else if (tmi == 0.3) {
            return 15595 * 0.11 + ((revenusFam - 25659) * 0.3);
        } else if (tmi == 0.41) {
            return 15595 * 0.11 + ((73369 - 25659) * 0.3) + ((revenusFam - 73369) * 0.41);
        } else {
            return 15595 * 0.11 + ((73369 - 25659) * 0.3) + ((157806 - 73369) * 0.41) + ((revenusFam - 157806) * 0.45);
        }
    }
}