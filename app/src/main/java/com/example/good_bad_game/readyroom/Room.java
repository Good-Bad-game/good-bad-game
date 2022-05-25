package com.example.good_bad_game.readyroom;

public class Room {
    private  String num;
    private String title;

    public Room(String num, String title){ this.num = num; this.title = title;}

    public String getRoomNumber(){return num;}
    public String getRoomTitle(){return title;}

}
