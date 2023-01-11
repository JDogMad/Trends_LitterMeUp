package be.ehb.trends_littermeup.ui.home;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.LoginActivity;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.UsageDurationCallback;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentHomeBinding;
import be.ehb.trends_littermeup.ui.dashboard.DashboardViewModel;
import be.ehb.trends_littermeup.ui.maps.MapsViewModel;
import be.ehb.trends_littermeup.util.FeedAdapter;
import be.ehb.trends_littermeup.util.MapsAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;

    TextView txt_greeting, txt_points, txt_cash, txt_contribution, txt_hours, txt_days;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // A final check to see if the user is correctly logged in
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {
            System.out.println("User is null: " + mAuth.getCurrentUser());
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }

        // Searching for the instances
        txt_greeting = root.findViewById(R.id.txt_greeting);
        txt_points = root.findViewById(R.id.txt_points);
        txt_cash = root.findViewById(R.id.txt_cash);
        txt_contribution = root.findViewById(R.id.txt_contribution);

        // Calling the method to fill the data needed in the homefragment
        fillUserData(txt_greeting, txt_points, txt_cash, txt_contribution);

        // Searching for the instances
        txt_hours = root.findViewById(R.id.txt_contribution_hours);
        txt_days = root.findViewById(R.id.txt_contributions_days);
        // Searching for the differences of time spent on the app
        getUserUsageDuration(new UsageDurationCallback() {
            @Override
            public void onSuccess(long diffHours, long diffDays) {
                // Update the text field with the number of hours spent
                txt_hours.setText(String.valueOf(diffHours) + " hours");
                //Update the text field with the number of days spent
                txt_days.setText(String.valueOf(diffDays) + " days");
            }

            @Override
            public void onError(String errorMessage) {
                // Handle the error
                Log.e("Error getting duration", errorMessage);
            }
        });

        // Cycling through adapter to set the different markers in list form
        MapsViewModel viewModel = new ViewModelProvider(getActivity()).get(MapsViewModel.class);
        MapsAdapter mapsAdapter = new MapsAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rc.setAdapter(mapsAdapter);
        binding.rc.setLayoutManager(layoutManager);
        viewModel.getAllLocationsDB().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                mapsAdapter.addItems(posts);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Method to set user data but it will go async
    // So that app has the time to fetch the data from a new user
    public void fillUserData(TextView txt_greeting, TextView txt_points, TextView txt_cash, TextView txt_contribution){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Database database = new Database();
        Task<User> userTask = database.getUserFromDbByUidTask(mAuth.getUid());
        userTask.addOnCompleteListener(new OnCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull Task<User> task) {
                if (task.isSuccessful()) {
                    User user = task.getResult();
                    if(user != null){
                        System.out.println(user.toString());
                        txt_greeting.setText("Hi " + user.getUsername());
                        txt_points.setText(user.getPoints() + " LitterPoints");
                        txt_cash.setText("€" + (int) user.getPoints() / 100);
                        System.out.println(user.getContributions());
                        txt_contribution.setText(String.valueOf(user.getContributions()) + " contributions");
                    } else {
                        System.out.println("User is null");
                    }
                } else {
                    // Handle error
                }
            }
        });
    }


    public void getUserUsageDuration(final UsageDurationCallback callback) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        firestore.collection("Users").document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Timestamp start = document.getTimestamp("startTime");
                                Timestamp end = document.getTimestamp("endTime");
                                if (start != null && end != null) {
                                    long startTimestamp = start.getSeconds() * 1000;
                                    long endTimestamp = end.getSeconds() * 1000;
                                    long diffTimestamp = endTimestamp - startTimestamp;
                                    Log.d("diffTimestamp",String.valueOf(diffTimestamp));
                                    long diffHours = TimeUnit.MILLISECONDS.toHours(diffTimestamp);
                                    long diffDays = TimeUnit.MILLISECONDS.toDays(diffTimestamp);
                                    callback.onSuccess(diffHours, diffDays);
                                } else {
                                    callback.onError("Start or end time not found");
                                }
                            } else {
                                callback.onError("Document does not exist");
                            }
                        } else {
                            callback.onError(task.getException().getMessage());
                        }
                    }
                });
    }

}