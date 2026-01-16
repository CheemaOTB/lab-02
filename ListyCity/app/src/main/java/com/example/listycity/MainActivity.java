package com.example.listycity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // vars
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    int selectedPosition = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        //define array
        String []cities = {"Edmonton","Vancouver","Moscow","Sydney"};
        //        String []cities = {"Edmonton","Vancouver","Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka","New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);


        // button logic
        // city button
        Button addCityButton = findViewById(R.id.button_addCity);

        // open modal on city button click
        addCityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAddCityModal();
            }



        });

        // detect when a city is selected/tapped by the user
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
            }
        });

        // delete city button
        Button deleteCityButton = findViewById(R.id.button_deleteCity);
        // remove selected city from city list and gui
        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if a city is selected
                if (selectedPosition != -1) {
                    dataList.remove(selectedPosition);
                    // clear/reset selection
                    selectedPosition = -1;
                    //update ui
                    cityAdapter.notifyDataSetChanged();

                }

            }
        });

    }

    // modal window for adding city
    private void showAddCityModal() {
        AlertDialog.Builder modal = new AlertDialog.Builder(this);


        modal.setTitle("Add City");

        // input for city
        EditText input = new EditText(this);
        input.setHint("City Name");
        input.setTextAlignment(ListView.TEXT_ALIGNMENT_CENTER);

        modal.setView(input);

        // confirm button
        modal.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cityName = input.getText().toString();
                // add input city to city adapter
                // check if city is already added
                if (!cityName.isEmpty() && !dataList.contains(cityName)) {
                    dataList.add(cityName);
                    cityAdapter.notifyDataSetChanged();
                }
            }
        });

        // cancel button
        modal.setNegativeButton("Cancel", null);

        modal.show();



    }

}

