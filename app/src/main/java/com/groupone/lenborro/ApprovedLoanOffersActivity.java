package com.groupone.lenborro;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by user on 12/16/2016.
 */

public class ApprovedLoanOffersActivity extends Activity {

    TextView loanOfferIDView;
    TextView loanAmountView;
    TextView interestRateView;
    TextView repaymentDurationView;
    TextView totalRepaymentAmountView;
    TextView monthlyPaymentView;


    TextView FirstNameView;
    TextView LastNameView;
    TextView DateOfBirthView;
    TextView EmailView;
    TextView PhoneNumberView;
    TextView SSNView;
    TextView AddressView;
    TextView BankNameView;
    TextView IBANView;


    String []dataValues = new String[10];
    int counter = 0;


    String lenborroCategory = null;

    String ME = null;
    String loanOfferID = null;
    String loanAmount = null;
    String interestRate = null;
    String repaymentDuration = null;
    String totalRepaymentAmount = null;
    String monthlyPayment = null;
    String Email = null;
    String loanStatus = null;

    String FirstName = null;
    String LastName = null;
    String DateOfBirth = null;
    String PhoneNumber = null;
    String SSN = null;
    String Address = null;
    String BankName = null;
    String IBAN = null;


    Bitmap photo_YOU;
    Bitmap bankStatementPhoto;
    Bitmap IDCardPhoto;

