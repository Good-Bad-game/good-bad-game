package com.example.good_bad_game;

public class Target {

    private int uid;
    private int target;

    public int getUid(){
        return uid;
    }

    public int getTarget(){
        return target;
    }

    Target(int uid, int target){
        this.uid = uid;
        this.target = target;
    }

}
