package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.ArrayList;

public class CustomerMainActivity extends AppCompatActivity {

    private Context context;
    private EditText searchEmployeeEditText;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EmployeeListAdapter employeeListAdapter;

    private ArrayList<Employee> matchedEmployees = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);


    }
}
