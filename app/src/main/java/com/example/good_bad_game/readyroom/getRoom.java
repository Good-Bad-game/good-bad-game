package com.example.good_bad_game.readyroom;

public class getRoom {
    private  String id;
    private String match_room;
    private String date;
    private String status;
    private String host;

    public String getRoomNumber(){return id;}
    public String getRoomTitle(){return match_room;}
    public String getHost(){return host;}

    getRoom(String host, String match_room){
        match_room = this.match_room;
        host = this.host;
        status = null;
    }

}