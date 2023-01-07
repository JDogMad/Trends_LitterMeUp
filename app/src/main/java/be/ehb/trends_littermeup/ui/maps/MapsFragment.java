package be.ehb.trends_littermeup.ui.maps;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import be.ehb.trends_littermeup.LatLngDeserializer;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.databinding.FragmentMapsBinding;
import be.ehb.trends_littermeup.ui.home.HomeViewModel;

public class MapsFragment extends Fragment {

    private FragmentMapsBinding binding;
    private GoogleMap map;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String title;
    private LatLng locationLL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    private OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            map = googleMap;
            drawAnnotations();
        }
    };

    private void drawAnnotations() {
        db.collection("Posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        Post post = new Post();
                        title = (String) data.get("title");

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
                            locationLL = location;

                            if(locationLL != null){
                                map.addMarker(new MarkerOptions().position(post.getLocation()).title(post.getTitel()));
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(post.getLocation(), 10));
                            }
                        }
                    }

                } else {
                    Log.d(TAG, Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.contMaps.getMapAsync(onMapReadyCallback);
        binding.contMaps.getMapAsync(onMapReadyCallback);
        binding.contMaps.onCreate(savedInstanceState);
    }

    @Override
    public void onPause(){
        super.onPause();
        binding.contMaps.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        binding.contMaps.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.contMaps.onDestroy();
        binding = null;
    }
}
