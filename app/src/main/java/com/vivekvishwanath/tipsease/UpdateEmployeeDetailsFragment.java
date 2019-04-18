package com.vivekvishwanath.tipsease;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


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
    Employee thisEmployee;

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

        updateEmployeeImage = view.findViewById(R.id.update_employee_image);
        updateEmployeeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.SELECT_IMAGE_REQUEST_CODE);
            }
        });
        updateEmployeeFirstName = view.findViewById(R.id.update_employee_first_name);
        updateEmployeeLastName = view.findViewById(R.id.update_employee_last_name);
        updateEmployeeWorkplace = view.findViewById(R.id.update_employee_workplace);
        updateEmployeeOccupation = view.findViewById(R.id.update_employee_service_type);
        updateEmployeeTime = view.findViewById(R.id.update_employee_time);
        updateEmployeeTagline = view.findViewById(R.id.update_employee_tagline);
        updateEmployeeBio = view.findViewById(R.id.update_employee_bio);
        updateEmployeeButton = view.findViewById(R.id.update_employee_button);

        new Thread(new Runnable() {
            @Override
            public void run() {
                thisEmployee = UserDAO.getSpecificEmployee(id, token);
                final Bitmap employeeImage = UserDAO.getEmployeeImage(thisEmployee.getImageUrl());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        updateEmployeeImage.setImageBitmap(employeeImage);
                        updateEmployeeFirstName.setText(thisEmployee.getFirstName());
                        updateEmployeeLastName.setText(thisEmployee.getLastName());
                        updateEmployeeWorkplace.setText(thisEmployee.getWorkplace());
                        updateEmployeeOccupation.setText(thisEmployee.getServiceType());
                        updateEmployeeTime.setText(thisEmployee.getTimeAtJob());
                        updateEmployeeTagline.setText(thisEmployee.getTagline());
                        updateEmployeeBio.setText(thisEmployee.getBio());
                    }
                });
                updateEmployeeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                UserDAO.updateEmployee(updateEmployee(thisEmployee), id, token);
                            }
                        }).start();
                    }
                });
            }
        }).start();

    }

    public Employee updateEmployee(Employee employee) {

        final Employee employee1 = employee;
        employee1.setFirstName(updateEmployeeFirstName.getText().toString());
        employee1.setLastName(updateEmployeeLastName.getText().toString());
        employee1.setWorkplace(updateEmployeeWorkplace.getText().toString());
        employee1.setServiceType(updateEmployeeOccupation.getText().toString());
        employee1.setTimeAtJob(updateEmployeeTime.getText().toString());
        employee1.setTagline(updateEmployeeTagline.getText().toString());
        employee1.setBio(updateEmployeeBio.getText().toString());

        return employee1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.SELECT_IMAGE_REQUEST_CODE) {
            Uri imageUri = data.getData();
            updateEmployeeImage.setImageURI(imageUri);
            final String imageBase64 = convertUriToBase64(imageUri);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String imageUrl = UserDAO.uploadImageForUrl(imageBase64, thisEmployee.getFirstName() +
                            thisEmployee.getLastName() + id);
                    thisEmployee.setImageUrl(imageUrl);
                    UserDAO.updateEmployee(thisEmployee, id, token);
                }
            }).start();
        }
    }

    public String convertUriToBase64(Uri imageUri) {
        final InputStream imageStream;
        try {
            imageStream = getActivity().getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String encodedImage = encodeImage(selectedImage);
            return encodedImage;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        encImage = encImage.replace("\n", "");
        return encImage;

    }


}
