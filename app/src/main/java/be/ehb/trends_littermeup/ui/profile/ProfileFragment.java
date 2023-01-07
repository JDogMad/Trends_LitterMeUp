package be.ehb.trends_littermeup.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentProfileBinding;
import be.ehb.trends_littermeup.ui.dashboard.DashboardViewModel;
import be.ehb.trends_littermeup.ui.home.HomeViewModel;
import be.ehb.trends_littermeup.ui.shop.Shop100Fragment;
import be.ehb.trends_littermeup.ui.shop.Shop50Fragment;
import be.ehb.trends_littermeup.ui.shop.Shop5Fragment;
import be.ehb.trends_littermeup.util.FeedAdapter;
import be.ehb.trends_littermeup.util.FriendsAdapter;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private Button btn_coupon_5, btn_coupon_50, btn_coupon_100, btn_addFriends;

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

        // Redirect to the shop
        btn_coupon_5 = root.findViewById(R.id.btn_coupon_5);
        btn_coupon_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.const_profile, new Shop5Fragment());

                Button button1 = getView().findViewById(R.id.btn_coupon_5);
                Button button2 = getView().findViewById(R.id.btn_coupon_50);
                Button button3 = getView().findViewById(R.id.btn_coupon_100);
                TextView button4 = getView().findViewById(R.id.btn_moreFriends);

                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                button4.setVisibility(View.GONE);

                fragmentTransaction.commit();
            }
        });

        btn_coupon_50 = root.findViewById(R.id.btn_coupon_50);
        btn_coupon_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.const_profile, new Shop50Fragment());

                Button button1 = getView().findViewById(R.id.btn_coupon_5);
                Button button2 = getView().findViewById(R.id.btn_coupon_50);
                Button button3 = getView().findViewById(R.id.btn_coupon_100);
                TextView button4 = getView().findViewById(R.id.btn_moreFriends);

                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                button4.setVisibility(View.GONE);

                fragmentTransaction.commit();
            }
        });

        btn_coupon_100 = root.findViewById(R.id.btn_coupon_100);
        btn_coupon_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.const_profile, new Shop100Fragment());

                Button button1 = getView().findViewById(R.id.btn_coupon_5);
                Button button2 = getView().findViewById(R.id.btn_coupon_50);
                Button button3 = getView().findViewById(R.id.btn_coupon_100);
                TextView button4 = getView().findViewById(R.id.btn_moreFriends);

                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                button4.setVisibility(View.GONE);

                fragmentTransaction.commit();
            }
        });

        btn_addFriends = root.findViewById(R.id.btn_moreFriends);
        btn_addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.const_profile, new FriendsFragment());

                Button button1 = getView().findViewById(R.id.btn_coupon_5);
                Button button2 = getView().findViewById(R.id.btn_coupon_50);
                Button button3 = getView().findViewById(R.id.btn_coupon_100);
                TextView button4 = getView().findViewById(R.id.btn_moreFriends);

                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                button4.setVisibility(View.GONE);

                fragmentTransaction.commit();
            }
        });

        ProfileViewModel viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        FriendsAdapter friendsAdapter = new FriendsAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcFriends.setAdapter(friendsAdapter);
        binding.rcFriends.setLayoutManager(layoutManager);
        viewModel.getAllFriends().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                friendsAdapter.addItems(users);
            }
        });

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
        database.getUserFromDbByUid(mAuth.getUid()).observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                username.setText(user.getUsername());
                points.setText(Integer.toString(user.getPoints()) + " points");
                level.setText("Level " +  Integer.toString(user.getUserLevel(user.getPoints())));
            }
        });
    }
}
