package com.example.upc.service.model;

/**
 * @author zcc
 * @date 2019/10/28 0:59
 */
public class InspectCheck {
    private int shouldCheck;
    private int haveCheck;
    private int checkEnterprise;
    private int numberEnterprise;

    public int getCheckEnterprise() {
        return checkEnterprise;
    }

    public void setCheckEnterprise(int checkEnterprise) {
        this.checkEnterprise = checkEnterprise;
    }

    public int getNumberEnterprise() {
        return numberEnterprise;
    }

    public void setNumberEnterprise(int numberEnterprise) {
        this.numberEnterprise = numberEnterprise;
    }

    public int getShouldCheck() {
        return shouldCheck;
    }

    public void setShouldCheck(int shouldCheck) {
        this.shouldCheck = shouldCheck;
    }

    public int getHaveCheck() {
        return haveCheck;
    }

    public void setHaveCheck(int haveCheck) {
        this.haveCheck = haveCheck;
    }
}
