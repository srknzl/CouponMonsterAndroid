package com.example.couponmonster;


import com.example.couponmonster.Data.Coupon;
import com.example.couponmonster.Data.OnlinePerson;

import java.util.Vector;

public class AppState {
    private static final AppState ourInstance = new AppState();
    public boolean connected;
    public Vector<Coupon> coupons;
    public Vector<OnlinePerson> onlinePeople;
    public Listener listener;
    Thread listenerThread;
    public OnlinePerson user;
    public static AppState getInstance() {
        return ourInstance;
    }

    private AppState() {
        connected = false;
        coupons = new Vector<>();
        onlinePeople = new Vector<>();
    }
    public int removeCoupon(String hash){
        for (int i =0;i<coupons.size();i++){
            Coupon c = coupons.elementAt(i);
            if(c.getHash().equals(hash)){
                coupons.removeElementAt(i);
                return i;
            }
        }
        return -1;
    }
}
