package com.example.optimus.blooddonation;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.optimus.blooddonation.FetchDataListener;
import com.example.optimus.blooddonation.R;

import java.util.List;

public class Donor_List_Activity extends ListActivity implements FetchDataListener {

    private ProgressDialog dialog;
    Button btnRegister;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        btnRegister = (Button) findViewById(R.id.register);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_donor__list_);
        initView();
    }



    private void initView() {
        // show progress dialog
        dialog = ProgressDialog.show(this, "", "Loading...");

        String url;
        Intent in = getIntent();

        if(in.getStringExtra("activity").equals("SearchActivity"))
        {
            url = "http://blood.thejobayer.com/show_donors.php?blood=" + "%27" + in.getStringExtra("search_for").replace(" ", "%20") + "%27";
        }
        else
        {
            url = "http://blood.thejobayer.com/show_donors.php?blood=%27All%20donors%27";
        }



        FetchDataTask task = new FetchDataTask(this);
        task.execute(url);
      
    }





    @Override
    public void onFetchComplete(List<Application> data) {

        // dismiss the progress dialog
        if(dialog != null)  dialog.dismiss();
        // create new adapter

        Custom_Adapter adapter = new Custom_Adapter(this, data);
        // set the adapter to list
        setListAdapter(adapter);

    }

    @Override
    public void onFetchFailure(String msg) {
        // dismiss the progress dialog
        if(dialog != null)  dialog.dismiss();
        // show failure message
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void goToRegister(View view)
    {
        startActivity(new Intent(this, NewDonorForm.class));


    }

}
