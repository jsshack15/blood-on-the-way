package com.ansh270292.hackathonbloodontheway.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class JSONParser {

    public static final String JSON_ARRAY = "result";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_AGE = "age";


    public static String[] mobile;
    public static String[] names;
    public static String[] address;
    public static String[] ages;

    private JSONArray users = null;

    private String json;

    public JSONParser(String json){
        this.json = json;
    }

    public void JSONParser(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            mobile = new String[users.length()];
            names = new String[users.length()];
            address = new String[users.length()];
            ages = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                mobile[i] = jo.getString(KEY_MOBILE);
                names[i] = jo.getString(KEY_NAME);
                address[i] = jo.getString(KEY_ADDRESS);
                ages[i] = jo.getString(KEY_AGE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}