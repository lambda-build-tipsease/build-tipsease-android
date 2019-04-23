package com.vivekvishwanath.tipsease;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private EditText searchEmployeeEditText;
    private Button customerLogoutButton;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EmployeeListAdapter employeeListAdapter;

    private ArrayList<Employee> matchedEmployees = new ArrayList<>();
    public static HashMap<Integer, Bitmap> employeeImages = new HashMap<>();
    public static HashMap<Integer, Integer> employeePositions = new HashMap<>();
    private ArrayList<Employee> allEmployees = new ArrayList<>();

    private String token = "";
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_navigation);
        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.cus_nav_name);

        token = LoginActivity.prefs.getString(Constants.TOKEN_KEY, null);
        id = LoginActivity.prefs.getInt(Constants.ID_KEY, 0);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        employeeListAdapter = new EmployeeListAdapter(matchedEmployees);
        recyclerView.setAdapter(employeeListAdapter);

        searchEmployeeEditText = findViewById(R.id.search_employee_edit_text);
        new Thread(new Runnable() {
            @Override
            public void run() {
                allEmployees = UserDAO.getAllEmployees(token);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        employeeListAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        searchEmployeeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                matchedEmployees.clear();
                employeeImages.clear();
                employeePositions.clear();
                employeeListAdapter.notifyDataSetChanged();
                    for (int i = 0; i < allEmployees.size(); i++) {
                        if (allEmployees.get(i).getFirstName().startsWith(s.toString())) {
                            matchedEmployees.add(allEmployees.get(i));
                            final int employeeId = allEmployees.get(i).getId();
                            final String imageUrl = allEmployees.get(i).getImageUrl();
                            employeePositions.put(employeeId, matchedEmployees.size() - 1);
                            if (imageUrl != null) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        employeeImages.put(employeeId, UserDAO.getEmployeeImage(imageUrl));
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                employeeListAdapter.notifyItemChanged(employeePositions.get(employeeId));
                                            }
                                        });
                                    }
                                }).start();
                            }
                        }
                    }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
