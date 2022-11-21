package com.wcz.bean;

import java.util.Date;

public class Cart {
    private int cart_id;
    private int user_id;
    private int pro_id;
    private Date cart_time;
    private double cart_money;

    @Override
    public String toString() {
        return "cart{" +
                "cart_id=" + cart_id +
                ", user_id=" + user_id +
                ", pro_id=" + pro_id +
                ", cart_time=" + cart_time +
                ", cart_money=" + cart_money +
                '}';
    }

    public double getCart_money() {
        return cart_money;
    }

    public void setCart_money(double cart_money) {
        this.cart_money = cart_money;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public Date getCart_time() {
        return cart_time;
    }

    public void setCart_time(Date cart_time) {
        this.cart_time = cart_time;
    }
}
