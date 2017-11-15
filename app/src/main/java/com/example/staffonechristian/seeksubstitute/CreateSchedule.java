package com.example.staffonechristian.seeksubstitute;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class CreateSchedule extends AppCompatActivity {


    public static FloatingActionButton nextScreen;
    public static ScheduleData scheduleData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);
        nextScreen = (FloatingActionButton) findViewById(R.id.nextScreenFab);
        Fragment fragment = FirstCreateFragment.newInstance();
        FragmentTransaction(fragment);
        scheduleData = new ScheduleData();

    }

    public void FragmentTransaction(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

}
