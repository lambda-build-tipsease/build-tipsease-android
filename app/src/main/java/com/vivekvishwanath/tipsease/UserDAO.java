package com.vivekvishwanath.tipsease;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserDAO {
    private static final String BASE_URL = "https://buildtipease.herokuapp.com";
    private static final String GET_ALL_WORKERS_URL = "/serviceWorkers";
    private static final String REGISTER_CUSTOMER_URL = "/auth/users/register";
    private static final String REGISTER_EMPLOYEE_URL = "/auth/serviceWorkers/register";
    private static final String LOGIN_URL = "/auth/users/login";
    private static final String GET_SPECIFIC_CUSTOMER_URL = "/users/${" + "%d" + "}";
    private static final String GET_SPECIFIC_EMPLOYEE_URL = "/serviceWorkers/${" + "%d" + "}";
    private static final String RATE_WORKER_URL = "/serviceWorkers/rate/${" + "%d" + "}";
    HashMap<String, String> headerProperties = new HashMap<>();

    //   "username": "swUsername1",
    //        "fullName": "Bleh Bluh",
    //        "password": "$2b$08$3DGr0lGSiZMtAtVBlPgg6.rTK/tVjdsGlaI6jDdumGsOB5e./Tpz2",

    public static String authenticateLogin(String username, String password, String type) {
        String url = BASE_URL + LOGIN_URL;
        JSONObject loginJSON = new JSONObject();
        try {
            loginJSON.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            loginJSON.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            loginJSON.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        String result = NetworkAdapter.httpRequest(url, NetworkAdapter.POST, loginJSON, headerProperties);
            try {
            JSONObject resultJSON = new JSONObject(result);
            return resultJSON.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
