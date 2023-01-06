package be.ehb.trends_littermeup.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.ehb.trends_littermeup.Post;

public class DashboardViewModel extends AndroidViewModel {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DashboardViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Post>> getAllPostsDB(){
        MutableLiveData<List<Post>> posts = new MutableLiveData<>();
        CollectionReference postsRef = db.collection("Posts");
        postsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Post> postList = new ArrayList<>();
                for (DocumentSnapshot documentSnapchot: queryDocumentSnapshots.getDocuments()) {
                    Post post = documentSnapchot.toObject(Post.class);
                    postList.add(post);
                }
                posts.setValue(postList);
            }
        });
        return posts;
    }

}