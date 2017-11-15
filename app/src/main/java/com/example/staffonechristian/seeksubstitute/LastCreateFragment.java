package com.example.staffonechristian.seeksubstitute;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LastCreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LastCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LastCreateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    TextView dateText,timeText,schoolText,subjectText,countryText;
    ImageView coverPic;
    public LastCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LastCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LastCreateFragment newInstance() {
        LastCreateFragment fragment = new LastCreateFragment();
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
        View view;
        view = inflater.inflate(R.layout.fragment_last_create, container, false);
        dateText = view.findViewById(R.id.dateText);
        timeText = view.findViewById(R.id.timeText);
        countryText = view.findViewById(R.id.countryName);
        subjectText = view.findViewById(R.id.subjectName);
        schoolText = view.findViewById(R.id.schoolName);
        coverPic = view.findViewById(R.id.coverPic);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(getContext()).load(R.drawable.cover_pic_1).centerCrop().into(coverPic);
        dateText.setText(CreateSchedule.scheduleData.getDate());
        timeText.setText(CreateSchedule.scheduleData.getTime());
        countryText.setText(CreateSchedule.scheduleData.getCountry());
        subjectText.setText(CreateSchedule.scheduleData.getSubject());
        schoolText.setText(CreateSchedule.scheduleData.getSchoolName());
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
