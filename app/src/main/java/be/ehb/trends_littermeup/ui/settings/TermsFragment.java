package be.ehb.trends_littermeup.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentSettingsBinding;
import be.ehb.trends_littermeup.databinding.FragmentTermsConditionsBinding;
import be.ehb.trends_littermeup.ui.home.HomeViewModel;

public class TermsFragment extends Fragment {
    private FragmentTermsConditionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTermsConditionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
