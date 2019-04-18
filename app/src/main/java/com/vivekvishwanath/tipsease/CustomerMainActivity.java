package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerMainActivity extends AppCompatActivity {

    private Context context;
    private EditText searchEmployeeEditText;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EmployeeListAdapter employeeListAdapter;

    private ArrayList<Employee> matchedEmployees = new ArrayList<>();
    public static HashMap<Integer, Bitmap> employeeImages = new HashMap<>();
    private ArrayList<Employee> allEmployees;

    private String token = "";
    private int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        context = this;

        Bundle extras = getIntent().getExtras();
        int i = 0;
        token = extras.getString(Constants.TOKEN_KEY);
        id = extras.getInt(Constants.ID_KEY);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        employeeListAdapter = new EmployeeListAdapter(matchedEmployees);
        recyclerView.setAdapter(employeeListAdapter);
        searchEmployeeEditText = findViewById(R.id.search_employee_edit_text);
        new Thread(new Runnable() {
            @Override
            public void run() {
                allEmployees = UserDAO.getAllEmployees(token);
            }
        }).start();

        searchEmployeeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                matchedEmployees.clear();
                employeeImages.clear();
                employeeListAdapter.notifyDataSetChanged();
                if (!s.toString().equals("")) {
                    for (int i = 0; i < allEmployees.size(); i++) {
                        if (allEmployees.get(i).getFirstName().startsWith(s.toString())) {
                            matchedEmployees.add(allEmployees.get(i));
                            final int employeeId = allEmployees.get(i).getId();
                            final String imageUrl = allEmployees.get(i).getImageUrl();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    employeeImages.put(employeeId, UserDAO.getEmployeeImage(imageUrl));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            employeeListAdapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }).start();
                        }
                    }
                }
            }
        });


    }
}
