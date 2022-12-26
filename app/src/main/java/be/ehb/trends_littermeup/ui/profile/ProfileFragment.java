package be.ehb.trends_littermeup.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentProfileBinding;
import be.ehb.trends_littermeup.ui.home.HomeViewModel;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel profileViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView username = root.findViewById(R.id.txt_username);
        TextView points = root.findViewById(R.id.txt_points);
        TextView level = root.findViewById(R.id.txt_level);
        fillUserData(username,points,level);
        // TODO: Don't forget to call the adapter and link it with the ui

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void fillUserData(TextView username, TextView points, TextView level){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Database database = new Database();
        User help;
        database.getUserFromDbByUid(mAuth.getUid()).observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                System.out.println(user.getPoints());
            }
        });
    }
}
