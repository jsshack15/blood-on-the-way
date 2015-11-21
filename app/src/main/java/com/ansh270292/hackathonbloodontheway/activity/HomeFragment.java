package com.ansh270292.hackathonbloodontheway.activity;


import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ansh270292.hackathonbloodontheway.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ansh on 11/21/2015.
 */
public class HomeFragment extends Fragment {
    double lat,lon;
    GPSTracker gps;
    String lo,postalCode,rest;

    TextView user_location;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        user_location = (TextView)rootView.findViewById(R.id.yourlocation);

        lat=0;
        getLocation();
       if(lat==0){
            getLocation();
        }

       String address="",locality="",postalcode="";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocation(lat,
                    lon, 1);
            Log.e("Addresses", "-->" + addresses);
            if(addresses.size()>0) {
              address = addresses.get(0).getAddressLine(0).toString();
                locality = addresses.get(0).getLocality().toString();
               // postalcode = addresses.get(0).getPostalCode().toString();
            }else{

            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

            user_location.setText(address+"\n"+locality+"\n"+postalcode);


        return rootView;
    }
    public void getLocation() {
        gps = new GPSTracker(getActivity());
        if(gps.canGetLocation()) {
            lat = gps.getLatitude();
            lon = gps.getLongitude();
        }else{
            gps.showSettingsAlert();

        }


    }

    class CityAsyncTask extends AsyncTask<String, String, String> {
        Activity act;
        double latitude;
        double longitude;

        public CityAsyncTask(Activity act, double latitude, double longitude) {
            // TODO Auto-generated constructor stub
            this.act = act;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String postalCode ="";
            StringBuilder add = new StringBuilder();
            Geocoder geocoder = new Geocoder(act, Locale.getDefault());
            try {
                add=null;
                List<Address> addresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                Log.e("Addresses", "-->" + addresses);
                if(addresses.size()>0) {
                    add.append(addresses.get(0).getLocality().toString());
                    add.append(":");
                    add.append(addresses.get(0).getPostalCode().toString());

                   /* result+="\n";
                    result+=addresses.get(0).getPostalCode().toString();*/
                    /*result.concat(":");

                    postalCode = addresses.get(0).getPostalCode().toString();
                    result.concat(postalCode);*/
                }else{

                }
            } catch (IOException e) {
                e.printStackTrace();
            }catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
            result = add.toString();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

        }

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