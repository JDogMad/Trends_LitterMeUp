package be.ehb.trends_littermeup.DAO;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import be.ehb.trends_littermeup.User;

public class UserDAO {
    private DatabaseReference databaseReference;
    public UserDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());

    }
    public Task<Void> add(User user){
        if(user == null){
            //TODO: exeption!
        }
        return databaseReference.child(user.getUid()).setValue(user);
    }
}
