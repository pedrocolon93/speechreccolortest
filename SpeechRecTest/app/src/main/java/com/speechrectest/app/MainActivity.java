package com.speechrectest.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener{
    LinearLayout background = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (findViewById(R.id.button1)).setOnClickListener(this);
        background = (LinearLayout)findViewById(R.id.background);
    }

    protected static final int REQUEST_OK = 1;

    @Override
    public void onClick(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, REQUEST_OK);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
            setBackgroundColor(thingsYouSaid);
        }
    }

    private void setBackgroundColor(ArrayList<String> thingsYouSaid) {
        if(thingsYouSaid.size()==0){
            return;
        }
        String mainString  = thingsYouSaid.get(0);
        try{
            background.setBackgroundColor(Color.parseColor(mainString));
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,"Could not find color...",Toast.LENGTH_LONG).show();
        }

    }
}
