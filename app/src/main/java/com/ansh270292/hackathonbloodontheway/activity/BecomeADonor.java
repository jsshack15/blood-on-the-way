package com.ansh270292.hackathonbloodontheway.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.telephony.TelephonyManager;
import android.widget.DatePicker;

import com.ansh270292.hackathonbloodontheway.R;
import com.ansh270292.hackathonbloodontheway.model.RegisterUserClass;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Ansh on 11/21/2015.
 */
public class BecomeADonor extends Fragment {
    public BecomeADonor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    String item;
    TextView dob,dateofbirth;

    Button btnRegister;
    EditText etmobile,etname,etage,etaddress,postalCode;
    String mobile,name,age,address,postal;

    private static final String REGISTER_URL = "http://ldentalpolyclinic.com/test/php/getdonors.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_become_a_donor, container, false);

        dob = (TextView)rootView.findViewById(R.id.tveditdob);
        etmobile = (EditText)rootView.findViewById(R.id.etMob);
        etname = (EditText)rootView.findViewById(R.id.etName);
        etaddress = (EditText)rootView.findViewById(R.id.etAddress);
        postalCode = (EditText)rootView.findViewById(R.id.etpostalcode);

        etage = (EditText)rootView.findViewById(R.id.etage);
        dateofbirth = (TextView)rootView.findViewById(R.id.editdob);
        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // to show date picker
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                dob.setTextColor(ColorStateList.valueOf(R.color.navigationBarColor));
                dateofbirth.setVisibility(View.INVISIBLE);

            }
        });
btnRegister = (Button)rootView.findViewById(R.id.btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile = etmobile.getText().toString();
                name = etname.getText().toString();
                age = etage.getText().toString();
                address = etaddress.getText().toString();
                postal = postalCode.getText().toString();
                if(mobile.equals("") || mobile.length()<10){
                    raisetoast();
                    etmobile.setText("");
                }
                if(name.equals("")){
                    raisetoast();
                }
                if(age.equals("")){
                    raisetoast();
                }
                if(address.equals("")){
                    raisetoast();
                }
                if(postal.equals("") || postal.length()<6){
                    raisetoast();
                    postalCode.setText("");
                }
                registerUser();
            }
        });
        Spinner spinner = (Spinner)rootView.findViewById(R.id.bloodgroupspinner);
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

    public void raisetoast(){
        Toast.makeText(getActivity(),"All Fields Are Required", Toast.LENGTH_SHORT).show();

    }
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {
            dob.setText(month+"/"+day+"/"+year);
        }

    }

    // for registering user

    private void registerUser() {

        register(name,mobile,address,age,dob.getText().toString(),postal,item);
    }

    private void register(String name, String mob,String a, String add, String date_of_birth, String zip, String bldgrp) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading= ProgressDialog.show(getActivity(), "Please Wait","Registering...", true, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("mobile",params[1]);
                data.put("address",params[2]);
                data.put("age",params[3]);
                data.put("dob",params[4]);
                data.put("postal",params[5]);
                data.put("bloodgrp",params[6]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,mob,a,add,date_of_birth,zip,bldgrp);
    }

}
