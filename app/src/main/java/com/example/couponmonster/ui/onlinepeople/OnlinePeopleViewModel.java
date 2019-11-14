package com.example.couponmonster.ui.onlinepeople;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.couponmonster.OnlinePerson;

import java.util.Vector;

public class OnlinePeopleViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Vector<OnlinePerson>> onlinePeople;

    public OnlinePeopleViewModel() {
        mText = new MutableLiveData<>();
        onlinePeople = new MutableLiveData<>();
        Vector<OnlinePerson> onlinePeopleVec = new Vector<>();

        OnlinePerson onlinePerson1 = new OnlinePerson("Serkan Özel","srknzl","10.10.2017");
        OnlinePerson onlinePerson2 = new OnlinePerson("Mustafa Özel","mstfzl","13.08.2019");
        OnlinePerson onlinePerson3 = new OnlinePerson("Hülya Özel","hlyzl","12.10.2019");
        OnlinePerson onlinePerson4 = new OnlinePerson("Mahmut Özel","mhmtzl","11.10.2019");

        onlinePeopleVec.add(onlinePerson1);
        onlinePeopleVec.add(onlinePerson2);
        onlinePeopleVec.add(onlinePerson3);
        onlinePeopleVec.add(onlinePerson4);
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