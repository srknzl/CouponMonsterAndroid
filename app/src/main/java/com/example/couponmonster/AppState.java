package com.example.couponmonster;


import com.example.couponmonster.Data.Coupon;
import com.example.couponmonster.Data.CouponWinResult;
import com.example.couponmonster.Data.OnlinePerson;

import java.util.Vector;

public class AppState {
    private static final AppState ourInstance = new AppState();
    public boolean connected;
    public Vector<Coupon> coupons;
    public Vector<OnlinePerson> onlinePeople;
    public Listener listener;
    Thread listenerThread;
    public static AppState getInstance() {
        return ourInstance;
    }

    private AppState() {
        connected = false;
        coupons = new Vector<>();
        onlinePeople = new Vector<>();
    }
    public CouponWinResult removeCoupon(String hash){
        for (int i =0;i<coupons.size();i++){
            Coupon c = coupons.elementAt(i);
            if(c.getHash().equals(hash)){
                coupons.removeElementAt(i);
                return new CouponWinResult(c.getReward(),"",i);
            }
        }
        return new CouponWinResult("","",-1);
    }
}
