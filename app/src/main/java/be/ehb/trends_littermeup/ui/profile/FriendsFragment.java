package be.ehb.trends_littermeup.ui.profile;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentFriendsBinding;
import be.ehb.trends_littermeup.databinding.FragmentProfileBinding;
import be.ehb.trends_littermeup.ui.dashboard.DashboardViewModel;
import be.ehb.trends_littermeup.util.FeedAdapter;
import be.ehb.trends_littermeup.util.FriendsListAdapter;

public class FriendsFragment extends Fragment {
    private FragmentFriendsBinding binding;

    EditText txt_addFriends;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txt_addFriends = root.findViewById(R.id.txt_search);
        txt_addFriends.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //Checks if the done button/search button has been clicked
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
        CollectionReference usersRef = db.collection("Users");
        Query searchQuery = usersRef.whereEqualTo("username", query);
        searchQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    List<User> users = new ArrayList<>();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        User user = document.toObject(User.class);
                        users.add(user);
                    }
                    displayUser(users);
                } else {
                    // When there is an error for what ever reason.
                    Log.w("SEARCH", "Search failed", task.getException());
                    Toast.makeText(getContext(), "Search failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void displayUser(List<User> friends) {
        FriendsListAdapter adapter = new FriendsListAdapter();
        adapter.addItems(friends);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rcSearchedFriends.setAdapter(adapter);
        binding.rcSearchedFriends.setLayoutManager(layoutManager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
