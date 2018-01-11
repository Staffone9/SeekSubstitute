package com.example.staffonechristian.seeksubstitute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button login,register;
    EditText email,password;
    FirebaseAuth firebaseAuth;
    String emailS;
    String passS;
    String temp;
    String pas;
    LinearLayout progressLinear,wholeLayout;
    public static Activity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.loginEmailButton);
        register = findViewById(R.id.registerEmailButton);
        email = findViewById(R.id.emailL);
        password = findViewById(R.id.passL);
        progressLinear = findViewById(R.id.progressLinear);
        wholeLayout = findViewById(R.id.wholeLayout);
        Bundle bundle = getIntent().getExtras();
        firebaseAuth = FirebaseAuth.getInstance();
        loginActivity = this;
//        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(intent);

        if(MainActivity.mainActivity!=null)
        {
            MainActivity.mainActivity.finish();
        }
        if(RegistrationActivity.registration!=null)
        {
            RegistrationActivity.registration.finish();
        }
        if(bundle!=null)
        {
            emailS = bundle.getString("email");
            passS = bundle.getString("pass");
            if(emailS!=null && passS!=null)
            {
                email.setText(emailS);
                password.setText(passS);
            }
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailS = email.getText().toString().trim();
                passS = password.getText().toString().trim();

                if(!emailS.equals("") && !passS.equals("")) {
                    wholeLayout.setVisibility(View.GONE);
                    progressLinear.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(emailS, passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                CheckExist(emailS);
                                startActivity(intent);

                            } else {
                                wholeLayout.setVisibility(View.VISIBLE);
                                progressLinear.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }else{
                    wholeLayout.setVisibility(View.VISIBLE);
                    progressLinear.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please enter some value", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    private void CheckExist(final String emailS) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                {
                    DatabaseReference writeDb = FirebaseDatabase.getInstance().getReference();
                    writeDb.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(emailS);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
