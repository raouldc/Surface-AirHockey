package com.example.vibrateandroid;


import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

	Vibrator vibrator;
	MulticastSocket listener;
	EditText portEditText; 
	TextView statusEditText; 
	TextView myAddressTextView;
	ListenOnPortTask task;
	

	private AtomicBoolean IsVibrating =  new AtomicBoolean(false);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		portEditText= (EditText) findViewById(R.id.portEditText);
		statusEditText= (TextView) findViewById(R.id.statusTextView);
		myAddressTextView= (TextView) findViewById(R.id.myAddressTextView);
		myAddressTextView.setText(wifiIpAddress(this));



	}
	
	
	
	protected String wifiIpAddress(Context context) {
	    WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
	    int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

	    // Convert little-endian to big-endianif needed
	    if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
	        ipAddress = Integer.reverseBytes(ipAddress);
	    }

	    byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

	    String ipAddressString;
	    try {
	        ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
	    } catch (UnknownHostException ex) {
	        Log.e("WIFIIP", "Unable to get host address.");
	        ipAddressString = null;
	    }

	    return ipAddressString;
	}
	public void vibrateButtonPressed(View view){
		vibrate();
	}
	public int getPort(){
		String port = portEditText.getText().toString();
		return  Integer.parseInt(port);
	}


	public void listenButtonPressed(View view){

		if(task==null){
			
			Button listenButton= (Button) findViewById(R.id.listenButton);
			listenButton.setEnabled(false);
			task = new ListenOnPortTask();
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getPort());
		}

	}


	/**
	 * Vibrates if not vibrating.
	 * Also starts a thread to mark not vibrating.
	 */
	public void vibrate() {
		//textWidget.setText(textWidget.getText() + "Zoom... ");
		if(!IsVibrating.get()){
			final int time= 150;
			vibrator.vibrate(time);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(time);
						IsVibrating.set(false);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
	}


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



	/**
	 * Async code to handle UDP network communication. 
	 * @author Ace
	 *
	 */
	class ListenOnPortTask extends AsyncTask<Integer, String, Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			begin(params[0]);
			return null;
		}

		protected void onProgressUpdate(String...strings ){
			for (String string : strings) {
				statusEditText.setText("listening on port: "+ getPort() +"\n status :"+string);
				vibrate();
			}

		}
		public void begin(int port){
			while (true) {
				try{
					String text;
					int server_port = port;
					byte[] message = new byte[1500];
					//publishProgress("Listening");
					DatagramSocket s = new DatagramSocket(server_port);
					DatagramPacket p = new DatagramPacket(message, message.length);
					s.receive(p);
					text = new String(message, 0, p.getLength());
					publishProgress(text);
					s.close();
				}catch (Exception e){

					Log.e(MainActivity.class.getName(),e.getMessage());
				}
			}
		}
	}
}
