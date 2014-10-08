package com.example.vibrateandroid;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

	Vibrator vibrator;
	ServerSocket listener;
	EditText portEditText; 
	TextView statusEditText; 
	
	ListenOnPortTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		portEditText= (EditText) findViewById(R.id.portEditText);
		statusEditText= (TextView) findViewById(R.id.statusTextView);
		


	}

	public void vibrateButtonPressed(View view){
		vibrate();
	}
	public int getPort(){
		String port = portEditText.getText().toString();
		return  Integer.parseInt(port);
	}


	public void listenButtonPressed(View view){

		if(task!=null && !task.isCancelled()){
			
			//End the task
			
			task.cancel(true);
		}
		
		
		task = new ListenOnPortTask();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getPort());
		
	}


	public void vibrate() {
		//textWidget.setText(textWidget.getText() + "Zoom... ");
		vibrator.vibrate(600);
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



	class ListenOnPortTask extends AsyncTask<Integer, String, Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub


			for (Integer port : params) {
				begin(port);
			}

			return null;
		}
		
		protected void onProgressUpdate(String...strings ){
			for (String string : strings) {
				statusEditText.setText("listening on port: "+ getPort() +"\n status :"+string);
			}
			
		}


		public void begin(int port){
			//Close if open
			if(listener!=null ){

				try {
					listener.close();
					
					
					Log.i("ASYNCTASK", "closed port ");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			listener = null;
			try {
				listener = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				while (true) {
					publishProgress("Listening");
					Socket socket = listener.accept();
					try {

						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						String x ="";
						vibrate();
						String msg="";
						while((x=in.readLine())!=null){
							msg+=x;
							
						}
						publishProgress("Recieved: "+msg);

						
					} finally {
						socket.close();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					if(listener!=null)
						listener.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


	}






}
