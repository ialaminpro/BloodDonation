package com.example.optimus.blooddonation;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.RequestQueue;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NewDonorForm extends Activity {
    EditText etFirstName, etLastName, etEmail, etPhone, etPassword,etConfirmPass,etLocation, etBlood;
    String firstName, lastName, email, phone, password, bloodGroup, location;
    Button btnSubmit;
    String clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_donor_form);
        etFirstName=(EditText) findViewById(R.id.et_first_name);
        etLastName=(EditText)findViewById(R.id.et_last_name);
        etEmail=(EditText)findViewById(R.id.et_email);
        etPassword=(EditText)findViewById(R.id.et_password);
        etConfirmPass=(EditText)findViewById(R.id.et_confirm_password);
        etPhone=(EditText)findViewById(R.id.et_phone);
        etLocation=(EditText)findViewById(R.id.et_location);
        etBlood=(EditText)findViewById(R.id.et_blood_group);
        btnSubmit=(Button)findViewById(R.id.btn_submit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etFirstName.getText().toString().length() < 2 ){
                    Toast.makeText(NewDonorForm.this, "Name should be at least 2 characters", Toast.LENGTH_SHORT).show();
                    etFirstName.requestFocus();
                }
                else if(etEmail.getText().toString().length() > 5){
                    Toast.makeText(NewDonorForm.this, "Enter your email address", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }
                else if(etEmail.getText().toString().indexOf("@") < 0){
                    Toast.makeText(NewDonorForm.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }
                else if(etPassword.getText().toString().length()< 1){
                    Toast.makeText(NewDonorForm.this, "Password can not be empty", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                }
                else if (etPassword.getText().toString().equals(etConfirmPass.getText().toString()) != true) {
                    Toast.makeText(NewDonorForm.this, "Password did not match", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                }
                else if (etPhone.getText().toString().length() < 11 || etPhone.getText().toString().length() > 15){
                    Toast.makeText(NewDonorForm.this, "Phone number should be at least 11 digits", Toast.LENGTH_SHORT).show();
                    etPhone.requestFocus();
                }
                else if (etLocation.getText().toString().length() < 3){
                    Toast.makeText(NewDonorForm.this, "Specify your location", Toast.LENGTH_SHORT).show();
                    etLocation.requestFocus();
                }
                else if (etBlood.getText().toString().length() < 1 || etPhone.getText().toString().length() > 15){
                    Toast.makeText(NewDonorForm.this, "Specify your blood group", Toast.LENGTH_SHORT).show();
                }
                else{
                    doPostRequest();
                }
            }
        });



        etBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = onCreateDialogSingleChoice(etBlood,"Blood Group", new String[]{"A positive",
                        "A negative", "B positive", "B negative", "O positive", "O negative", "AB positive",
                        "AB negative"});

                dialog.show();
            }
        });

    }


    public  void  doPostRequest() {

        com.android.volley.RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://blood.thejobayer.com/register_android.php";
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        email = etEmail.getText().toString();
        phone = etPhone.getText().toString();
        password = etPassword.getText().toString();
        location = etLocation.getText().toString();
        bloodGroup = etBlood.getText().toString();

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(NewDonorForm.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NewDonorForm.this, Donor_List_Activity.class).putExtra("activity", "NewDonorForm"));
                finish();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewDonorForm.this, "A problem occurred", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("firstName", firstName); //Add the data you'd like to send to the server.
                MyData.put("lastName", lastName); //Add the data you'd like to send to the server.
                MyData.put("email", email); //Add the data you'd like to send to the server.
                MyData.put("phone", phone); //Add the data you'd like to send to the server.
                MyData.put("password", password); //Add the data you'd like to send to the server.
                MyData.put("location", location); //Add the data you'd like to send to the server.
                MyData.put("bloodGroup", bloodGroup); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);

    }




    public AlertDialog onCreateDialogSingleChoice(final EditText editText,String title, final CharSequence[] array) {
        clicked = array[0].toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clicked = array[which].toString();
            }
        })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editText.setText(clicked);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }



}


