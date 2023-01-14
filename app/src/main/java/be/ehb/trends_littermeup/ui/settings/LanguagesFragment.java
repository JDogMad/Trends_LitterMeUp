package be.ehb.trends_littermeup.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentLanguagesBinding;
import be.ehb.trends_littermeup.databinding.FragmentTermsConditionsBinding;

public class LanguagesFragment extends Fragment {
    private FragmentLanguagesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLanguagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
