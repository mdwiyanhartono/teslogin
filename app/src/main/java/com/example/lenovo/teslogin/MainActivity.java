package com.example.lenovo.teslogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    private EditText etPassword;

    String username;
    String bank;
    String branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Reference to variables
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);

    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View v) {

        // Get text from email and passord field
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        System.out.println("eror async");
        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(email,password);

    }



    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://www.anagata.co.id/apifwdata/login.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    String hasil = "x";
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray array = json.getJSONArray("login");

//                    for(int i=0 ;i<array.length();i++){
//                        username = array.getJSONObject(i).getString("username");
//                        bank = array.getJSONObject(i).getString("bank");
//                        branch = array.getJSONObject(i).getString("branch");
//                    }

                    username = array.getJSONObject(0).getString("username");
                    bank = array.getJSONObject(0).getString("bank");
                    branch = array.getJSONObject(0).getString("branch");

                    // Pass data to onPostExecute method
                    return (json.getString("success").toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception"+e.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return "exception"+e.toString();
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
             pdLoading.dismiss();

           // pdLoading.dismiss();
            //extract json jadi string...

            if (result.equalsIgnoreCase("1")) {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("username",username);
                intent.putExtra("bank",bank);
                intent.putExtra("branch",branch);
                startActivity(intent);


//               Intent intent2 = new Intent(MainActivity.this, NavBarHead.class);
//                intent2.putExtra("username",username);
//                intent2.putExtra("bank",bank);
//                intent2.putExtra("branch",branch);
//                startActivity(intent2);

                //MainActivity.this.finish();

            } else if (result.equalsIgnoreCase("0")) {

                // If username and password does not match display a error message
                Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }
    }
}
