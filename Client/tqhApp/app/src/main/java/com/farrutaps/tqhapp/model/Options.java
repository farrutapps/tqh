package com.farrutaps.tqhapp.model;

/**
 * Created by Sònia Batllori on 15/02/2018.
 */
public enum Options {

    HAMBRE("Tengo hambre"),
    SUEÑO("Tengo sueño"),
    CACA("Tengo caca"),
    EMPITUFE("Estoy verschlümpft"),
    NO_CENA("No vengo a cenar"),
    MASATGE("Vull un ma(ssa)tge"),
    NO_ESPERAR("No me esperes"),
    BESPRECHUNG("Besprechung"),
    LO_DEJAMOS("Lo dejamos");

    private String title;

    Options(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
