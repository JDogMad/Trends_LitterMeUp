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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import be.ehb.trends_littermeup.LoginActivity;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    Button termsAndConditions, privacy, feedback, selected_language, logout, darkmode, achievements;
    String languageCode;

    String[] languageCodes = {"en", "nl", "fr"};
    private boolean isDarkModeEnabled = false;

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


        feedback = root.findViewById(R.id.btn_feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the email intent
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"help@littermeup.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello,\n\nI would like to give the following feedback:\n\n");

                // Create a chooser dialog to allow the user to choose an email client
                startActivity(Intent.createChooser(emailIntent, "Send feedback using:"));
            }
        });



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
                                languageCode = languageCodes[which];
                                System.out.println(languageCode);

                                // Set the app language to the selected language
                                setLocale(languageCode);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        logout = root.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });


        // TODO: FIX THIS SHIT
        darkmode = root.findViewById(R.id.btn_nightmode);
        darkmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nightMode = AppCompatDelegate.getDefaultNightMode();

                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    darkmode.setText("Light mode");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    darkmode.setText("Dark mode");
                }
                requireActivity().recreate();
            }
        });

        achievements = root.findViewById(R.id.btn_achievements);
        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new instance of the terms and conditions fragment
                AchievementsFragment achievementsFragment = new AchievementsFragment();

                // Begin the fragment transaction
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.container, achievementsFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
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

    public void setEndTime(){
        Map<String, Object> endData = new HashMap<>();
        endData.put("endTime", FieldValue.serverTimestamp());

        FirebaseFirestore.getInstance()
                .collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update(endData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // End time has been saved
                    }
                });
    }


    private void logoutUser(){
        setEndTime();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
