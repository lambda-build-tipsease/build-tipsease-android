package com.vivekvishwanath.tipsease;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeDetailsFragment extends Fragment {

    private TextView employeeDetailsName;
    private ImageView employeeDetailsImage;
    private TextView employeeDetailsTagline;
    private RatingBar employeeDetailsRatingBar;
    private TextView employeeDetailsWorkplace;
    private TextView employeeDetailsOccupation;
    private TextView employeeDetailsBio;

    private Employee employee;


    public EmployeeDetailsFragment() {
        // Required empty public constructor
    }

    public static EmployeeDetailsFragment newInstance() {
        EmployeeDetailsFragment frag = new EmployeeDetailsFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        employee = (Employee) bundle.getSerializable("employee");
        return inflater.inflate(R.layout.employee_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        employeeDetailsName = view.findViewById(R.id.employee_details_name);
        employeeDetailsImage = view.findViewById(R.id.employee_details_image);
        employeeDetailsTagline = view.findViewById(R.id.employee_details_tagline);
        employeeDetailsRatingBar = view.findViewById(R.id.employee_details_rating_bar);
        employeeDetailsWorkplace = view.findViewById(R.id.employee_details_workplace);
        employeeDetailsOccupation = view.findViewById(R.id.employee_details_occupation);
        employeeDetailsBio = view.findViewById(R.id.employee_details_bio);

        employeeDetailsName.setText(employee.getFirstName() + " " + employee.getLastName());
        employeeDetailsImage.setImageBitmap(CustomerMainActivity.employeeImages.get(employee.getId()));
        employeeDetailsTagline.setText(employee.getTagline());
        employeeDetailsRatingBar.setRating((float)employee.getRating());
        employeeDetailsWorkplace.setText(employee.getWorkplace());
        employeeDetailsOccupation.setText(employee.getServiceType());
        employeeDetailsBio.setText(employee.getBio());

    }
}