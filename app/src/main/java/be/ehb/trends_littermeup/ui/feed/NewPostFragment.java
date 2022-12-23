package be.ehb.trends_littermeup.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import be.ehb.trends_littermeup.databinding.FragmentDashboardBinding;
import be.ehb.trends_littermeup.databinding.FragmentNewpostBinding;
import be.ehb.trends_littermeup.ui.dashboard.DashboardViewModel;

public class NewPostFragment extends Fragment {
    private FragmentNewpostBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewPostViewModel newPostViewModel =
                new ViewModelProvider(this).get(NewPostViewModel.class);

        binding = FragmentNewpostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
