package com.example.couponmonster;

import java.util.Date;

public class Coupon {
    private String hash;
    private Date creationDate;
    private String problem;
    private int answer;
    private String reward;

    public Coupon(String hash, Date creationDate, String problem, String reward, int answer){
        this.hash = hash;
        this.creationDate = creationDate;
        this.problem = problem;
        this.answer = answer;
        this.reward = reward;
    }
    public Coupon(){
        this.hash = "";
        this.creationDate = new Date();
        this.problem = "";
        this.reward = "";
        this.answer = 0;
    }

    public String getProblem(){
        return this.problem;
    }
    public String getHash(){
        return this.hash;
    }

    public String getReward(){
        return this.reward;
    }
    public boolean checkAnswer(int answer){
        return this.answer == answer;
    }


}
