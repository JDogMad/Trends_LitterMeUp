package be.ehb.trends_littermeup;

import android.graphics.drawable.Drawable;
import android.media.Image;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.util.List;
public class User {
    private List<Integer> friendId;
    private String email, username;
    private int points,id, contributions;
    private double balance;
    private Image profilePicture;
    private String uid;
    private Timestamp startTime, endTime;


    public User() {
    }

    public User(String email, String username, String uid, int id) {
        this.uid = uid;
        this.points = 0;
        this.balance = 0;
        this.startTime = Timestamp.now();
        this.endTime = Timestamp.now();
        this.contributions = 0;
        this.id = id;
        this.email = email;
        this.username = username;
    }


    @Override
    public String toString() {
        return "User{" +
                "friendId=" + friendId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", points=" + points +
                ", balance=" + balance +
                ", profilePicture=" + profilePicture +
                ", uid='" + uid + '\'' +
                '}';
    }

    public Integer getUserLevel(int points){
        Integer level = (int) Math.floor(Math.sqrt(points) / 10);

        return level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getFriendId() {
        return friendId;
    }

    public void setFriendId(List<Integer> friendId) {
        this.friendId = friendId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getUid() {
        return uid;
    }
    @Exclude
    public User getUser(){
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}