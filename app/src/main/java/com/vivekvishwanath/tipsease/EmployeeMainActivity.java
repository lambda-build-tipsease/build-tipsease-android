package com.vivekvishwanath.tipsease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class EmployeeMainActivity extends AppCompatActivity {

    private Button editEmployeeDetailsButton;
    private Button viewTipHistoryButton;
    private Button viewProfileButton;

    String token;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

        Bundle extras = getIntent().getExtras();
        int i = 0;
        token = extras.getString(Constants.TOKEN_KEY);
        id = extras.getInt(Constants.ID_KEY);

        editEmployeeDetailsButton = findViewById(R.id.edit_employee_details_button);
        viewTipHistoryButton = findViewById(R.id.view_tip_history_button);
        viewProfileButton = findViewById(R.id.view_profile_button);

        
    }
}
