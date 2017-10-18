package com.example.staffonechristian.seeksubstitute;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSchedule extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private android.support.v7.widget.RecyclerView scheduleRecyclerView;
    ArrayList<ScheduleData> scheduleDatas;
    ArrayList<CountrySchoolSubjectData> countrySchoolSubjectDatas;
    ScheduleAdaptor scheduleAdaptor;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public FragmentSchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragmentSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSchedule newInstance() {
        FragmentSchedule fragment = new FragmentSchedule();
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
        View view =  inflater.inflate(R.layout.fragment_fragment_schedule, container, false);
        // Inflate the layout for this fragment


        scheduleDatas = new ArrayList<>();
        countrySchoolSubjectDatas = new ArrayList<>();
        scheduleRecyclerView = view.findViewById(R.id.scheduleRecycler);
        scheduleAdaptor = new ScheduleAdaptor(scheduleDatas,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        scheduleRecyclerView.setLayoutManager(linearLayoutManager);
        scheduleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scheduleRecyclerView.setAdapter(scheduleAdaptor);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PrepareSchedule();
    }

    private void PrepareSchedule() {




        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference readScheduleDetail = databaseReference.child("seeksubstitute").child("ScheduleDetail").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        readScheduleDetail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot individual:dataSnapshot.getChildren()) {
                    countrySchoolSubjectDatas.add(individual.getValue(CountrySchoolSubjectData.class));

                }
                finalRead();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void finalRead() {

        for (CountrySchoolSubjectData singObject:countrySchoolSubjectDatas
             ) {
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference scheduleDb = databaseReference.child("seeksubstitute").child("schedule").child(singObject.getCountry())
                    .child(singObject.getSchool()).child(singObject.getSubject());
            scheduleDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot oneSnap:dataSnapshot.getChildren()) {
                        scheduleDatas.add(oneSnap.getValue(ScheduleData.class));
                        scheduleAdaptor.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

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