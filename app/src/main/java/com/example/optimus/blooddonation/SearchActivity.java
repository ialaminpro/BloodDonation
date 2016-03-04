package com.example.optimus.blooddonation;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends Activity{

    String search_for;
    Button show_button;
    static int x;
    private Spinner spinner;
    private static final String[]options = {"All donors", "A positive",
            "A negative", "B positive", "B negative", "O positive", "O negative", "AB positive",
            "AB negative"};

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        show_button = (Button) findViewById(R.id.show);
        show_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, Donor_List_Activity.class).putExtra("search_for", search_for).putExtra("activity", "SearchActivity"));
            }
        });



        spinner = (Spinner)findViewById(R.id.search_options);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,
                R.layout.spinner_layout,options);

        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                search_for = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }




}


