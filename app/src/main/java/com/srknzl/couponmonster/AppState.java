package com.srknzl.couponmonster;


import com.srknzl.couponmonster.Data.Coupon;
import com.srknzl.couponmonster.Data.OnlinePerson;

import java.util.Vector;

public class AppState {
    private static final AppState ourInstance = new AppState();
    public boolean connected;
    public Vector<Coupon> coupons;
    public Vector<Coupon> gainedCoupons;
    public Vector<OnlinePerson> onlinePeople;
    public Listener listener;
    Thread listenerThread;
    public OnlinePerson user;
    public boolean attempting;
    public static AppState getInstance() {
        return ourInstance;
    }

    private AppState() {
        connected = false;
        coupons = new Vector<>();
        gainedCoupons = new Vector<>();
        onlinePeople = new Vector<>();
        user = new OnlinePerson();
        attempting = false;
    }
    int removeCoupon(String hash){
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
