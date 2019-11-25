package com.example.couponmonster.Data;


public class Coupon {
    private String hash;
    private String problem;
    private String reward;

    public int getSolveTime() {
        return solveTime;
    }

    private int solveTime;

    public Coupon(String hash, String problem, String reward, int solveTime){
        this.hash = hash;
        this.problem = problem;
        this.reward = reward;
        this.solveTime = solveTime;
    }
    public Coupon(){
        this.hash = "";
        this.problem = "";
        this.reward = "";
    }
    public Coupon(Coupon another) {
        this.hash = another.hash;
        this.reward = another.reward;
        this.problem = another.problem;
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


}
