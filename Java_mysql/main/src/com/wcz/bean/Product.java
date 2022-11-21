package com.wcz.bean;

public class Product {
    private int pro_id;
    private String pro_name;
    private double pro_price;
    private int pro_num;
    private String pro_info;
    private int pro_good;
    private int bus_id;

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

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public double getPro_price() {
        return pro_price;
    }

    public void setPro_price(double pro_price) {
        this.pro_price = pro_price;
    }

    public int getPro_num() {
        return pro_num;
    }

    public void setPro_num(int pro_num) {
        this.pro_num = pro_num;
    }

    public String getPro_info() {
        return pro_info;
    }

    public void setPro_info(String pro_info) {
        this.pro_info = pro_info;
    }

    public int getPro_good() {
        return pro_good;
    }

    public void setPro_good(int pro_good) {
        this.pro_good = pro_good;
    }
    public void Print(){
        System.out.println(String.format("%-10d%-20s\t%-10.2f%-10d%-20s\t%-10d%-10d",pro_id,pro_name,pro_price,pro_num,pro_info,pro_good,bus_id));
    }
    @Override
    public String toString() {
        return pro_id +
                "\t\t\t" + pro_name +
                "\t\t\t" + pro_price +
                "\t\t\t"+ pro_num +
                "\t\t\t" + pro_info +
                "\t\t\t" + pro_good+
                "\t\t\t"+bus_id
                ;
    }
}
