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
