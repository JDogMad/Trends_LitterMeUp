package be.ehb.trends_littermeup.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardViewModel extends AndroidViewModel {
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public DashboardViewModel(@NonNull Application application) {
        super(application);
    }

    // Voorbeeld
    /*public LiveData<List<Posts>> getAllNotes(){
        // Hier komt u get posts shit
        return noteDataBass.getNoteDAO().getAllNotes();
    }*/
}