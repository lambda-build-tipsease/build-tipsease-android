package com.vivekvishwanath.tipsease;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SignUpChoiceFragment extends DialogFragment {

    RadioGroup signUpRadioGroup;
    Button createAccountButton;

    public SignUpChoiceFragment() {
        // Required empty public constructor
    }

    public static SignUpChoiceFragment newInstance() {
        SignUpChoiceFragment frag = new SignUpChoiceFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_choice, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signUpRadioGroup = view.findViewById(R.id.sign_up_radio_group);

        createAccountButton = view.findViewById(R.id.create_account_button);

        final FragmentManager fragmentManager = getFragmentManager();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButton = signUpRadioGroup.getCheckedRadioButtonId();
                if (checkedRadioButton == R.id.customer_sign_up_radio_button) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment prev = fragmentManager.findFragmentByTag(Constants.SIGN_UP_CHOICE_FRAGMENT_TAG);
                    if (prev != null)
                        fragmentTransaction.remove(prev);
                    fragmentTransaction.addToBackStack(null);
                    CreateCustomerAccountFragment frag = CreateCustomerAccountFragment.newInstance();
                    frag.show(fragmentTransaction, Constants.CREATE_CUSTOMER_FRAGMENT_TAG);

                } else if (checkedRadioButton == R.id.employee_sign_up_radio_button) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment prev = fragmentManager.findFragmentByTag(Constants.SIGN_UP_CHOICE_FRAGMENT_TAG);
                    if (prev != null)
                        fragmentTransaction.remove(prev);
                    fragmentTransaction.addToBackStack(null);
                    CreateEmployeeAccountFragment frag = CreateEmployeeAccountFragment.newInstance();
                    frag.show(fragmentTransaction, Constants.CREATE_EMPLOYEE_FRAGMENT_TAG);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.select_account_type_text, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
