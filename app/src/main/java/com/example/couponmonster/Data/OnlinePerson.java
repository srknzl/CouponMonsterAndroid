package com.example.couponmonster.Data;

public class OnlinePerson {
    private String name;
    private String nickname;
    private String lastLogin;

    public OnlinePerson(String name, String nickname, String lastLogin){
        this.name = name;
        this.nickname = nickname;
        this.lastLogin = lastLogin;
    }
    public OnlinePerson(){
        this.name = "";
        this.nickname = "";
        this.lastLogin = "";
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickname;
    }

    public String getLastLogin() {
        return lastLogin;
    }
}
