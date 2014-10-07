package com.example.vibrateandroid;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener{
	
	Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        
        final Button button = (Button) findViewById(R.id.button_id);
        button.setOnClickListener(this);
    }
    
    public void onClick(View view) {
       vibrate();
    }
    	 
    public void vibrate() {
    	//textWidget.setText(textWidget.getText() + "Zoom... ");
    	v.vibrate(300);
    }

    /*public void vibrate(View v){
    	Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
    	 // Vibrate for 500 milliseconds
    	 vibrator.vibrate(500);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
