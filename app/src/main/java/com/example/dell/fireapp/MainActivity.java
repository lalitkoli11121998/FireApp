package com.example.dell.fireapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    EditText name ,stream ,passwordtext;
    EditText emailtext,year;
    Button signIn ,signUp;
    FirebaseDatabase database;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    String TAG="TAGGER";
    FirebaseUser currentUser;
    Button read;
    FloatingActionButton floatingActionButton;
    private FirebaseAuth mAuth;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.nameeditText);
        stream = findViewById(R.id.steramedittext);
        passwordtext = findViewById(R.id.passwordtext);
        floatingActionButton = findViewById(R.id.floatingActionButton2);
        year = findViewById(R.id.yearedittext);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        read = findViewById(R.id.readbutton);
        emailtext = findViewById(R.id.emailedittext);
        signIn = findViewById(R.id.signin);

        signUp = findViewById(R.id.signup);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUP();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIN();
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Main2Activity.class);
                startActivity(intent);
            }
        });
    }


    private void readData() {
        myRef=database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                Log.d("snapshot" , String.valueOf(dataSnapshot.getValue(UserData.class)));
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()){
                    Log.d("FirebaseKey",childSnapShot.toString());
                    UserData userData=childSnapShot.getValue(UserData.class);
                    Log.d("particularUser",userData.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void signIN() {
        String email = emailtext.getText().toString();
        String password = passwordtext.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null)
                                Toast.makeText(MainActivity.this,user.getUid(), Toast.LENGTH_SHORT).show();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signUP() {
        String email = emailtext.getText().toString();
        String password = passwordtext.getText().toString();
        String username = name.getText().toString();
        String userstream = stream.getText().toString();
        String useryear = year.getText().toString();
        final UserData userData = new UserData(email,userstream,useryear,password,username);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            myRef = database.getReference(user.getUid());

                            myRef.setValue(userData);
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
}
}
