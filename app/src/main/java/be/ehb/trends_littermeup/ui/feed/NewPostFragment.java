package be.ehb.trends_littermeup.ui.feed;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.RegisterActivity;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentNewpostBinding;
import be.ehb.trends_littermeup.ui.dashboard.DashboardFragment;

public class NewPostFragment extends Fragment implements LocationListener {
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private static final int REQUEST_LOCATION_PERMISSION = 3;

    private FragmentNewpostBinding binding;
    private Bitmap bitmap;
    private Database db = new Database();
    private FirebaseFirestore fDb = FirebaseFirestore.getInstance();

    LocationManager locationManager;
    Location location;
    LatLng latLng;
    private double latitude, longitude;

    NewPostViewModel viewModel;
    TextView txt_newPostTitle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewpostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getUserLocation();

        Button addPic = root.findViewById(R.id.btn_addPic);
        EditText titlePost = root.findViewById(R.id.txt_newPost_title);
        EditText descriptionPost = root.findViewById(R.id.txt_newPost_description);
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAndSavePicture();
            }
        });

        Button newPost = root.findViewById(R.id.btn_post);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(titlePost.getText().toString().isEmpty()) && !(titlePost.getText().toString().isEmpty()) && bitmap != null){
                    db.getPostCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            int tempId = integer.intValue() + 1;
                            Post post = new Post(bitmap, titlePost.getText().toString(), descriptionPost.getText().toString(), latLng,tempId);
                            post.setNameFile("Image-" + post.getId() + ".jpg");
                            savePicture(post);
                            db.add(post);
                            FragmentManager fragmentManager = getFragmentManager();
                            if (fragmentManager != null) {
                                Button button1 = getView().findViewById(R.id.btn_addPic);
                                Button button2 = getView().findViewById(R.id.btn_post);

                                button1.setVisibility(View.GONE);
                                button2.setVisibility(View.GONE);

                                fragmentManager.beginTransaction().replace(R.id.const_newpost, new DashboardFragment()).commit();
                            }
                        }
                    });

                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                }else{
                    if(titlePost.getText().toString().isEmpty()){
                        titlePost.setBackground(getResources().getDrawable(R.drawable.app_shape_2_error));
                    }
                    if(descriptionPost.getText().toString().isEmpty()){
                        descriptionPost.setBackground(getResources().getDrawable(R.drawable.app_shape_5_error));
                    }
                    if(bitmap == null){
                        Toast.makeText(getActivity(), "No Picture included", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return root;
    }


    public void takeAndSavePicture() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Permissions are not granted, request them
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap = imageBitmap;
        }
    }


    public void savePicture(Post post) {
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "saved_images");
        boolean success = true;
        if (!myDir.exists()) {
            success = myDir.mkdirs();
        }
        if (success) {

            File file = new File(myDir, post.getNameFile());
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                post.getBitmap().compress(Bitmap.CompressFormat.JPEG, 90, out);

                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, take picture
                takeAndSavePicture();
            } else {
                // Permission is denied, show message to user
                Toast.makeText(getActivity(), "CAMERA permission is required to take a picture", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void getUserLocation() {
        //TODO: PERMISSION DENIED OR NOT GIVEN => BE SURE TO ASK
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);

        // A listener for any updates of location
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Is changed when there is a new provider
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                latLng = new LatLng(latitude, longitude);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
            return;
        }

        List<String> providers = locationManager.getProviders(true);
        if (!providers.isEmpty()) {
            String provider = providers.get(0);
            locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
        }

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            latLng = new LatLng(latitude, longitude);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        // Update the latitude and longitude when the location changes
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        latLng = new LatLng(latitude, longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
