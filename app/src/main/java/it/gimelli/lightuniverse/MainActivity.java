package it.gimelli.lightuniverse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button button1;
    Button button2;
    Button button3;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        button1 = (Button) findViewById(R.id.button6);
        edt=(EditText)findViewById(R.id.editText2);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String ret = null;
                CaricaJob job = new CaricaJob();
                job.execute("url",null,ret);
              //  edt.setText(ret);


            }

        });

        button2 = (Button) findViewById(R.id.button7);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                edt.setText("ciao come stai");

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    class CaricaJob extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here
            try {

                getClientList();

            }catch (Exception ex){
                ex.printStackTrace();
                return ex.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String message) {
            EditText edt=(EditText)findViewById(R.id.editText2);
            edt.setText(message);
        }

        public void getClientList() {
            int macCount = 0;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("/proc/net/arp"));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitted = line.split(" +");
                    if (splitted != null ) {
                        // Basic sanity check
                        String mac = splitted[3];
                        System.out.println("Mac : Outside If "+ mac );
                        if (mac.matches("..:..:..:..:..:..")) {
                            macCount++;
                   /* ClientList.add("Client(" + macCount + ")");
                    IpAddr.add(splitted[0]);
                    HWAddr.add(splitted[3]);
                    Device.add(splitted[5]);*/
                            System.out.println("Mac : "+ mac + " IP Address : "+splitted[0] );
                            System.out.println("Mac_Count  " + macCount + " MAC_ADDRESS  "+ mac);
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Mac_Count  " + macCount + "   MAC_ADDRESS  "
                                            + mac, Toast.LENGTH_SHORT).show();

                        }
               /* for (int i = 0; i < splitted.length; i++)
                    System.out.println("Addressssssss     "+ splitted[i]);*/

                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        public String getHtmlPage(){

            try{
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
    }

}
