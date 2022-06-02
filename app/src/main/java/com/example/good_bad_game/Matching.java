package com.example.good_bad_game;

public class Matching {
    private String id;
    private String userId;
    private String matchIdx;

    public String getId(){return id;}
    public String getUserId(){return userId;}
    public String getMatchIdx(){return matchIdx;}


    public Matching(String id, String userId, String matchIdx){
        this.id = id;
        this.userId = userId;
        this.matchIdx = matchIdx;
    }

}
