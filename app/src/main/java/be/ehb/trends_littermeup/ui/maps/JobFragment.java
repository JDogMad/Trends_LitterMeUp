package be.ehb.trends_littermeup.ui.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentJobBinding;
import be.ehb.trends_littermeup.ui.dashboard.DashboardFragment;
import be.ehb.trends_littermeup.ui.dashboard.DashboardViewModel;

public class JobFragment extends Fragment {
    private FragmentJobBinding binding;

    TextView job_info, txt_feedback;
    Button btn_take_job;
    String postDescription, postTitle;
    int postId, newTotal, contribution;
    Database database = new Database();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJobBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            postId = bundle.getInt("postId");
            postTitle = bundle.getString("postTitle");
            postDescription = bundle.getString("postDescription");
        }

        job_info = root.findViewById(R.id.job_info_description);
        job_info.setText(postDescription);

        btn_take_job = root.findViewById(R.id.btn_jobdone);
        btn_take_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Use the title of the marker passed through the bundle to query for the correct post in the database
                db.collection("Posts").whereEqualTo("id", postId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();

                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                database.getUserFromDbByUid(mAuth.getUid()).observe(getViewLifecycleOwner(), new Observer<User>() {
                                    @Override
                                    public void onChanged(User user) {
                                        int points = user.getPoints();
                                        int contributions = user.getContributions();
                                        newTotal = points + getRandomInt(1, 150);
                                        contribution = contributions + 1;
                                        database.changePointsOnUser(newTotal, user);
                                        database.changeContributionsOnUser(contribution, user);
                                    }
                                });
                                Toast.makeText(getContext(), "Good job!", Toast.LENGTH_SHORT).show();

                                FragmentManager fragmentManager = getFragmentManager();
                                if (fragmentManager != null) {
                                    fragmentManager.beginTransaction().replace(R.id.const_job, new MapsFragment()).commit();
                                }

                            }
                        } else {
                            Toast.makeText(getContext(), "Something went wrong, try again later!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public int getRandomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
