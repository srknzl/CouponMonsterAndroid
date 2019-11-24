package com.example.couponmonster.Data;

public class OnlinePerson {
    public String name;
    public String username;
    public int score;

    public OnlinePerson(String name, String username, int score){
        this.name = name;
        this.username = username;
        this.score = score;
    }
    public OnlinePerson(){
        this.name = "";
        this.username = "";
        this.score = 0;
    }
}
