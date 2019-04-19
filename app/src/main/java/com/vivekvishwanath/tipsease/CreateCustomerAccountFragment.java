package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;


public class CreateCustomerAccountFragment extends DialogFragment {

    private EditText customerFirstName;
    private EditText customerLastName;
    private EditText customerUsername;
    private EditText customerPassword;
    private EditText customerRetypePassword;
    private Button createCustomerAccountButton;

    public CreateCustomerAccountFragment() {
        // Required empty public constructor
    }


    public static CreateCustomerAccountFragment newInstance() {
        CreateCustomerAccountFragment fragment = new CreateCustomerAccountFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_customer_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customerFirstName = view.findViewById(R.id.customer_first_name_edit_text);
        customerLastName = view.findViewById(R.id.customer_last_name_edit_text);
        customerUsername = view.findViewById(R.id.customer_username_edit_text);
        customerPassword = view.findViewById(R.id.customer_password_edit_text);
        customerRetypePassword = view.findViewById(R.id.customer_retype_password_edit_text);
        createCustomerAccountButton = view.findViewById(R.id.customer_create_account_button);

        createCustomerAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Customer customer = new Customer();
                customer.setFirstName(customerFirstName.getText().toString());
                customer.setLastName(customerLastName.getText().toString());
                customer.setUsername(customerUsername.getText().toString());
                if (customerPassword.getText().toString().equals(customerRetypePassword.getText().toString())) {
                    customer.setPassword(customerPassword.getText().toString());
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = UserDAO.registerCustomer(customer);
                        if (result.equals(Constants.RESPONSE_OK) || result.equals(Constants.RESPONSE_CREATED)) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.registration_pass_toast),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.registration_fail_toast),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }).start();
            }
        });

    }
}
