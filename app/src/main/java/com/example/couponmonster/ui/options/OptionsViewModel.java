package com.example.couponmonster.ui.options;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OptionsViewModel extends ViewModel {


    private MutableLiveData<String> mText;

    public OptionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Settings:");
    }

    public LiveData<String> getText() {
        return mText;
    }
}