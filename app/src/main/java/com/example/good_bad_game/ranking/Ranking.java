package com.example.good_bad_game.ranking;

public class Ranking {
    private String nickname;
    private String skin;
    private String rank;

    public Ranking(String nickname, String skin){
        this.nickname = nickname;
        this.skin = skin;
    }
    public Ranking(){

    }

    public String getNickname(){return this.nickname;}
    public String getSkin(){return this.skin;}
    public String getRank(){return this.rank;}

    public void setNickname(String nickname){this.nickname = nickname;}
    public void setSkin(String skin){this.skin = skin;}
    public void setRank(String rank){this.rank = rank;}

}
