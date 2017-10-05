package com.example.staffonechristian.seeksubstitute;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
                firebaseAuth.signInWithEmailAndPassword(emailS,passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);

                        }else {

                            Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });


    }
}
