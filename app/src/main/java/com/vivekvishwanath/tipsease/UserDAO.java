package com.vivekvishwanath.tipsease;

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
    private static final String RATE_WORKER_URL = "/serviceWorkers/rate/" + "%d";


    private static HashMap<String, String> headerProperties;

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

        headerProperties = new HashMap<>();
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

    public static void registerEmployee(Employee employee) {
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
        NetworkAdapter.httpRequest(BASE_URL + REGISTER_EMPLOYEE_URL, NetworkAdapter.POST,
                newEmployeeJSON, headerProperties);
    }

    public static void registerCustomer(Customer customer) {
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
        NetworkAdapter.httpRequest(BASE_URL + REGISTER_CUSTOMER_URL, NetworkAdapter.POST, newCustomerJSON, headerProperties);
    }

    public static ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        headerProperties = new HashMap<>();
        headerProperties.put("Content-Type", "application/json");
        headerProperties.put("authorization", Constants.TEMP_TOKEN);
        try {
            JSONArray employeesJSONArray = new JSONArray(NetworkAdapter.httpRequest(BASE_URL + GET_ALL_EMPLOYEES_URL,
                    NetworkAdapter.GET, null, headerProperties));
            for (int i = 0; i < employeesJSONArray.length(); i++) {
                JSONObject employeeJSON = employeesJSONArray.getJSONObject(i);
                employees.add(getEmployeeFromJson(employeeJSON));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return employees;
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
            String lastName = fullName[1];
            employee.setLastName(lastName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setId(jsonObject.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setImageUrl(jsonObject.getString("photoUrl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setServiceType(jsonObject.getString("serviceType"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setTimeAtJob(jsonObject.getString("timeAtJob"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setTagline(jsonObject.getString("tagline"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setBio(jsonObject.getString("bio"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employee.setWorkplace(jsonObject.getString("workplace"));
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
        headerProperties.put("authorization", Constants.TEMP_TOKEN);
        String result = NetworkAdapter.httpRequest(BASE_URL + String.format(GET_SPECIFIC_EMPLOYEE_URL, 1),
                NetworkAdapter.GET, null, headerProperties);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getString("you").equals("session timed out!")) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


}
