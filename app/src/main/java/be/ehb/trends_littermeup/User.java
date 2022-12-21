package be.ehb.trends_littermeup;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.List;

public class User {
    private List<Integer> friendId;
    private String email, password, username;
    private int id, points;
    private double balance;
    private Image profilePicture;
    static int counter = 1;
    private String uid;



    public User() {
    }

    public User(String email, String username, String uid) {
        this.uid = uid;
        this.points = 0;
        this.balance = 0;
        this.id = counter;
        this.email = email;
        this.username = username;
        counter++;
    }




    @Override
    public String toString() {
        return "User{" +
                "friendId=" + friendId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", id=" + id +
                ", points=" + points +
                ", balance=" + balance +
                ", profilePicture=" + profilePicture +
                ", uid='" + uid + '\'' +
                '}';
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUid() {
        return uid;
    }

    public User getUser(){
        return this;
    }
}