package com.vivekvishwanath.tipsease;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTipFragment extends DialogFragment {

    private EditText amountPaid;
    private EditText customTip;
    private Button tenPercentButton;
    private Button fifteenPercentButton;
    private Button twentyPercentButton;
    private Button finishTippingButton;

    private Employee employee;

    public AddTipFragment() {
        // Required empty public constructor
    }

    public static AddTipFragment newInstance() {
        AddTipFragment frag = new AddTipFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        employee = (Employee) bundle.getSerializable("employee");
        return inflater.inflate(R.layout.fragment_add_tip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        amountPaid = view.findViewById(R.id.amount_paid);
        customTip = view.findViewById(R.id.custom_tip);
        tenPercentButton = view.findViewById(R.id.ten_percent_button);
        tenPercentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(amountPaid.getText().toString());
                double tip = round(amount *0.10, 2 );
                customTip.setText(Double.toString(tip));
            }
        });
        fifteenPercentButton = view.findViewById(R.id.fifteen_percent_button);
        fifteenPercentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(amountPaid.getText().toString());
                double tip = round(amount * 0.15, 2);
                customTip.setText(Double.toString(tip));
            }
        });
        twentyPercentButton = view.findViewById(R.id.twenty_percent_button);
        twentyPercentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(amountPaid.getText().toString());
                double tip = round(amount * 0.20, 2);
                customTip.setText(Double.toString(tip));
            }
        });
        finishTippingButton = view.findViewById(R.id.finish_tipping_button);
        finishTippingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserDAO.addTip(Double.parseDouble(customTip.getText().toString()), employee.getId());
                    }
                }).start();
            }
        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
