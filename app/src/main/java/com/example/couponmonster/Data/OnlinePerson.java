package com.example.couponmonster.Data;

import androidx.annotation.NonNull;

public class OnlinePerson implements Comparable<OnlinePerson>{
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

    @Override
    public int compareTo(OnlinePerson o) {
        return Integer.compare(o.score, this.score);
    }
    @Override
    @NonNull
    public String toString(){
        return "name: " + this.name + ", username: " + this.username + ", score: " + this.score;
    }
}
