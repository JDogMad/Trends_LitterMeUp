package be.ehb.trends_littermeup.ui.profile;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import be.ehb.trends_littermeup.R;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    TextView username;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
