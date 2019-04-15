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
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private AppCompatCheckBox customerCheckBox;
    private AppCompatCheckBox employeeCheckBox;
    private Button loginButton;
    private TextView forgetLoginInfoView;
    private TextView signUpView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        customerCheckBox = findViewById(R.id.customer_check_box);
        employeeCheckBox = findViewById(R.id.employee_check_box);
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
    }
}
