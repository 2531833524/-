package com.wcz.bean;

public class Tuple<A,B> {
    public  A first;
    public  B second;
    public Tuple(A a,B b){
        first=a;
        second=b;
    }
    public String toStirng(){
        return "("+first+","+second+")";
    }
}
