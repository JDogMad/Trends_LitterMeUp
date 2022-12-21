package be.ehb.trends_littermeup.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import be.ehb.trends_littermeup.databinding.FragmentProfileBinding;
import be.ehb.trends_littermeup.databinding.FragmentSettingsBinding;
import be.ehb.trends_littermeup.ui.home.HomeViewModel;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel profileViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
