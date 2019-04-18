package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TippingHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TippingListAdapter tippingListAdapter;
    private Button transferButton;
    private TextView accountBalanceTextView;
    private Button employeeHomeButton;
    private Context context;
    private Employee employee;
    private ArrayList<TipObject> tips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipping_history);
        context = this;

        recyclerView = findViewById(R.id.tips_recycler_view);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        accountBalanceTextView = findViewById(R.id.account_balance_view);
        transferButton = findViewById(R.id.transfer_button);

        employeeHomeButton = findViewById(R.id.employee_home_button);
        employeeHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

         new Thread(new Runnable() {
            @Override
            public void run() {
                tips.clear();
                employee = UserDAO.getSpecificEmployee(LoginActivity.prefs.getInt(Constants.ID_KEY, 0),
                        LoginActivity.prefs.getString(Constants.TOKEN_KEY, null ));
                tips = UserDAO.getAllEmployeeTips(LoginActivity.prefs.getInt(Constants.ID_KEY, 0),
                        LoginActivity.prefs.getString(Constants.TOKEN_KEY, null));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        accountBalanceTextView.setText("Account Balance: $" + Double.toString(employee.getAccountBalance()));
                        tippingListAdapter = new TippingListAdapter(tips);
                        recyclerView.setAdapter(tippingListAdapter);
                        tippingListAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

         transferButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         String result = UserDAO.transferToBank(employee.getId(), LoginActivity.prefs.getString(Constants.TOKEN_KEY, null));
                         final double accountBalance = UserDAO.getEmployeeBalance(employee.getId(),
                                 LoginActivity.prefs.getString(Constants.TOKEN_KEY, null));
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 accountBalanceTextView.setText(Double.toString(accountBalance));
                             }
                         }); 
                     }
                 }).start();
             }
         });


    }

}
