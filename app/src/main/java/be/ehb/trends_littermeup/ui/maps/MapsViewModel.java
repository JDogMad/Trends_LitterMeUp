package be.ehb.trends_littermeup.ui.maps;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.ehb.trends_littermeup.LatLngDeserializer;
import be.ehb.trends_littermeup.Post;

public class MapsViewModel extends AndroidViewModel {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MapsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Post>> getAllLocationsDB(){
        MutableLiveData<List<Post>> posts = new MutableLiveData<>();
        CollectionReference postsRef = db.collection("Posts");
        postsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Post> postList = new ArrayList<>();
                for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()) {
                    Map<String, Object> data = documentSnapshot.getData();
                    Post post = new Post();
                    post.setId(((Long) data.get("id")).intValue());
                    post.setTitel((String) data.get("titel"));
                    post.setDescription((String) data.get("description"));

                    Object locationData = data.get("location");
                    if (locationData != null) {
                        // Use LatLngDeserializer to deserialize the "location" field from the map
                        LatLngDeserializer latLngDeserializer = new LatLngDeserializer();
                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> locationMap = (Map<String, Object>) data.get("location");
                        String locationJson = null;
                        try {
                            locationJson = mapper.writeValueAsString(locationMap);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        LatLng location = null;
                        try {
                            location = mapper.readValue(locationJson, LatLng.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        post.setLocation(location);
                    }

                    postList.add(post);
                }
                posts.setValue(postList);
            }
        });
        return posts;
    }

}
