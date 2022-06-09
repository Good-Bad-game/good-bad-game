package com.example.good_bad_game;

public class getItem {

    private String id;
    private String uid;
    private String sid;
    private String cash;
    private String coin;

    public String getUserid(){return uid;}
    public String getShopid(){return sid;}
    public String getCash(){return cash;}
    public String getCoin(){return coin;}

    public getItem(String uid, String sid, String cash, String coin){
        this.uid = uid;
        this.sid = sid;
        this.cash = cash;
        this.coin = coin;
    }



}
