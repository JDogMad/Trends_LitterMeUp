package be.ehb.trends_littermeup.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentProfileBinding;
import be.ehb.trends_littermeup.databinding.FragmentSettingsBinding;
import be.ehb.trends_littermeup.ui.home.HomeViewModel;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    Button termsAndConditions;
    Button privacy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        termsAndConditions = root.findViewById(R.id.btn_terms);
        termsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new instance of the terms and conditions fragment
                TermsFragment termsFragment = new TermsFragment();

                // Begin the fragment transaction
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.container, termsFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        privacy = root.findViewById(R.id.btn_privacy);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.freeprivacypolicy.com/live/6b33855f-f75f-46ba-b440-c6920464db61";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Start the activity
                startActivity(intent);
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
