package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    ArrayList<Employee> matchedEmployees;
    Context context;

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
    public void onBindViewHolder(@NonNull EmployeeListAdapter.ViewHolder holder, int position) {
        Employee employee = matchedEmployees.get(position);
        holder.employeeFirstName.setText(employee.getFirstName());
        holder.employeeLastName.setText(employee.getLastName());
        holder.employeeWorkplace.setText(employee.getWorkplace());
        holder.employeeOccupation.setText(employee.getServiceType());
        holder.employeeImageView.setImageBitmap(CustomerMainActivity.employeeImages.get(employee.getId()));
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
