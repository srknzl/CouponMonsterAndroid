package com.example.couponmonster.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.couponmonster.Coupon;

import java.util.Date;
import java.util.Vector;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Vector<Coupon>> coupons;

    public HomeViewModel() {
        coupons = new MutableLiveData<>();

        Vector<Coupon> couponVec = new Vector<Coupon>();

        Coupon coupon1 = new Coupon("#123123123sadaskdj1k2j",new Date(),"1+1=?","10 tl discount for 100 tl shopping",2);
        Coupon coupon2 = new Coupon("#ikjıurğqwpencçzmx<zdğ",new Date(),"1+2=?","11 tl discount for 110 tl shopping",3);
        Coupon coupon3 = new Coupon("#asdasdasdşqidklşkqweş",new Date(),"1+3=?","12 tl discount for 120 tl shopping",4);
        Coupon coupon4 = new Coupon("#üğp*94*10k31k23livncm",new Date(),"1+4=?","13 tl discount for 130 tl shopping",5);

        couponVec.add(coupon1);
        couponVec.add(coupon2);
        couponVec.add(coupon3);
        couponVec.add(coupon4);
        coupons.setValue(couponVec);

        mText = new MutableLiveData<>();
        mText.setValue("Coupons");

    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Vector<Coupon>> getCoupons(){
        return coupons;
    }

}