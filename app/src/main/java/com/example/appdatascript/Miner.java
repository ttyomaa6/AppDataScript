package com.example.appdatascript;

import java.util.ArrayList;

public class Miner {
    private long inHour;
    private long minerImage;
    private long minerPrice;

    public Miner(long inHour, long minerImage, long minerPrice){
        this.inHour=inHour;
        this.minerImage=minerImage;
        this.minerPrice=minerPrice;
    }
    public Miner(){
    }

    public long getInHour(){
        return this.inHour;
    }

    public void setInHour(long inHour){
        this.inHour=inHour;
    }

    public long getMinerImage(){
        return this.minerImage;
    }

    public void setMinerImage(long minerImage){
        this.minerImage=minerImage;
    }

    public long getMinerPrice(){
        return this.minerPrice;
    }

    public void setMinerPrice(long minerPrice){
        this.minerPrice=minerPrice;
    }
}
