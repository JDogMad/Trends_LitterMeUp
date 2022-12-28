package be.ehb.trends_littermeup.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

import be.ehb.trends_littermeup.LoginActivity;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    Button termsAndConditions;
    Button privacy;
    Button feedback;
    Button selected_language;

    String[] languageCodes = {"en", "nl", "fr"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btnLogout = root.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

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

        /*feedback = root.findViewById(R.id.btn_feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        selected_language = root.findViewById(R.id.btn_languages);
        selected_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the names of the supported languages
                String[] languageNames = getResources().getStringArray(R.array.language_names);

                // Create the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.select_language)
                        .setItems(languageNames, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Get the selected language code
                                String languageCode = languageCodes[which];

                                // Set the app language to the selected language
                                setLocale(languageCode);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config,
                getContext().getResources().getDisplayMetrics());

        // Refresh the fragment to apply the language change
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new SettingsFragment())
                    .commit();
        }
    }

    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
