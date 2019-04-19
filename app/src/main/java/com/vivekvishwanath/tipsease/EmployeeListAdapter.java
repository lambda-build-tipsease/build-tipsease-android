package com.vivekvishwanath.tipsease;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    ArrayList<Employee> matchedEmployees;
    Context context;
    private int lastPosition = -1;

    public EmployeeListAdapter(ArrayList<Employee> matchedEmployees) {
        this.matchedEmployees = matchedEmployees;
    }

    @NonNull
    @Override
    public EmployeeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.searched_employee_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EmployeeListAdapter.ViewHolder holder, int position) {
        final Employee employee = matchedEmployees.get(position);
        holder.employeeFirstName.setText(employee.getFirstName());
        holder.employeeLastName.setText(employee.getLastName());
        holder.employeeWorkplace.setText(employee.getWorkplace());
        holder.employeeOccupation.setText(employee.getServiceType());
        holder.employeeImageView.setImageBitmap(CustomerMainActivity.employeeImages.get(employee.getId()));

        holder.searchedEmployeeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                EmployeeDetailsFragment fragment = EmployeeDetailsFragment.newInstance();

                Bundle bundle = new Bundle();
                Bundle empBundle = new Bundle();
                Bundle options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity)v.getContext(),
                        holder.employeeImageView,
                        ViewCompat.getTransitionName(holder.employeeImageView)
                ).toBundle();
                empBundle.putSerializable("employee", employee);
                bundle.putBundle("empBundle", empBundle);
                bundle.putBundle("animationBundle", options);

                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.employee_details_fragment_container, fragment).commit();
            }
        });

        setEnterAnimation(holder.searchedEmployeeCardView, position);
    }

    private void setEnterAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            }
        }

    @Override
    public int getItemCount() {
        return matchedEmployees.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView searchedEmployeeCardView;
        TextView employeeFirstName;
        TextView employeeLastName;
        TextView employeeWorkplace;
        TextView employeeOccupation;
        ImageView employeeImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            searchedEmployeeCardView = itemView.findViewById(R.id.employee_card_view);
            employeeFirstName = itemView.findViewById(R.id.employee_first_name);
            employeeLastName = itemView.findViewById(R.id.employee_last_name);
            employeeWorkplace = itemView.findViewById(R.id.employee_workplace);
            employeeOccupation = itemView.findViewById(R.id.employee_occupation);
            employeeImageView = itemView.findViewById(R.id.employee_image_view);

        }
    }
}
