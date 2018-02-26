package com.bit.blmt.nl4fr_fillwithwoorden;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class NL4FR_FillMain extends AppCompatActivity {

    String ServerURL = "http://www.quizart.be/lidwoord/FillFile.php" ;
    Spinner sp_lidwoord = null;
    Spinner sp_fr_lidwoord = null;
    EditText et_woord = null;
    EditText et_vertaling = null;
    String file = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nl4_fr__fill_main);
        sp_lidwoord = (Spinner) findViewById(R.id.sp_lidwoord);
        sp_fr_lidwoord = (Spinner) findViewById(R.id.sp_fr_lidwoord);
        et_vertaling = (EditText) findViewById(R.id.et_vertaling);
        et_woord = (EditText) findViewById(R.id.et_woord);

        et_woord.setOnFocusChangeListener( new View.OnFocusChangeListener() {

            public void onFocusChange( View v, boolean hasFocus ) {
                if( hasFocus ) {
                    System.out.println("value : " + et_woord.getText().toString());
                    if(et_woord.getText().toString().trim().equals("Woord")) {

                        et_woord.setText("", TextView.BufferType.EDITABLE);
                    }
                }
            }

        } );

        et_vertaling.setOnFocusChangeListener( new View.OnFocusChangeListener() {

            public void onFocusChange( View v, boolean hasFocus ) {
                if( hasFocus ) {
                    System.out.println("value : " + et_vertaling.getText().toString());
                    if(et_vertaling.getText().toString().trim().equals("Vertaling")) {

                        et_vertaling.setText("", TextView.BufferType.EDITABLE);
                    }
                }
            }

        } );


    }

    public void save_woord(View v)
    {
        if(sp_lidwoord.getSelectedItem().toString() == "Het")
        {
            file = "hetwoorden.txt";
        }
        else
        {
            file = "dewoorden.txt";
        }
        InsertData(et_woord.getText().toString(),sp_lidwoord.getSelectedItem().toString(),sp_fr_lidwoord.getSelectedItem().toString(),et_vertaling.getText().toString(),file);
    }

    public void InsertData(final String woord, final String lidwoord, final String fr_lidwoord, final String vertaling, final String filetofill) {

        System.out.println("Enter InsertData Function");
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                System.out.println("Enter Do in Background");

                try {
                    System.out.println("Try...");
                    //Toast.makeText(NL4FR_FillMain.this, "Fill with : " + woord + " " + lidwoord + " " + vertaling + " " + fr_lidwoord + " " + filetofill,Toast.LENGTH_LONG).show();
                    System.out.println("woord : " + woord);
                    System.out.println("lidwoord : " + lidwoord);
                    System.out.println("vertaling : " + vertaling);
                    System.out.println("fr_lid : " + fr_lidwoord);
                    System.out.println("file : " + filetofill);
                    String data = URLEncoder.encode("woord", "UTF-8") + "=" +
                            URLEncoder.encode(woord, "UTF-8");
                    data += "&" + URLEncoder.encode("lidwoord", "UTF-8") + "=" +
                            URLEncoder.encode(lidwoord, "UTF-8");
                    data += "&" + URLEncoder.encode("vertaling", "UTF-8") + "=" +
                            URLEncoder.encode(vertaling, "UTF-8");
                    data += "&" + URLEncoder.encode("fr_lidwoord", "UTF-8") + "=" +
                            URLEncoder.encode(fr_lidwoord, "UTF-8");
                    data += "&" + URLEncoder.encode("filetofill", "UTF-8") + "=" +
                            URLEncoder.encode(filetofill, "UTF-8");

                    URL url = new URL(ServerURL);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    System.out.println("Read Server Response");
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    //Toast.makeText(NL4FR_FillMain.this, "Data Sent !",Toast.LENGTH_LONG).show();
                    return sb.toString();

                } catch (Exception e) {
                    System.out.println("Exception :" + e.toString());
                    //Toast.makeText(NL4FR_FillMain.this, "Exception :" + e.toString(),Toast.LENGTH_LONG).show();
                    return new String("Exception: " + e.getMessage());

                } finally {
                    System.out.println("Request Sent !");
                    System.out.println("Data Inserted Successfully");
                    Toast.makeText(NL4FR_FillMain.this, "Request Sent !",Toast.LENGTH_LONG).show();
                    return "ok";
                }

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute();

        System.out.println("Exit InsertData Function");

        Toast.makeText(NL4FR_FillMain.this, "Send Finished !",Toast.LENGTH_LONG).show();
    }

    public void clear_me(View v)
    {
        EditText toclear = (EditText) findViewById(v.getId());
        toclear.setText("");
    }
}
