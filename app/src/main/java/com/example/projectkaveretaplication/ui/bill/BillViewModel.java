package com.example.projectkaveretaplication.ui.bill;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BillViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BillViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("חשבונית");
    }

    public LiveData<String> getText() {
        return mText;
    }
}