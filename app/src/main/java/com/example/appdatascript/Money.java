package com.example.appdatascript;

public class Money {
    private long money;

    public Money(long money){
        this.money=money;
    }
    public Money(){
    }

    public long  getMoney(){
        return this.money;
    }

    public void setMoney(long money){
        this.money=money;
    }
    //
}