    ImageView profileImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approved_loan_offers_activity);

        LoadData();

        Intent intent = getIntent();

        loanOfferID = intent.getStringExtra("LoanOfferID");
        loanAmount  = intent.getStringExtra("LoanAmount");
        interestRate = intent.getStringExtra("InterestRate");
        repaymentDuration = intent.getStringExtra("RepaymentDuration");
        totalRepaymentAmount = intent.getStringExtra("TotalRepaymentAmount");
        monthlyPayment = intent.getStringExtra("MonthlyPayment");
        Email = intent.getStringExtra("Email");
        loanStatus = intent.getStringExtra("LoanStatus");


        loanOfferIDView = (TextView) findViewById(R.id.LoanOfferID);
        loanAmountView = (TextView) findViewById(R.id.LoanAmountID);
        interestRateView = (TextView) findViewById(R.id.InterestRateID);
        repaymentDurationView = (TextView) findViewById(R.id.RepaymentDurationID);
        totalRepaymentAmountView = (TextView) findViewById(R.id.TotalRepaymentAmountID);
        monthlyPaymentView = (TextView) findViewById(R.id.MonthlyPaymentID);


        FirstNameView = (TextView) findViewById(R.id.FirstNameID);
        LastNameView  = (TextView) findViewById(R.id.LastNameID);
        DateOfBirthView = (TextView) findViewById(R.id.DateOfBirthID);
        EmailView = (TextView) findViewById(R.id.EmailID);
        PhoneNumberView = (TextView) findViewById(R.id.PhoneNumberID);
        SSNView = (TextView) findViewById(R.id.SSNID);
        AddressView = (TextView) findViewById(R.id.AddressID);
        BankNameView = (TextView) findViewById(R.id.BankNameID);
        IBANView = (TextView) findViewById(R.id.IBANID);

        profileImage = (ImageView) findViewById(R.id.ProfilePhotoID);


        loanOfferIDView.setText("Loan Offer ID: " + loanOfferID);
        loanAmountView.setText("Loan Amount: " + loanAmount + " EUR");
        interestRateView.setText("Interest Rate: " + interestRate + " %");
        repaymentDurationView.setText("Repayment Duration: " + repaymentDuration + "months");
        totalRepaymentAmountView.setText("Total Repayment Amount: " + totalRepaymentAmount + " EUR");
        monthlyPaymentView.setText("Monthly Payment: " + monthlyPayment + " EUR");


        dataValues[0] = "";
        dataValues[1] = "";
        dataValues[2] = "";
        dataValues[3] = "";
        dataValues[4] = "";
        dataValues[5] = "";
        dataValues[6] = "";
        dataValues[7] = "";
        dataValues[8] = "";
        dataValues[9] = "";



        if (lenborroCategory.equals("Lender"))
        {
            new FetchDetailsFromServer().execute("http://www.voltbuy.com/Lenborro/UsersCredientals/Borrower/Credientals.txt");
        }
        else if (lenborroCategory.equals("Borrower"))
        {


            new FetchDetailsFromServer().execute("http://www.voltbuy.com/Lenborro/UsersCredientals/Lender/Credientals.txt");
        }


    }





    private class FetchDetailsFromServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return FetchDetails(urls[0]);
            } catch (IOException e) {
                return "NotFound";
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



            dataValues[0] = "";
            dataValues[1] = "";
            dataValues[2] = "";
            dataValues[3] = "";
            dataValues[4] = "";
            dataValues[5] = "";
            dataValues[6] = "";
            dataValues[7] = "";
            dataValues[8] = "";
            dataValues[9] = "";



            counter = 0;



        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            if (result.equals("OK")) {


                FirstName = dataValues[0];
                LastName = dataValues[1];
                DateOfBirth = dataValues[2];
                PhoneNumber = dataValues[5];
                SSN = dataValues[6];
                Address = dataValues[7];
                BankName = dataValues[8];
                IBAN = dataValues[9];




                new RetrieveProfilePhotoFromServer().execute("http://www.voltbuy.com/Lenborro/ProfilePhotos/" +  Email + ".jpg");



            }


            else if (result.equals("NotFound"))

            {
                Toast.makeText(getApplicationContext(), "No Loan Offers found", Toast.LENGTH_SHORT).show();



            }

            else
            {
                Toast.makeText(getApplicationContext(), "Network Connection Problem. Make sure that your internet is properly connected", Toast.LENGTH_SHORT).show();


            }






        }
    }

    private String FetchDetails(String myurl) throws IOException, UnsupportedEncodingException {
        InputStream is = null;


        // Only display the first 500 characters of the retrieved
        // web page content.


        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();


            is = conn.getInputStream();

            BufferedReader textReader = new BufferedReader(new InputStreamReader(is));


            String readlineText;


            while ((readlineText = textReader.readLine()) != null) {



                if(readlineText.length() <= 0)
                {
                    continue;
                }



                for (int i = 0 ; i < readlineText.length() ; ++i)
                {
                    if (readlineText.charAt(i) == '|')
                    {
                        ++counter;

                        continue;
                    }

                    dataValues[counter] = (dataValues[counter] + readlineText.charAt(i));
                }



                if (Email.equals(dataValues[3]))
                {
                    FirstName = dataValues[0];
                    LastName = dataValues[1];
                    DateOfBirth = dataValues[2];
                    PhoneNumber = dataValues[5];
                    SSN = dataValues[6];
                    Address = dataValues[7];
                    BankName = dataValues[8];
                    IBAN = dataValues[9];

                    break;
                }




                counter = 0;

                dataValues[0] = "";
                dataValues[1] = "";
                dataValues[2] = "";
                dataValues[3] = "";
                dataValues[4] = "";
                dataValues[5] = "";
                dataValues[6] = "";
                dataValues[7] = "";
                dataValues[8] = "";
                dataValues[9] = "";





            }




            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                conn.disconnect();
                return "OK";

            }
            else
            {
                conn.disconnect();
                return "NetworkError";
            }



            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {


            if (is != null)
            {
                is.close();


            }

        }
    }


    public void LoadData()
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        ME = prefs.getString("LenborroME", null);
        lenborroCategory = prefs.getString("LenborroCategory", null);


    }




    public void LogoutButtonClicked(View v)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("LenborroME", null);
        editor.apply();


        Intent intent = new Intent(ApprovedLoanOffersActivity.this , AlreadyLoggedInActivity.class);
        startActivity(intent);
        finish();
    }


    public void BackButtonClicked(View v)
    {

        Intent intent = new Intent(ApprovedLoanOffersActivity.this , AlreadyLoggedInActivity.class);
        startActivity(intent);
        finish();
    }






    private class RetrieveProfilePhotoFromServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return RetrieveProfilePhoto(urls[0]);
            } catch (IOException e) {


                return "Unable to retrieve web page. URL may be invalid.";
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            photo_YOU = null;


        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            if (result.equals("OK")) {


                FirstNameView.setText("First Name: " + FirstName);
                LastNameView.setText("Last Name: " + LastName);
                DateOfBirthView.setText("Date of Birth: " + DateOfBirth);
                EmailView.setText("Email: " + Email);
                PhoneNumberView.setText("Contact No: " + PhoneNumber);;
                SSNView.setText("Social Security Number: " + SSN);
                AddressView.setText("Address: " + Address);
                BankNameView.setText("Bank Name: " + BankName);
                IBANView.setText("IBAN: " + IBAN);


                profileImage.setImageBitmap(photo_YOU);


            } else if (result.equals("NetworkError")) {

                //new RetrieveProfilePhotoFromServer().execute("http://www.voltbuy.com/Lenborro/ProfilePhotos/" + YOU + ".jpg");

            } else {
                // new RetrieveProfilePhotoFromServer().execute("http://www.voltbuy.com/Lenborro/ProfilePhotos/" + YOU + ".jpg");
            }


        }
    }

    private String RetrieveProfilePhoto(String myurl) throws IOException, UnsupportedEncodingException {
        InputStream is = null;

        // Only display the first 500 characters of the retrieved
        // web page content.


        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);
            conn.addRequestProperty("Cache-Control", "no-cache");
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();


            is = conn.getInputStream();

            BufferedReader textReader = new BufferedReader(new InputStreamReader(is));


            BufferedInputStream bis = new BufferedInputStream(is, 8190);

            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            byte[] imageData = baf.toByteArray();
            photo_YOU = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);


            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return "OK";
            } else {
                return "NetworkError";
            }


        } finally {


            if (is != null) {
                is.close();


            }

        }
    }


    public void DownloadBankStatementButtonClicked(View v) {
        new RetrieveBankStatementFromServer().execute("http://www.voltbuy.com/Lenborro/BankStatementPhotos/" + Email + ".jpg");

    }


    private class RetrieveBankStatementFromServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return RetrieveBankStatement(urls[0]);
            } catch (IOException e) {


                return "Unable to retrieve web page. URL may be invalid.";
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bankStatementPhoto = null;


        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            if (result.equals("OK")) {



                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/Leborro/Bank Statements");
                myDir.mkdirs();
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                String fname =  Email +".jpg";
                File file = new File (myDir, fname);
                if (file.exists ()) file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bankStatementPhoto.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }




                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);// set your audio path
                intent.setDataAndType(Uri.fromFile(file), "image/*");

                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                Notification noti = new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("Download completed")
                        .setContentText("Bank Statement")
                        .setSmallIcon(R.drawable.icon_profile_avator)
                        .setContentIntent(pIntent).build();

                noti.flags |= Notification.FLAG_AUTO_CANCEL;

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, noti);




            }
        }

        private String RetrieveBankStatement(String myurl) throws IOException, UnsupportedEncodingException {
            InputStream is = null;

            // Only display the first 500 characters of the retrieved
            // web page content.


            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDefaultUseCaches(false);
                conn.addRequestProperty("Cache-Control", "no-cache");
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();


                is = conn.getInputStream();

                BufferedReader textReader = new BufferedReader(new InputStreamReader(is));


                BufferedInputStream bis = new BufferedInputStream(is, 8190);

                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }
                byte[] imageData = baf.toByteArray();
                bankStatementPhoto = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);


                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return "OK";
                } else {
                    return "NetworkError";
                }


            } finally {


                if (is != null) {
                    is.close();


                }

            }
        }


    }





    public void DownloadIDCardButtonClicked(View v) {
        new RetrieveIDCardFromServer().execute("http://www.voltbuy.com/Lenborro/IDCardPhotos/" + Email + ".jpg");

    }


    private class RetrieveIDCardFromServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return RetrieveIDCard(urls[0]);
            } catch (IOException e) {


                return "Unable to retrieve web page. URL may be invalid.";
            }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            IDCardPhoto = null;


        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            if (result.equals("OK")) {



                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/Leborro/ID Cards");
                myDir.mkdirs();
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                String fname =  Email +".jpg";
                File file = new File (myDir, fname);
                if (file.exists ()) file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    IDCardPhoto.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }




                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);// set your audio path
                intent.setDataAndType(Uri.fromFile(file), "image/*");

                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                Notification noti = new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("Download completed")
                        .setContentText("Identity Card")
                        .setSmallIcon(R.drawable.icon_profile_avator)
                        .setContentIntent(pIntent).build();

                noti.flags |= Notification.FLAG_AUTO_CANCEL;

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, noti);




            }
        }

        private String RetrieveIDCard(String myurl) throws IOException, UnsupportedEncodingException {
            InputStream is = null;

            // Only display the first 500 characters of the retrieved
            // web page content.


            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDefaultUseCaches(false);
                conn.addRequestProperty("Cache-Control", "no-cache");
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();


                is = conn.getInputStream();

                BufferedReader textReader = new BufferedReader(new InputStreamReader(is));


                BufferedInputStream bis = new BufferedInputStream(is, 8190);

                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }
                byte[] imageData = baf.toByteArray();
                IDCardPhoto = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);


                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return "OK";
                } else {
                    return "NetworkError";
                }


            } finally {


                if (is != null) {
                    is.close();


                }

            }
        }


    }








}
