package be.ehb.trends_littermeup;

import android.graphics.Bitmap;


import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class Post {
    private Bitmap bitmap;
    private int id;
    private String titel;
    private String description;

    private String nameFile;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @JsonDeserialize(using = LatLngDeserializer.class)
    private LatLng location;

    public Post() {
    }

    public Post(Bitmap bitmap, String titel, String description, LatLng location, int id) {
        this.bitmap = bitmap;
        this.titel = titel;
        this.description = description;
        this.id = id;
        this.location = location;
    }

    @Exclude
    public Bitmap getBitmap() {
        return bitmap;
    }
    @Exclude
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }


    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
}
