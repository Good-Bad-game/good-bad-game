package com.example.good_bad_game;

public class Matching {
    private String id;
    private String userId;
    private String matchIdx;
    private String ready;

    public String getId(){return id;}
    public String getUserId(){return userId;}
    public String getMatchIdx(){return matchIdx;}
    public String getReady(){return ready;}


    public Matching(String id, String userId, String matchIdx, String ready){
        this.id = id;
        this.userId = userId;
        this.matchIdx = matchIdx;
        this.ready = ready;
    }

}
