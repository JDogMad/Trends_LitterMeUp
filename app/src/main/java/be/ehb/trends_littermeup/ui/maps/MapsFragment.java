package be.ehb.trends_littermeup.ui.maps;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import be.ehb.trends_littermeup.LatLngDeserializer;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentMapsBinding;
import be.ehb.trends_littermeup.ui.home.HomeViewModel;

public class MapsFragment extends Fragment {

    private FragmentMapsBinding binding;
    private GoogleMap map;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                        // Set the fields for the Post object
                        if (data.get("id") instanceof Long) {
                            post.setId(((Long) data.get("id")).intValue());
                        } else if (data.get("id") instanceof Integer) {
                            post.setId((Integer) data.get("id"));
                        }
                        post.setTitel((String) data.get("titel"));
                        post.setDescription((String) data.get("description"));


                        // Check if there is a location in post => avoids errors
                        if (data.containsKey("location")) {
                            // Deserialize the JSON object stored in the "location" field
                            ObjectMapper mapper = new ObjectMapper();
                            String locationJson = null;
                            try {
                                locationJson = mapper.writeValueAsString(data.get("location"));
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            JsonParser parser = null;
                            try {
                                parser = mapper.getFactory().createParser(locationJson);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            LatLngDeserializer latLngDeserializer = new LatLngDeserializer();
                            LatLng location = null;
                            try {
                                location = latLngDeserializer.deserialize(parser, null);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // Add a marker to the map using the deserialized LatLng object
                            map.addMarker(new MarkerOptions().position(location)
                                    .title(post.getTitel())
                                    .snippet(post.getDescription()));

                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
                            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(@NonNull Marker marker) {
                                        // Do something here when the marker is clicked
                                        JobFragment jobFragment = new JobFragment();
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.const_maps, jobFragment);
                                        ft.addToBackStack(null);

                                        Bundle bundle = new Bundle();
                                        bundle.putString("postTitle", marker.getTitle());
                                        bundle.putString("postDescription", marker.getSnippet());
                                        bundle.putInt("postId", post.getId());
                                        jobFragment.setArguments(bundle);

                                        ft.commit();
                                        return false;
                                }
                            });
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
