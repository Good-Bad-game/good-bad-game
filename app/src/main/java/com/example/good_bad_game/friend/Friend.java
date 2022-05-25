package com.example.good_bad_game.friend;

public class Friend {
    private String nickname;
    private String online;

    public Friend(String nick, String online) {
        this.nickname = nick;
        this.online = online;
    }

    public String getNickname() {
        return nickname;
    }

    public String getOnline(){
        return online;
    }

    public void setOnline(){
        this.online = "온라인";

    }

    public void setOffline(){
        this.online = "오프라인";
    }
}
