package be.ehb.trends_littermeup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            return;
        }

        Button btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        TextView toLogin = findViewById(R.id.app_redirect_to_login);
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLoginActivity();
            }
        });
    }


    private void registerUser(){
        EditText txtEmail = findViewById(R.id.txt_email);
        EditText txtPassword = findViewById(R.id.txt_password);
        EditText txtUsername = findViewById(R.id.txt_username);
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        Database database = new Database();


        if(email.isEmpty()){
            Toast.makeText(this, "Email field not filled in", Toast.LENGTH_SHORT).show();
            if(password.isEmpty()){
                Toast.makeText(this, "Password is not filled in", Toast.LENGTH_SHORT).show();
                if(username.isEmpty()){
                    Toast.makeText(this, "Username is not filled in", Toast.LENGTH_SHORT).show();
                }
            }
        }


        if(!(email.isEmpty()||password.isEmpty()|| username.isEmpty())){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                database.getUserCount().observe(RegisterActivity.this, new Observer<Integer>() {
                                    @Override
                                    public void onChanged(Integer integer) {
                                        int tempId = integer.intValue() + 1;
                                        User newUser = new User(email,username,mAuth.getCurrentUser().getUid(),tempId);
                                        database.add(newUser);

                                        db.collection("Achievements").whereEqualTo("title", "Registration complete").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    QuerySnapshot result = task.getResult();
                                                    if (!result.isEmpty()) {
                                                        DocumentSnapshot achievement = result.getDocuments().get(0);
                                                        String achievementId = achievement.getId();
                                                        ArrayList<String> userIds = (ArrayList<String>) achievement.get("userId");

                                                        if(userIds == null){
                                                            userIds = new ArrayList<>();
                                                        }

                                                        userIds.add(mAuth.getCurrentUser().getUid()); // add the user's ID to the userIds list
                                                        db.collection("Achievements").document(achievementId).update("userId", userIds);
                                                        db.collection("Users").document(mAuth.getCurrentUser().getUid()).update("points", newUser.getPoints() + 10);
                                                    }
                                                }
                                            }
                                        });

                                    }
                                });
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                setStartTime();
                                showMainActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // Method to get startTime
    public void setStartTime(){
        Map<String, Object> startData = new HashMap<>();
        startData.put("startTime", FieldValue.serverTimestamp());

        FirebaseFirestore.getInstance()
                .collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update(startData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Start time has been saved
                    }
                });
    }



    private void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}