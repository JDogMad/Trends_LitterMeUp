package be.ehb.trends_littermeup.ui.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.Locale;

import be.ehb.trends_littermeup.LoginActivity;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    Button termsAndConditions, privacy, feedback, selected_language, logout, darkmode;
    String languageCode;

    String[] languageCodes = {"en", "nl", "fr"};

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
        /*darkmode = root.findViewById(R.id.btn_nightmode);
        darkmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentNightMode = AppCompatDelegate.getDefaultNightMode();
                if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    // Switch to light theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    // Switch to dark theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                // Get the parent activity
                //getActivity().recreate();
            }
        });*/

        // TODO: THINK ABOUT ACHIEVEMENTS

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
