package be.ehb.trends_littermeup.ui.profile;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.ehb.trends_littermeup.LatLngDeserializer;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.ui.feed.NewPostFragment;

public class ProfileViewModel extends AndroidViewModel {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<List<User>> getAllFriends(){
        MutableLiveData<List<User>> friendsList = new MutableLiveData<>();
        CollectionReference userRef = db.collection("Users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userRef.document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User currentUser = documentSnapshot.toObject(User.class);
                if(currentUser.getFriendId() != null){
                    List<Integer> friendIds = currentUser.getFriendId();
                    System.out.println("FriendsIds" + friendIds);

                    List<User> friends = new ArrayList<>();
                    for (Integer friendId : friendIds) {
                        userRef.whereEqualTo("id", friendId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {
                                for (DocumentSnapshot snapshot : querySnapshot.getDocuments()) {
                                    User friend = snapshot.toObject(User.class);
                                    friends.add(friend);
                                    System.out.println("Friends: " + friends.toString());
                                    System.out.println("Friendo: " + friend);
                                }
                                friendsList.setValue(friends);
                                System.out.println("List: " + friendsList.toString());
                            }
                        });
                    }
                }
            }
        });

        return friendsList;
    }


}
