package com.groupone.lenborro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;


public class AlreadyLoggedInActivity extends Activity {


    String ME = null;

    String lenborroCategory = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.already_logged_in);
        LoadData();


        if (ME != null)
        {



            if(lenborroCategory.equals("Borrower")) {

                Intent intent = new Intent(AlreadyLoggedInActivity.this, BorrowerActivity.class);
                startActivity(intent);
                finish();
            }
            else if (lenborroCategory.equals("Lender"))
            {
                Intent intent = new Intent(AlreadyLoggedInActivity.this, LenderActivity.class);
                startActivity(intent);
                finish();
            }
            else if(lenborroCategory.equals("Admin"))
            {
                Intent intent = new Intent(AlreadyLoggedInActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else
        {

            Intent intent = new Intent(AlreadyLoggedInActivity.this , LoginActivity.class);
            startActivity(intent);
            finish();
        }



    }


    public void LoadData()
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        ME = prefs.getString("LenborroME", null);
        lenborroCategory = prefs.getString("LenborroCategory", null);




    }





}
