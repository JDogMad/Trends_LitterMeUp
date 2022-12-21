package be.ehb.trends_littermeup.DAO;

import android.os.Build;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import be.ehb.trends_littermeup.User;

public class UserDAO {
    private User selectedUser;
    private DatabaseReference databaseReference;
    private List<User> userList = new ArrayList<>();
    public UserDAO(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    userList.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The Read Failed");
            }
        });

    }
    public Task<Void> add(User user){
        if(user == null){
            //TODO: exeption!
        }
        return databaseReference.child(user.getUid()).setValue(user);
    }
    public User getUserByUid(String uid){
        for (User user: userList) {
        if(user.getUid().equals(uid))
            return user;
        }
        return null;
    }
}


