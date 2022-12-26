package be.ehb.trends_littermeup;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Database {
    final ObjectMapper objectMapper = new ObjectMapper();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Database() {
    }

    public Task<DocumentReference> add(User user){
        Map<String, Object> map = objectMapper.convertValue(user, new TypeReference<Map<String, Object>>() {});
        return db.collection("Users").add(map);
    }
}
