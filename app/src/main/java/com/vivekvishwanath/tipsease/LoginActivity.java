package com.vivekvishwanath.tipsease;

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

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Employee> employees = UserDAO.getAllEmployees();
                int i = 0;
            }
        }).start();

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginRadioGroup = findViewById(R.id.login_radio_group);
        customerLoginRadioButton = findViewById(R.id.customer_login_radio_button);
        employeeLoginRadioButton = findViewById(R.id.employee_login_radio_button);
        loginButton = findViewById(R.id.login_button);
        forgetLoginInfoView = findViewById(R.id.forget_login_info_view);
        signUpView = findViewById(R.id.sign_up_view);

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
                } else if (loginRadioGroup.getCheckedRadioButtonId() == R.id.employee_login_radio_button) {
                    type = "serviceWorkers";
                } else {
                    type = "";
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        token = UserDAO.authenticateLogin(username, password, type);
                    }
                }).start();
            }
        });
    }
}
