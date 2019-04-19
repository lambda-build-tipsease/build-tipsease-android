package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmployeeMainActivity extends AppCompatActivity {

    private TextView employeeMainPageName;
    private Button editEmployeeDetailsButton;
    private Button viewTipHistoryButton;
    private Button employeeLogoutButton;
    private Context context;

    String token;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);
        context = this;

        Bundle extras = getIntent().getExtras();
        token = extras.getString(Constants.TOKEN_KEY);
        id = extras.getInt(Constants.ID_KEY);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Employee employee = UserDAO.getSpecificEmployee(id, token);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        employeeMainPageName = findViewById(R.id.employee_main_page_name);
                        employeeMainPageName.setText(employee.getFirstName() + " " + employee.getLastName());
                    }
                });
            }
        }).start();

        editEmployeeDetailsButton = findViewById(R.id.edit_employee_details_button);
        editEmployeeDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateEmployeeDetailsFragment frag = UpdateEmployeeDetailsFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TOKEN_KEY, token);
                bundle.putInt(Constants.ID_KEY, id);
                frag.setArguments(bundle);
                frag.show(fragmentManager, Constants.UPDATE_EMPLOYEE_FRAGMENT_TAG);
            }
        });
        viewTipHistoryButton = findViewById(R.id.view_tip_history_button);
        viewTipHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TippingHistory.class);
                startActivity(intent);
            }
        });

        employeeLogoutButton = findViewById(R.id.employee_logout_button);
        employeeLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = LoginActivity.prefs.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }
}
