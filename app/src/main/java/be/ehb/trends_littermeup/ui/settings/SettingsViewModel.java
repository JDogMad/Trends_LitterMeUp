package be.ehb.trends_littermeup.ui.settings;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import be.ehb.trends_littermeup.Achievements;

public class SettingsViewModel extends AndroidViewModel {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Achievements>> getAllAchievements(){
        MutableLiveData<List<Achievements>> data = new MutableLiveData<>();
        CollectionReference reference = db.collection("Achievements");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        reference.whereArrayContains("userId", mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Achievements> achievements = new ArrayList<>();
                    for(QueryDocumentSnapshot document: task.getResult()){
                        String title = document.getString("title");
                        String description = document.getString("description");
                        int points = document.getLong("points").intValue();
                        achievements.add(new Achievements(title, description, points));
                    }
                    data.setValue(achievements);
                }else{
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        return data;
    }
}
