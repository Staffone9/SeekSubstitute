package com.example.staffonechristian.seeksubstitute;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstCreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstCreateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AutoCompleteTextView countryAutoCompleteTextView,schoolAutoCompleteTextView,subjectAutoCompleteTextView;

    private OnFragmentInteractionListener mListener;
    String[] country = {"Australia", "Canada", "America", "New Zealand", "Poland", "Russia", "South Africa", "Switzerland","China","Brazil"};
    String[] school = {"University of Melbourne", "Conestoga", "Auckland University", "Humber", "George Brown"};
    String[] subject = {"Mobile Security", "Yoga", "Mobile Application Development", "Can fit pro", "Art", "Business Management", "Product Development", "CPU"};
    public FirstCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FirstCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstCreateFragment newInstance() {
        FirstCreateFragment fragment = new FirstCreateFragment();
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
        View view = inflater.inflate(R.layout.fragment_first_create, container, false);
        countryAutoCompleteTextView = view.findViewById(R.id.countryAutoCompleteTextView);
        subjectAutoCompleteTextView = view.findViewById(R.id.subjectAutoCompleteTextView);
        schoolAutoCompleteTextView = view.findViewById(R.id.schoolAutoCompleteTextView);

        ArrayAdapter<String> countryAdaptor = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, country);
        ArrayAdapter<String> subjectAdaptor = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, subject);
        ArrayAdapter<String> schoolAdaptor = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, school);
        //Getting the instance of AutoCompleteTextView
        countryAutoCompleteTextView.setThreshold(1);//will start working from first character
        countryAutoCompleteTextView.setAdapter(countryAdaptor);//setting the adapter data into the AutoCompleteTextView
        subjectAutoCompleteTextView.setThreshold(1);//will start working from first character
        subjectAutoCompleteTextView.setAdapter(subjectAdaptor);
        schoolAutoCompleteTextView.setThreshold(1);//will start working from first character
        schoolAutoCompleteTextView.setAdapter(schoolAdaptor);
        CreateSchedule.createButton.setVisibility(View.GONE);
        CreateSchedule.nextScreen.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CreateSchedule.nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(subjectAutoCompleteTextView.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getContext(),"Please Enter Subject before moving on.",Toast.LENGTH_LONG).show();
                }else if(schoolAutoCompleteTextView.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(),"Please Enter School before moving on.",Toast.LENGTH_LONG).show();
                }else if(countryAutoCompleteTextView.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(),"Please Enter Country before moving on.",Toast.LENGTH_LONG).show();
                }else {
                    CreateSchedule.scheduleData.setCountry(countryAutoCompleteTextView.getText().toString());
                    CreateSchedule.scheduleData.setSchoolName(schoolAutoCompleteTextView.getText().toString());
                    CreateSchedule.scheduleData.setSubject(subjectAutoCompleteTextView.getText().toString());
                    Fragment fragment = SecondCreateFragment.newInstance();
                    FragmentTransaction(fragment);
                }

            }
        });

    }
    public void FragmentTransaction(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(fragment.getClass().toString());
        transaction.commit();
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
