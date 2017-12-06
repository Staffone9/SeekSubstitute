package com.example.staffonechristian.seeksubstitute;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView coverPic;
    EditText name,email,subject,bio;
    Button updateProfile;
    LinearLayout progressLinear,linearLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar progressBar;
    TextView progressBarTitle;
    Button logOut;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        coverPic = view.findViewById(R.id.coverPic);
        name = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.emailId);
        subject = view.findViewById(R.id.subjectName);
        bio = view.findViewById(R.id.bio);
        updateProfile = view.findViewById(R.id.updateProfile);
        progressLinear = view.findViewById(R.id.progressLinear);
        linearLayout = view.findViewById(R.id.linear1);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBarTitle = view.findViewById(R.id.progressBarTitle);
        logOut = view.findViewById(R.id.logoutProfile);
        return view;
    }

    public void Invisible(){
        linearLayout.setVisibility(View.GONE);
        progressLinear.setVisibility(View.VISIBLE);
    }

    public void Visible(){
        progressLinear.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBarTitle.setText("Getting your profile");
        Invisible();
        GetData();
        Glide.with(getContext()).load(R.drawable.coverpicprofile2).centerCrop().into(coverPic);


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),Splash.class);
                startActivity(intent);


            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getContext(),"Please Enter Name before updating profile", Toast.LENGTH_LONG).show();
                }else if(email.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getContext(),"Please Enter Email before updating profile", Toast.LENGTH_LONG).show();

                }else if(bio.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getContext(),"Please Enter Bio before updating profile", Toast.LENGTH_LONG).show();
                }
                else if(subject.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getContext(),"Please Enter Subject before updating profile", Toast.LENGTH_LONG).show();
                }else{
                    progressBarTitle.setText("Updating your profile");
                    Invisible();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    UserData userData = new UserData();
                    userData.setBio(bio.getText().toString().trim());
                    userData.setEmailId(email.getText().toString().trim());
                    userData.setFirstAndLastName(name.getText().toString().trim());
                    userData.setSubject(subject.getText().toString().trim());
                    databaseReference.child("UsersData").child(FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getUid())
                            .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(),"Profile updated successfully", Toast.LENGTH_LONG).show();
                            GetData();
                        }
                    });


                }


            }
        });
    }

    private void GetData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("UsersData").child(FirebaseAuth.getInstance()
                .getCurrentUser()
                .getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    UserData userData = dataSnapshot.getValue(UserData.class);
                    if(userData!=null) {
                        bio.setText(userData.getBio());
                        subject.setText(userData.getSubject());
                        name.setText(userData.getFirstAndLastName());
                        email.setText(userData.getEmailId());
                       Visible();
                    }
                }else{
                    Visible();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
