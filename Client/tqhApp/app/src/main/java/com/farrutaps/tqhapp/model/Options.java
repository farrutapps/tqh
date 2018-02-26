package com.farrutaps.tqhapp.model;

import java.util.Random;

/**
 * Created by Sònia Batllori on 15/02/2018.
 */
public enum Options {

    OP_1("Tengo hambre"),
    OP_2("Tengo sueño"),
    OP_3("Tengo caca"),
    OP_4("Estoy verschlümpft"),
    OP_5("No vengo a cenar"),
    OP_6("Vull un ma(ssa)tge"),
    OP_7("No me esperes"),
    OP_8("Besprechung"),
    OP_9("Lo dejamos");

    private String title;
    Options(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
