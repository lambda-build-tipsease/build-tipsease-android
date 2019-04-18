package com.vivekvishwanath.tipsease;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button addTipButton;
    private Button customerHomeButton;



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
        customerHomeButton = view.findViewById(R.id.customer_home_button);
        customerHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        employeeDetailsRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                if (fromUser) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String ratingResult = UserDAO.rateEmployee(employee.getId(), LoginActivity.prefs.getString(Constants.TOKEN_KEY, null), rating);
                            int i = 0;
                        }
                    }).start();
                }
            }
        });
        addTipButton = view.findViewById(R.id.add_tip_button);

        addTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                AddTipFragment fragment = AddTipFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("employee", employee);
                fragment.setArguments(bundle);
                fragment.show(transaction, "Add tip fragment");
            }
        });

        employeeDetailsName.setText(employee.getFirstName() + " " + employee.getLastName());
        employeeDetailsImage.setImageBitmap(CustomerMainActivity.employeeImages.get(employee.getId()));
        employeeDetailsTagline.setText(employee.getTagline());
        employeeDetailsRatingBar.setRating((float)employee.getRating());
        employeeDetailsWorkplace.setText(employee.getWorkplace());
        employeeDetailsOccupation.setText(employee.getServiceType());
        employeeDetailsBio.setText(employee.getBio());

    }
}
