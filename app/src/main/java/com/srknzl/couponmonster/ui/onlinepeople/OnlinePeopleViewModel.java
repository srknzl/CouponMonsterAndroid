package com.srknzl.couponmonster.ui.onlinepeople;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srknzl.couponmonster.AppState;

public class OnlinePeopleViewModel extends ViewModel {

    public static MutableLiveData<String> mText;

    public OnlinePeopleViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue(AppState.getInstance().onlinePeople.size() + " Online Users");
    }

    public LiveData<String> getText() {
        return mText;
    }
}