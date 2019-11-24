package com.example.couponmonster.ui.onlinepeople;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.couponmonster.Data.OnlinePerson;

import java.util.Vector;

public class OnlinePeopleViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public MutableLiveData<Vector<OnlinePerson>> onlinePeople;

    public OnlinePeopleViewModel() {
        mText = new MutableLiveData<>();
        onlinePeople = new MutableLiveData<>();
        Vector<OnlinePerson> onlinePeopleVec = new Vector<>();

        onlinePeople.setValue(onlinePeopleVec);

        mText.setValue(onlinePeople.getValue().size() + " Online Users");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Vector<OnlinePerson>> getOnlinePeople(){
        return onlinePeople;
    }
}