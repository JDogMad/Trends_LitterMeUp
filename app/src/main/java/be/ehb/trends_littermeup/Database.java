package be.ehb.trends_littermeup;

import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {
    final ObjectMapper objectMapper = new ObjectMapper();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public User user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    public Database() {
    }

    public Task<Void> add(User user){
        return db.collection("Users").document(user.getUid()).set(user);
    }

    public Task<Void> add(Post post){
        String imageName = post.getNameFile();
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "saved_images");
        File file = new File(myDir, imageName);
        storage.getReference(imageName).putFile(Uri.fromFile(file));
        return db.collection("Posts").document(Integer.toString(post.getId())).set(post);
    }

    public MutableLiveData<List<Post>> getAllPostsDB(){
        MutableLiveData<List<Post>> posts = new MutableLiveData<>();
        CollectionReference postsRef = db.collection("Posts");
        postsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Post> postList = new ArrayList<>();
                for (DocumentSnapshot documentSnapchot: queryDocumentSnapshots.getDocuments()) {
                    Post post = documentSnapchot.toObject(Post.class);
                    postList.add(post);
                }
                posts.setValue(postList);
            }
        });
        return posts;
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

    public Task<Void> changePointsOnUser(int punten, User user){
        DocumentReference documentReference = db.collection("Users").document(user.getUid());
        user.setPoints(punten);
        return documentReference.set(user);
    }
}