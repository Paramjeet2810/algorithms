package com.suvariyaraj.algorithms;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DoubtActivity extends Activity {

    EditText algo_name, doubt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_doubt);

//        getActionBar ().setDisplayShowHomeEnabled (true);

        String t = getIntent().getStringExtra ("Title");

        algo_name = (EditText) findViewById (R.id.doubt_algoname);
        doubt = (EditText) findViewById (R.id.doubt_doubt);
        Button send = (Button) findViewById (R.id.doubt_send);

        algo_name.setText (t);
        algo_name.setFocusable (false);

        send.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("rajsuvariya@gmail.com") +
                        "?subject=" + Uri.encode("Doubt in "+algo_name.getText ()) +
                        "&body=" + Uri.encode("Hi!\nI am having following trouble in \""+algo_name.getText ()+"\" algorithm\nMy Doubt is : \n"+doubt.getText ());
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
            }
        });

    }
}
