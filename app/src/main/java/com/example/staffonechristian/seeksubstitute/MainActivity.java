package com.example.staffonechristian.seeksubstitute;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton floatingActionButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  createLecture();
        Splash.splashActivity.finish();
        FirebaseMessaging.getInstance().subscribeToTopic("common");

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        Fragment fragment;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CreateSchedule.class);
                startActivity(intent);

            }
        });


        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>"+"Your schedule"+"</font>"));




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {

                    case R.id.action_feed:{
                        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>"+"Your schedule"+"</font>"));
                        TransactionOfFragment(FragmentSchedule.newInstance());
                        break;
                    }
                    case R.id.action_alert:{
                        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>"+"Available for substitute"+"</font>"));
                        TransactionOfFragment(NotGoingFragment.newInstance());
                        break;
                    }
                    case R.id.action_profile:{

                        break;
                    }


                }


                return true;


            }
        });
        fragment = new FragmentSchedule();
        TransactionOfFragment(fragment);


    }
    public void TransactionOfFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);

        transaction.commit();
    }

    private void createLecture() {
        ScheduleData scheduleData = new ScheduleData();
        scheduleData.setCountry("Australia");
        scheduleData.setDate("July 10,2018");
        scheduleData.setTime("11 : 30 AM");
        scheduleData.setSchoolName("University of Melbourne");
        scheduleData.setSirId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        scheduleData.setSubject("Mobile Application Development");
        scheduleData.setSirName("James Austin");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference scheduleDb = databaseReference.child("seeksubstitute").child("schedule").child(scheduleData.getCountry())
                .child(scheduleData.getSchoolName()).child(scheduleData.getSubject()).push();
        scheduleData.setLectureId(scheduleDb.getKey());
        scheduleDb.setValue(scheduleData);


        CountrySchoolSubjectData countrySchoolSubjectData = new CountrySchoolSubjectData();
        countrySchoolSubjectData.setSubject(scheduleData.getSubject());
        countrySchoolSubjectData.setCountry(scheduleData.getCountry());
        countrySchoolSubjectData.setSchool(scheduleData.getSchoolName());


        DatabaseReference writeDb = databaseReference.child("seeksubstitute").child("ScheduleDetail").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push();
        writeDb.setValue(countrySchoolSubjectData);
    }
}


