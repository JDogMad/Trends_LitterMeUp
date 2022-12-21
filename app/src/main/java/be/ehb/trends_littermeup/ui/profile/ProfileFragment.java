package be.ehb.trends_littermeup.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

import be.ehb.trends_littermeup.DAO.UserDAO;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentProfileBinding;
import be.ehb.trends_littermeup.ui.home.HomeViewModel;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private UserDAO userDAO = new UserDAO();
    private FirebaseUser loggedInUser = FirebaseAuth.getInstance().getCurrentUser();
    private User selectedUser;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel profileViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        selectedUser = userDAO.getUserByUid(loggedInUser.getUid());
        TextView username = root.findViewById(R.id.txt_username);
        TextView points = root.findViewById(R.id.txt_points);
        TextView level = root.findViewById(R.id.txt_level);
        level.setText("Level: " + selectedUser.getUserLevel(selectedUser.getPoints()).toString());
        points.setText("LitterPoints: " + selectedUser.getPoints());
        username.setText("Username: " + selectedUser.getUsername());

        //final TextView textView = binding.textie;
        //profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

