package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TippingHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TippingListAdapter tippingListAdapter;
    private Button employeeHomeButton;
    private Context context;

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
                tips = UserDAO.getAllEmployeeTips(LoginActivity.prefs.getInt(Constants.ID_KEY, 0),
                        LoginActivity.prefs.getString(Constants.TOKEN_KEY, null));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tippingListAdapter = new TippingListAdapter(tips);
                        recyclerView.setAdapter(tippingListAdapter);

                        tippingListAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }
}
