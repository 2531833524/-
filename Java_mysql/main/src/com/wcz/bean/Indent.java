package com.wcz.bean;

public class Indent {
    private String ind_id;
    private int user_id;
    private String ind_aads;
    private String ind_sads;
    private double ind_money;
    private int ind_state;
    private int pro_id;
    private int bus_id;

    @Override
    public String toString() {
        return "Indent{" +
                "ind_id='" + ind_id + '\'' +
                ", user_id=" + user_id +
                ", ind_aads='" + ind_aads + '\'' +
                ", ind_sads='" + ind_sads + '\'' +
                ", ind_money=" + ind_money +
                ", ind_state=" + ind_state +
                ", pro_id=" + pro_id +
                ", bus_id=" + bus_id +
                '}';
    }

    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public String getInd_id() {
        return ind_id;
    }

    public void setInd_id(String ind_id) {
        this.ind_id = ind_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getInd_aads() {
        return ind_aads;
    }

    public void setInd_aads(String ind_aads) {
        this.ind_aads = ind_aads;
    }

    public String getInd_sads() {
        return ind_sads;
    }

    public void setInd_sads(String ind_sads) {
        this.ind_sads = ind_sads;
    }

    public double getInd_money() {
        return ind_money;
    }

    public void setInd_money(double ind_money) {
        this.ind_money = ind_money;
    }

    public int getInd_state() {
        return ind_state;
    }

    public void setInd_state(int ind_state) {
        this.ind_state = ind_state;
    }
}
