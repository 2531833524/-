package com.wcz.bean;

public class Role {
    private int r_id;
    private String r_name;
    private String r_username;
    private String r_password;
    private int power_id;
    private String r_address;

    public String getR_address() {
        return r_address;
    }

    public void setR_address(String r_address) {
        this.r_address = r_address;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getR_username() {
        return r_username;
    }

    public void setR_username(String r_username) {
        this.r_username = r_username;
    }

    public String getR_password() {
        return r_password;
    }

    public void setR_password(String r_password) {
        this.r_password = r_password;
    }

    public int getPower_id() {
        return power_id;
    }

    public void setPower_id(int power_id) {
        this.power_id = power_id;
    }

    public void Print(){
        System.out.println(String.format("%-10d%-10s\t%-10s%-10s%-10d%-30s",r_id,r_name,r_username,r_password,power_id,r_address));
    }
    @Override
    public String toString() {
        return "Role{" +
                "r_id=" + r_id +
                ", r_name='" + r_name + '\'' +
                ", r_username='" + r_username + '\'' +
                ", r_password='" + r_password + '\'' +
                ", power_id=" + power_id +
                ", r_address='" + r_address + '\'' +
                '}';
    }
}
