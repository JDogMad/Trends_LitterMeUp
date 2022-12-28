package be.ehb.trends_littermeup;

import android.graphics.Bitmap;

import com.google.firebase.firestore.Exclude;

public class Post {
    private Bitmap bitmap;
    private int id;
    private String titel;
    private String description;
    private static int counter = 1;
    private String nameFile;

    public Post() {
    }

    public Post(Bitmap bitmap, String titel, String description) {
        this.bitmap = bitmap;
        this.titel = titel;
        this.description = description;
        this.id = counter;
        counter++;
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

    public static int getCounter() {
        return counter;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
}