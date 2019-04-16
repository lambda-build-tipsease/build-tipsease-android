package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private RadioGroup loginRadioGroup;
    private RadioButton customerLoginRadioButton;
    private RadioButton employeeLoginRadioButton;
    private Button loginButton;
    private TextView forgetLoginInfoView;
    private TextView signUpView;
    private Context context;
    private static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        prefs = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginRadioGroup = findViewById(R.id.login_radio_group);
        customerLoginRadioButton = findViewById(R.id.customer_login_radio_button);
        employeeLoginRadioButton = findViewById(R.id.employee_login_radio_button);
        loginButton = findViewById(R.id.login_button);
        forgetLoginInfoView = findViewById(R.id.forget_login_info_view);
        signUpView = findViewById(R.id.sign_up_view);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isTokenExpired = UserDAO.isTokenExpired(prefs.getString("token", null));
                if (!isTokenExpired) {
                    if (prefs.getString("type", null).equals("users")) {
                        final Intent intent = new Intent(context, CustomerMainActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("token", prefs.getString("token", null));
                        extras.putString("id", prefs.getString("id", null));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        }).start();
        final FragmentManager fragmentManager = getSupportFragmentManager();

        signUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment prev = fragmentManager.findFragmentByTag(Constants.SIGN_UP_CHOICE_FRAGMENT_TAG);
                if (prev != null)
                    fragmentTransaction.remove(prev);
                fragmentTransaction.addToBackStack(null);
                SignUpChoiceFragment fragment = SignUpChoiceFragment.newInstance();
                fragment.show(fragmentTransaction, Constants.SIGN_UP_CHOICE_FRAGMENT_TAG);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final String type;
                if (loginRadioGroup.getCheckedRadioButtonId() == R.id.customer_login_radio_button) {
                    type = "users";
                    authenticateLoginAttempt(username, password, type);
                } else if (loginRadioGroup.getCheckedRadioButtonId() == R.id.employee_login_radio_button) {
                    type = "serviceWorkers";
                    authenticateLoginAttempt(username, password, type);
                } else {
                    type = "";
                }
            }
        });
    }

    public void authenticateLoginAttempt(final String username, final String password, final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject loginJSON = UserDAO.authenticateLogin(username, password, type);
                if (loginJSON != null) {
                    try {
                        String token = loginJSON.getString("token");
                        int id = loginJSON.getJSONObject("userInfo").getInt("id");
                        boolean isTokenExpired = UserDAO.isTokenExpired(token);
                        if (!isTokenExpired) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("token", token);
                            editor.putInt("id", id);
                            editor.putString("type", type);
                            editor.commit();
                            final Intent intent = new Intent(context, CustomerMainActivity.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(intent);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
