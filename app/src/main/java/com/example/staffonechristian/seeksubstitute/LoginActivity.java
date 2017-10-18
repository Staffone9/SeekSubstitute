package com.example.staffonechristian.seeksubstitute;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.loginEmailButton);
        register = (Button) findViewById(R.id.registerEmailButton);
        email = (EditText) findViewById(R.id.emailL);
        password = (EditText) findViewById(R.id.passL);
        Bundle bundle = getIntent().getExtras();
        firebaseAuth = FirebaseAuth.getInstance();
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
                    firebaseAuth.signInWithEmailAndPassword(emailS, passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                CheckExist(emailS);
                                startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }else{
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
