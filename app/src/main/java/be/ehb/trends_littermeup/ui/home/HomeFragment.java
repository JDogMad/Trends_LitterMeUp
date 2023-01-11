package be.ehb.trends_littermeup.ui.home;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.LoginActivity;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;

    TextView txt_greeting, txt_points, txt_cash;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // A final check to see if the user is correctly logged in
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {
            System.out.println("User is null: " + mAuth.getCurrentUser());
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }

        // TODO: CHANGE ADAPTER TO SHOW PLACES
        // TODO: UPDATE TABLE
        // TODO: LAYOUT CHANGE
        txt_greeting = root.findViewById(R.id.txt_greeting);
        txt_points = root.findViewById(R.id.txt_points);
        txt_cash = root.findViewById(R.id.txt_cash);
        fillUserData(txt_greeting, txt_points, txt_cash);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void fillUserData(TextView txt_greeting, TextView txt_points, TextView txt_cash){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Database database = new Database();
        Task<User> userTask = database.getUserFromDbByUidTask(mAuth.getUid());
        userTask.addOnCompleteListener(new OnCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull Task<User> task) {
                if (task.isSuccessful()) {
                    User user = task.getResult();
                    if(user != null){
                        System.out.println(user.toString());
                        txt_greeting.setText("Hi " + user.getUsername());
                        txt_points.setText(user.getPoints() + " LitterPoints");
                        txt_cash.setText("€" + (int) user.getPoints() / 100);
                    } else {
                        System.out.println("User is null");
                    }

                } else {
                    // Handle error
                }
            }
        });
    }
}