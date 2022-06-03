package com.example.good_bad_game.ranking;

public class Ranking {
    private int id;
    private int uid;
    private int sid;
    private String rank;

    public Ranking(int uid, int sid){
        this.uid = uid;
        this.sid = sid;
    }

    public Ranking(){
    }

    public int getId(){return this.id;}
    public int getUid(){return this.uid;}
    public int getSid(){return this.sid;}
    public String getRank(){return this.rank;}

    public void setId(int id){this.id = id;}
    public void setUid(int uid){this.uid = uid;}
    public void setSkin(int Sid){this.sid = Sid;}
    public void setRank(String rank){this.rank = rank;}

}
