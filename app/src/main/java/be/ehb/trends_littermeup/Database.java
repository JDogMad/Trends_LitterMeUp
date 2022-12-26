package be.ehb.trends_littermeup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Database {
    final ObjectMapper objectMapper = new ObjectMapper();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public User user;
    public Database() {
    }

    public Task<Void> add(User user){
        return db.collection("Users").document(user.getUid()).set(user);//        return db.collection("Users").add(map);
    }

    public MutableLiveData<User> getUserFromDbByUid(String uid){
        MutableLiveData<User> user = new MutableLiveData<>();
        DocumentReference documentReference = db.collection("Users").document(uid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user.setValue(documentSnapshot.toObject(User.class));
            }
        });
    return user;
    }
}