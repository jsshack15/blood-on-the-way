package com.ansh270292.hackathonbloodontheway.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ansh270292.hackathonbloodontheway.R;
import com.ansh270292.hackathonbloodontheway.adapter.CustomList;
import com.ansh270292.hackathonbloodontheway.model.JSONParser;
import com.ansh270292.hackathonbloodontheway.model.RegisterUserClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ansh on 11/21/2015.
 */
public class NeedADonor extends Fragment {

    public static final String JSON_URL = "http://ldentalpolyclinic.com/test/php/fetch.php";
    private ListView listView,lv;

    public NeedADonor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    String item;
    Button getdonors;
    EditText editText;
    LinearLayout tohide,toshow;
    String requested_pin;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_need_a_donor, container, false);

        getdonors = (Button)rootView.findViewById(R.id.btnget);
        editText = (EditText)rootView.findViewById(R.id.pincode);

        listView = (ListView)rootView.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Toast.makeText(getActivity(),"YOU",Toast.LENGTH_LONG).show();

                String smobile = ((TextView)view.findViewById(R.id.donormobile)).getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+smobile));
                startActivity(callIntent);

            }
        });
        tv = (TextView)rootView.findViewById(R.id.fechingresult);

        tohide = (LinearLayout)rootView.findViewById(R.id.hide_when_list);
        toshow = (LinearLayout)rootView.findViewById(R.id.toshow);

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

        getdonors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requested_pin = editText.getText().toString();
                if(item.length()>6){
                    Toast.makeText(getActivity(),"Choose A valid Blood Group",Toast.LENGTH_LONG).show();

                }
                if(requested_pin.length() == 0 || requested_pin.length()>6){
                    Toast.makeText(getActivity(),"Wrong Pin..Enter A Valid Pin",Toast.LENGTH_LONG).show();
                    editText.setText("");
                }else{
                    tohide.setVisibility(View.GONE);
                    toshow.setVisibility(View.VISIBLE);
                    fetchResult();
                }
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

public void fetchResult() {



    JSONObject params = new JSONObject();
    try {
        params.put("zip",requested_pin);
        params.put("bloodgrp",item);
    } catch (JSONException e) {
        e.printStackTrace();
    }

    JsonObjectRequest req = new JsonObjectRequest(JSON_URL,params,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        VolleyLog.v("Response:%n %s", response.toString(4));
                        Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
                        showJSON(response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.e("Error: ", error.getMessage());
        }
    });

    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
    requestQueue.add(req);

}

    private void showJSON(String json){
        JSONParser pj = new JSONParser(json);
        pj.JSONParser();
        CustomList cl = new CustomList(getActivity(), JSONParser.mobile,JSONParser.names,JSONParser.address,JSONParser.ages);
        listView.setAdapter(cl);

        tv.setVisibility(View.INVISIBLE);
    }

}
