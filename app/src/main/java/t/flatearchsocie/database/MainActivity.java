package t.flatearchsocie.database;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {


    String username, password, admin;
    int userID;
    Button insert;
    EditText txt1, txt2;
    TextView txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        insert = (Button) findViewById(R.id.button);
        txt1 = (EditText) findViewById(R.id.editText);
        txt2 = (EditText) findViewById(R.id.editText2);
        txt3 = (TextView) findViewById(R.id.textView);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            txt3.setVisibility(View.INVISIBLE);
        } else {

            insert.setEnabled(false);
        }

    }


    public void onClickAdd(View view){

        startActivity(new Intent(String.valueOf(BackgroundTask.class)));
        username = txt1.getText().toString();
        password = txt2.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(username, password);
        finish();
    }

    class BackgroundTask extends AsyncTask<String, Void, String> {

        String add_info_url;

        @Override
        protected void onPreExecute() {
            add_info_url = "http://polyandrous-strands.000webhostapp.com/addinfo.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String username, password;
            username = args[0];
            password = args[1];
            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_String = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(2), "UTF-8") + "&" +
                        URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("admin", "UTF-8") + "=" + URLEncoder.encode("none", "UTF-8");

                bufferedWriter.write(data_String);
                bufferedWriter.flush();
                //bufferedWriter.close();
                outputStream.close();

               //InputStream inputStream = httpURLConnection.getInputStream();
               // inputStream.close();
                httpURLConnection.disconnect();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "One Row Inserted";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }


    }



}
