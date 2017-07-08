package it.gimelli.lightuniverse;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by simgim on 23/06/2017.
 */
class SendfeedbackJob extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String[] params) {
        // do above Server call here
        try {


            String link = "http://www.google.com";
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String webPage = "vuoto",data="";

            while ((data = reader.readLine()) != null){
                webPage += data + "\n";
            }

            return webPage;

        }catch (Exception ex){
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String message) {

    }
}