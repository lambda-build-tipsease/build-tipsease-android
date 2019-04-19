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


public class CreateEmployeeAccountFragment extends DialogFragment {

    private EditText employeeFirstName;
    private EditText employeeLastName;
    private EditText employeeOccupation;
    private EditText employeeUsername;
    private EditText employeePassword;
    private EditText employeeRetypePassword;
    private Button createEmployeeAccountButton;

    public CreateEmployeeAccountFragment() {
        // Required empty public constructor
    }

    public static CreateEmployeeAccountFragment newInstance() {
        return new CreateEmployeeAccountFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_employee_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        employeeFirstName = view.findViewById(R.id.employee_first_name_edit_text);
        employeeLastName = view.findViewById(R.id.employee_last_name_edit_text);
        employeeOccupation = view.findViewById(R.id.employee_occupation_edit_text);
        employeeUsername = view.findViewById(R.id.employee_username_edit_text);
        employeePassword = view.findViewById(R.id.employee_password_edit_text);
        employeeRetypePassword = view.findViewById(R.id.employee_retype_password_edit_text);
        createEmployeeAccountButton = view.findViewById(R.id.employee_create_account_button);

        createEmployeeAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Employee employee = new Employee();
                employee.setFirstName(employeeFirstName.getText().toString());
                employee.setLastName(employeeLastName.getText().toString());
                employee.setServiceType(employeeOccupation.getText().toString());
                employee.setUsername(employeeUsername.getText().toString());
                if (employeePassword.getText().toString().equals(employeeRetypePassword.getText().toString())) {
                    employee.setPassword(employeePassword.getText().toString());
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = UserDAO.registerEmployee(employee);
                        if (result.equals(Constants.RESPONSE_OK) || result.equals(Constants.RESPONSE_CREATED)) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), R.string.registration_pass_toast,
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), R.string.registration_fail_toast,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }).start();
            }
        });
    }

}

