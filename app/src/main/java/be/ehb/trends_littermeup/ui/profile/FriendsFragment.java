package be.ehb.trends_littermeup.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentFriendsBinding;
import be.ehb.trends_littermeup.databinding.FragmentProfileBinding;

public class FriendsFragment extends Fragment {
    private FragmentFriendsBinding binding;

    EditText txt_addFriends;
    String txt_searched_friend;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txt_addFriends = root.findViewById(R.id.txt_search);
        txt_addFriends.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_DONE) {
                    // Perform the search
                    String query = textView.getText().toString();
                    search(query);
                    return true;
                }
                return false;
            }
        });
        return root;
    }

    private void search(String query) {
        // Checking if searchbar is empty.
        if (query == null || query.isEmpty()) {
            Toast.makeText(getContext(), "You need to fill, your friends username.", Toast.LENGTH_SHORT).show();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");
        Query searchQuery = usersRef.whereEqualTo("username", query);
        searchQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    // If the searched is succesfull, add the first one.
                    if (!querySnapshot.isEmpty()) {
                        User user = querySnapshot.getDocuments().get(0).toObject(User.class);
                        displayUser(user);
                        //addFriend(user);
                    } else {
                        // No users found
                        Toast.makeText(getContext(), "No users found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // When there is an error for what ever reason.
                    Log.w("SEARCH", "Search failed", task.getException());
                    Toast.makeText(getContext(), "Search failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void displayUser(User user) {
        // Display the user's username
        TextView usernameTextView = requireView().findViewById(R.id.txt_friend_username);
        usernameTextView.setText(user.getUsername());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
