package com.example.couponmonster.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.couponmonster.AppState;
import com.example.couponmonster.Data.Coupon;

import java.util.Vector;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Coupons");

    }

    public LiveData<String> getText() {
        return mText;
    }

}