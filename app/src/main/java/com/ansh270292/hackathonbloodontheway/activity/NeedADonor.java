package com.ansh270292.hackathonbloodontheway.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ansh270292.hackathonbloodontheway.R;

/**
 * Created by Ansh on 11/21/2015.
 */
public class NeedADonor extends Fragment {
    public NeedADonor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    String item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_need_a_donor, container, false);
        Spinner spinner = (Spinner)rootView.findViewById(R.id.bldgrpspinner);
        ArrayAdapter<CharSequence> pageAdapter = new ArrayAdapter<CharSequence>(
                getActivity(), android.R.layout.simple_spinner_item);
        pageAdapter.add("--Select--");
        pageAdapter.add("O+ve");
        pageAdapter.add("O-ve");
        pageAdapter.add("AB+ve");
        pageAdapter.add("B+ve");
        pageAdapter.add("B-ve");
        pageAdapter.add("A+ve");
        pageAdapter.add("A-ve");
        pageAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(pageAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getActivity(), "Select Required Blood Group", Toast.LENGTH_SHORT);
            }



        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
