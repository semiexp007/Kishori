package com.uietsocial.kishori.model;

public class treatmentmodel {
    String type;
    String symp;
    String treat;

    public treatmentmodel() {
    }

    public treatmentmodel(String type, String symp, String treat) {
        this.type = type;
        this.symp = symp;
        this.treat = treat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymp() {
        return symp;
    }

    public void setSymp(String symp) {
        this.symp = symp;
    }

    public String getTreat() {
        return treat;
    }

    public void setTreat(String treat) {
        this.treat = treat;
    }
}
