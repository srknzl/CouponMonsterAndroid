package com.example.couponmonster;

import com.example.couponmonster.Data.Coupon;
import com.example.couponmonster.Data.OnlinePerson;

import java.util.Vector;

public class AppState {
    private static final AppState ourInstance = new AppState();
    public boolean connected;
    public Vector<Coupon> coupons;
    public Vector<OnlinePerson> onlinePeople;
    Listener listener;
    Thread listenerThread;
    public static AppState getInstance() {
        return ourInstance;
    }

    private AppState() {
        connected = false;
        coupons = new Vector<>();
        onlinePeople = new Vector<>();
    }
}
