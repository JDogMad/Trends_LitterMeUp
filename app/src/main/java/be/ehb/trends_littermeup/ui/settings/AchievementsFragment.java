package be.ehb.trends_littermeup.ui.settings;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import be.ehb.trends_littermeup.Achievements;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentAchievementsBinding;
import be.ehb.trends_littermeup.databinding.FragmentDashboardBinding;
import be.ehb.trends_littermeup.ui.dashboard.DashboardViewModel;
import be.ehb.trends_littermeup.ui.feed.NewPostFragment;
import be.ehb.trends_littermeup.ui.profile.ProfileFragment;
import be.ehb.trends_littermeup.util.AchievementsAdapater;
import be.ehb.trends_littermeup.util.FeedAdapter;

public class AchievementsFragment extends Fragment {
    private FragmentAchievementsBinding binding;

    Button btn_back;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAchievementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_back = root.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.const_achievements, new SettingsFragment());

                Button button2 = getView().findViewById(R.id.btn_back);

                button2.setVisibility(View.GONE);

                fragmentTransaction.commit();

            }
        });

        SettingsViewModel viewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        AchievementsAdapater achievementsAdapater = new AchievementsAdapater();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rcAchievements.setAdapter(achievementsAdapater);
        binding.rcAchievements.setLayoutManager(layoutManager);
        viewModel.getAllAchievements().observe(getViewLifecycleOwner(), new Observer<List<Achievements>>() {
            @Override
            public void onChanged(List<Achievements> achievements) {
                achievementsAdapater.addItems(achievements);
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
