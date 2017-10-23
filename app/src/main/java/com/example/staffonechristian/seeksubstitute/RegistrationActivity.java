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

public class RegistrationActivity extends AppCompatActivity {

    Button submit;
    EditText name,email,password,confirmPassword,subject,country,qualification;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        submit = (Button) findViewById(R.id.submitEmail);
        name=(EditText)findViewById(R.id.nameR);
        email = (EditText) findViewById(R.id.emailR);
        password = (EditText) findViewById(R.id.passwordR);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordR);
        subject=(EditText)findViewById(R.id.subjectR);
        country=(EditText)findViewById(R.id.countryR);
        qualification=(EditText)findViewById(R.id.qualificationR);
        firebaseAuth = FirebaseAuth.getInstance();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nameS,emailS,passS,confirmPassS,subjectS,countryS,qualificationS;
                nameS=name.getText().toString().trim();
                emailS = email.getText().toString().trim();
                passS = password.getText().toString().trim();
                confirmPassS = confirmPassword.getText().toString().trim();
                subjectS=subject.getText().toString().trim();
                countryS=country.getText().toString().trim();
                qualificationS=qualification.getText().toString().trim();
                if(confirmPassS.equals(passS))
                {
                    if (email != null && passS != null && confirmPassS != null ) {
                        firebaseAuth.createUserWithEmailAndPassword(emailS, passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("email",emailS);
                                    bundle.putString("pass",passS);
                                  //  bundle.putString("name",nameS);
                                   // bundle.putString("country",countryS);
                                    //bundle.putString("sub",subjectS);
                                    //bundle.putString("quali",qualificationS);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Try again Please", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }else{
                        Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter same passowrd in both field", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
