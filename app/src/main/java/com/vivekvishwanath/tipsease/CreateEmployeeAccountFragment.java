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
import android.widget.EditText;


public class CreateEmployeeAccountFragment extends DialogFragment {

    private EditText employeeFirstName;
    private EditText employeeLastName;
    private EditText employeeEmail;
    private EditText employeeUsername;
    private EditText employeePassword;
    private EditText employeeRetypePassword;

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
        employeeEmail = view.findViewById(R.id.employee_email_edit_text);
        employeeUsername = view.findViewById(R.id.employee_username_edit_text);
        employeePassword = view.findViewById(R.id.employee_password_edit_text);
        employeeRetypePassword = view.findViewById(R.id.employee_retype_password_edit_text);
    }

}

