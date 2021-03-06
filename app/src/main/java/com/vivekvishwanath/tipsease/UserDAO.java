package com.vivekvishwanath.tipsease;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDAO {
    private static final String BASE_URL = "https://buildtipease.herokuapp.com";
    private static final String GET_ALL_EMPLOYEES_URL = "/serviceWorkers";
    private static final String REGISTER_CUSTOMER_URL = "/auth/users/register";
    private static final String REGISTER_EMPLOYEE_URL = "/auth/serviceWorkers/register";
    private static final String LOGIN_URL = "/auth/users/login";
    private static final String GET_SPECIFIC_CUSTOMER_URL = "/users/" + "%d";
    private static final String GET_SPECIFIC_EMPLOYEE_URL = "/serviceWorkers/" + "%d";
    private static final String UPLOAD_IMAGE_URL = "https://api.imgbb.com/1/upload?key=";
    private static final String TIPPING_URL = "/serviceWorkers/pay/" + "%d";
    private static final String GET_EMPLOYEE_TIPS_URL = "/tickets/tipHistory/" + "%d";
    private static final String RATE_EMPLOYEE_URL = "/serviceWorkers/rate/" + "%d";
    private static final String BANK_TRANSFER_URL = "/serviceWorkers/transferToBank/" + "%d";


    private static HashMap<String, String> headerProperties;

    public static JSONObject authenticateLogin(String username, String password, String type) {
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

        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        ArrayList<String> results = NetworkAdapter.httpRequest(url, NetworkAdapter.POST, loginJSON, headerProperties);
        String result = results.get(0);
            try {
            JSONObject resultJSON = new JSONObject(result);
            return resultJSON;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String registerEmployee(Employee employee) {
        JSONObject newEmployeeJSON = new JSONObject();
        try {
            newEmployeeJSON.put("username", employee.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            newEmployeeJSON.put("fullName", employee.getFirstName() + " " + employee.getLastName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            newEmployeeJSON.put("password", employee.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            newEmployeeJSON.put("serviceType", employee.getServiceType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        ArrayList<String> results = NetworkAdapter.httpRequest(BASE_URL + REGISTER_EMPLOYEE_URL, NetworkAdapter.POST,
                newEmployeeJSON, headerProperties);
        return results.get(1);
    }

    public static String registerCustomer(Customer customer) {
        JSONObject newCustomerJSON = new JSONObject();
        try {
            newCustomerJSON.put("username", customer.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            newCustomerJSON.put("fullName", customer.getFirstName() + " " + customer.getLastName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            newCustomerJSON.put("password", customer.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        ArrayList<String> results = NetworkAdapter.httpRequest(BASE_URL + REGISTER_CUSTOMER_URL,
                NetworkAdapter.POST, newCustomerJSON, headerProperties);
        return results.get(1);
    }

    public static ArrayList<Employee> getAllEmployees(String token) {
        ArrayList<Employee> employees = new ArrayList<>();
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", token);
        try {
            ArrayList<String> results = NetworkAdapter.httpRequest(BASE_URL + GET_ALL_EMPLOYEES_URL,
                    NetworkAdapter.GET, null, headerProperties);
            JSONArray employeesJSONArray = new JSONArray(results.get(0));
            for (int i = 0; i < employeesJSONArray.length(); i++) {
                JSONObject employeeJSON = employeesJSONArray.getJSONObject(i);
                employees.add(getEmployeeFromJson(employeeJSON));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static Employee getSpecificEmployee(int id, String token) {
        String url = BASE_URL + String.format(GET_SPECIFIC_EMPLOYEE_URL, id);
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", token);

        try {
            ArrayList<String> results =  NetworkAdapter.httpRequest(url, NetworkAdapter.GET, null, headerProperties);
            JSONObject employeeJSON = new JSONObject(results.get(0));
            return getEmployeeFromJson(employeeJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String updateEmployee(Employee employee, int id, String token) {
        String url = BASE_URL + String.format(GET_SPECIFIC_EMPLOYEE_URL, id);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fullName", employee.getFirstName() + " " + employee.getLastName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("photoUrl", employee.getImageUrl());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("serviceType", employee.getServiceType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("timeAtJob", employee.getTimeAtJob());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("tagline", employee.getTagline());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("bio", employee.getBio());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("workplace", employee.getWorkplace());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", token);

        ArrayList<String> results = NetworkAdapter.httpRequest(url, NetworkAdapter.PUT, jsonObject, headerProperties);
        return results.get(1);
    }

    public static Employee getEmployeeFromJson(JSONObject jsonObject) {
        Employee employee = new Employee();
        try {
            String[] fullName = jsonObject.getString("fullName").split(" ");
            String firstName = fullName[0];
            employee.setFirstName(firstName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String[] fullName = jsonObject.getString("fullName").split(" ");
            if (fullName.length > 1) {
                String lastName = fullName[1];
                employee.setLastName(lastName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setId(jsonObject.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setUsername(jsonObject.getString("username"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setImageUrl(jsonObject.getString("photoUrl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (!jsonObject.getString("serviceType").equals("null")) {
                employee.setServiceType(jsonObject.getString("serviceType"));
            } else
                employee.setServiceType("No service added");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (!jsonObject.getString("timeAtJob").equals("null")) {
                employee.setTimeAtJob(jsonObject.getString("timeAtJob"));
            } else
                employee.setTimeAtJob("No time added");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (!jsonObject.getString("tagline").equals("null")) {
                employee.setTagline(jsonObject.getString("tagline"));
            } else
                employee.setTagline("No tagline added");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (!jsonObject.getString("bio").equals("null")) {
                employee.setBio(jsonObject.getString("bio"));
            } else
                employee.setBio("No bio added");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (!jsonObject.getString("workplace").equals("null")) {
                employee.setWorkplace(jsonObject.getString("workplace"));
            } else
                employee.setWorkplace("No workplace added");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setAccountBalance(jsonObject.getDouble("accountBalance"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setRating(jsonObject.getDouble("rating"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setNumOfRatings(jsonObject.getInt("numOfRatings"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public static boolean isTokenExpired(String token) {
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", token);
        int responseCode = NetworkAdapter.getResponseCode(BASE_URL + String.format(GET_SPECIFIC_EMPLOYEE_URL, 1),
                NetworkAdapter.GET, null, headerProperties);
        if (responseCode == 200) {
            return false;
        } else {
            return true;
        }
    }

    public static Bitmap getEmployeeImage(String url) {
        Bitmap image = NetworkAdapter.getBitmapFromUrl(url);
        return image;
    }

    public static String uploadImageForUrl(String base64, String pictureInfo) {
        String url = UPLOAD_IMAGE_URL + Constants.IMAGE_HOSTING_KEY;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("image", base64);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> results = NetworkAdapter.httpRequest(url, NetworkAdapter.POST, jsonObject, headerProperties);
        try {
            JSONObject resultJSON = new JSONObject(results.get(0));
            String imageUrl = resultJSON.getJSONObject("data").getJSONObject("image").getString("url");
            return imageUrl;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addTip(double tip, int id, String username, String token) {
        String url = BASE_URL + String.format(TIPPING_URL, id);
        JSONObject paymentJSON = new JSONObject();
        try {
            paymentJSON.put("payment", tip);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            paymentJSON.put("senderUsername", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", token);
        ArrayList<String> results = NetworkAdapter.httpRequest(url, NetworkAdapter.PUT, paymentJSON, headerProperties);
        return results.get(1);
    }

    public static ArrayList<TipObject> getAllEmployeeTips(int id, String token) {
        String url = BASE_URL + String.format(GET_EMPLOYEE_TIPS_URL, id);
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", token);

        ArrayList<String> results = NetworkAdapter.httpRequest(url, NetworkAdapter.GET, null, headerProperties);
        String result = results.get(0);
        ArrayList<TipObject> tipsList = new ArrayList<>();
        try {
            JSONArray tipsJSON = new JSONArray(result);
            for (int i = 0; i < tipsJSON.length(); i++) {
                JSONObject jsonObject = tipsJSON.getJSONObject(i);
                TipObject tip = new TipObject();
                tip.setDateReceived(jsonObject.getString("dateRecieved"));
                tip.setSenderName(jsonObject.getString("senderUsername"));
                tip.setTipAmount(jsonObject.getDouble("tipAmount"));
                tipsList.add(tip);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tipsList;
    }

    public static String rateEmployee(int id, String token, float rating) {
        String url = BASE_URL + String.format(RATE_EMPLOYEE_URL, id);
        JSONObject ratingJSON = new JSONObject();
        try {
            ratingJSON.put("rating", rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", token);

        ArrayList<String> results = NetworkAdapter.httpRequest(url, NetworkAdapter.PUT, ratingJSON, headerProperties);
        return results.get(1);
    }

    public static String transferToBank(int id, String token) {
        String url = BASE_URL + String.format(BANK_TRANSFER_URL, id);
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", token);
        ArrayList<String> results =  NetworkAdapter.httpRequest(url, NetworkAdapter.PUT, null, headerProperties);
        return results.get(1);
    }

    public static double getEmployeeBalance(int id, String token) {
        String url = BASE_URL + String.format(GET_SPECIFIC_EMPLOYEE_URL, id);
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", token);
        ArrayList<String> results = NetworkAdapter.httpRequest(url, NetworkAdapter.GET, null, headerProperties);
        String result = results.get(0);
        try {
            JSONObject jsonObject = new JSONObject(result);
            double accountBalance = jsonObject.getDouble("accountBalance");
            return accountBalance;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
