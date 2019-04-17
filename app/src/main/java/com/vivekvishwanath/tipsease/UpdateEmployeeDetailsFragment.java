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
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateEmployeeDetailsFragment extends DialogFragment {

    private ImageView updateEmployeeImage;
    private EditText updateEmployeeFirstName;
    private EditText updateEmployeeLastName;
    private EditText updateEmployeeWorkplace;
    private EditText updateEmployeeOccupation;
    private EditText updateEmployeeTime;
    private EditText updateEmployeeTagline;
    private EditText updateEmployeeBio;
    private Button updateEmployeeButton;

    private String token;
    private int id;

    public UpdateEmployeeDetailsFragment() {
        // Required empty public constructor
    }

    public static UpdateEmployeeDetailsFragment newInstance() {
        UpdateEmployeeDetailsFragment fragment = new UpdateEmployeeDetailsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        token = bundle.getString(Constants.TOKEN_KEY);
        id = bundle.getInt(Constants.ID_KEY);

        return inflater.inflate(R.layout.fragment_update_employee_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Employee employee = UserDAO.getSpecificEmployee(id, token);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateEmployeeFirstName = view.findViewById(R.id.update_employee_first_name);
                        updateEmployeeFirstName.setText(employee.getFirstName());

                        updateEmployeeLastName = view.findViewById(R.id.update_employee_last_name);
                        updateEmployeeLastName.setText(employee.getLastName());

                        updateEmployeeWorkplace = view.findViewById(R.id.update_employee_workplace);
                        updateEmployeeWorkplace.setText(employee.getWorkplace());

                        updateEmployeeOccupation = view.findViewById(R.id.update_employee_service_type);
                        updateEmployeeOccupation.setText(employee.getServiceType());

                        updateEmployeeTime = view.findViewById(R.id.update_employee_time);
                        updateEmployeeTime.setText(employee.getTimeAtJob());

                        updateEmployeeTagline = view.findViewById(R.id.update_employee_tagline);
                        updateEmployeeTagline.setText(employee.getTagline());

                        updateEmployeeBio = view.findViewById(R.id.update_employee_bio);
                        updateEmployeeBio.setText(employee.getBio());

                        updateEmployeeButton = view.findViewById(R.id.update_employee_button);
                        updateEmployeeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateEmployee(employee);
                            }
                        });
                    }
                });
            }
        }).start();



    }

    public void updateEmployee(Employee employee) {

        final Employee employee1 = employee;
        employee1.setFirstName(updateEmployeeFirstName.getText().toString());
        employee1.setLastName(updateEmployeeLastName.getText().toString());
        employee1.setWorkplace(updateEmployeeWorkplace.getText().toString());
        employee1.setServiceType(updateEmployeeOccupation.getText().toString());
        employee1.setTimeAtJob(updateEmployeeTime.getText().toString());
        employee1.setTagline(updateEmployeeTagline.getText().toString());
        employee1.setBio(updateEmployeeBio.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserDAO.updateEmployee(employee1, id, token);
            }
        }).start();
    }


}
