package be.ehb.trends_littermeup.ui.feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewPostViewModel extends ViewModel {
    private MutableLiveData<String> title;

    public NewPostViewModel() {
        title = new MutableLiveData<>();
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }
}
