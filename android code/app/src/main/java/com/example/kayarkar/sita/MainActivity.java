package com.example.kayarkar.sita;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.UUID;

import static com.example.kayarkar.sita.DeviceListActivity.EXTRA_DEVICE_ADDRESS;

public class MainActivity extends Activity {
    private FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference=mfirebaseDatabase.getReference();
    private DatabaseReference mHeadingReference=mRootReference.child("motor");
    private DatabaseReference mReference=mRootReference.child("notify");
    String ed;
    int state;
  Button btnOn, btnOff;
  TextView  txtString, txtStringLength;
  Handler bluetoothIn;

  final int handlerState = 0;        				 //used to identify handler message
  private BluetoothAdapter btAdapter = null;
  private BluetoothSocket btSocket = null;
  private StringBuilder recDataString = new StringBuilder();
    TextToSpeech t1;
  private ConnectedThread mConnectedThread;
    
  // SPP UUID service - this should work for most devices
  private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  
  // String for MAC address
  private static String address;

@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    Intent intent = new Intent(MainActivity.this, MyService2.class);
    //stopService(intent);

    t1=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if(status != TextToSpeech.ERROR) {
                t1.setLanguage(new Locale("hi", "IN"));
            }
        }
    });
    //Link the buttons and textViews to respective views 
    btnOn = (Button) findViewById(R.id.buttonOn);
    btnOff = (Button) findViewById(R.id.buttonOff);
    txtString = (TextView) findViewById(R.id.txtString);
    txtStringLength = (TextView) findViewById(R.id.testView1);



    bluetoothIn = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == handlerState) {										//if message is what we want
            	String readMessage = (String) msg.obj;
                mReference.setValue(readMessage);

                recDataString.append(readMessage);      								//keep appending to string until ~
                int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                if (endOfLineIndex > 0) {                                           // make sure there data before ~
                    String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                    txtString.setText("Data Received = " + dataInPrint);
                    int dataLength = dataInPrint.length();							//get length of data received
                    txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                    if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                    {
                    //	String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                    //	String sensor1 = recDataString.substring(6, 10);            //same again...
                    //	String sensor2 = recDataString.substring(11, 15);
                    //	String sensor3 = recDataString.substring(16, 20);

//                    	sensorView0.setText(" Sensor 0 Voltage = " + sensor0 + "V");	//update the textviews with sensor values
  //                  	sensorView1.setText(" Sensor 1 Voltage = " + sensor1 + "V");
    //                	sensorView2.setText(" Sensor 2 Voltage = " + sensor2 + "V");
      //              	sensorView3.setText(" Sensor 3 Voltage = " + sensor3 + "V");
                    }
                    recDataString.delete(0, recDataString.length()); 					//clear all string data
                   // strIncom =" ";
                    dataInPrint = " ";
                }
            }
        }
    };

    btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
    checkBTState();

    
  // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
    btnOff.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
          state=0;
        mConnectedThread.write("0");    // Send "0" via Bluetooth
        Toast.makeText(getBaseContext(), "Swing", Toast.LENGTH_SHORT).show();
      }
    });
  
    btnOn.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
          state=1;
        mConnectedThread.write("1");    // Send "1" via Bluetooth
        Toast.makeText(getBaseContext(), "Stop", Toast.LENGTH_SHORT).show();
      }
    });
  }
    public void onn(View v) {

            mConnectedThread.write("7");    // Send "1" via Bluetooth
            Toast.makeText(getBaseContext(), "fan on", Toast.LENGTH_SHORT).show();


    }
    public void off(View v) {

            mConnectedThread.write("8");    // Send "1" via Bluetooth
            Toast.makeText(getBaseContext(), "fan of", Toast.LENGTH_SHORT).show();



    }
    public void slow(View v) {

        mConnectedThread.write("4");
        Toast.makeText(getBaseContext(), "cradle run at slow speed", Toast.LENGTH_SHORT).show();
    }
    public void medium(View v) {

        mConnectedThread.write("3");
        Toast.makeText(getBaseContext(), "cradle run at medium speed", Toast.LENGTH_SHORT).show();
    }
   
  private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
      
      return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
      //creates secure outgoing connecetion with BT device using UUID
  }
    
  @Override
  public void onStart() {
    super.onStart();
      ValueEventListener postListener = new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              // Get Post object and use the values to update the UI
              Intent intent = new Intent(MainActivity.this, MyService.class);
              if(dataSnapshot.getValue(String.class)!=null)
              {
                  String key = dataSnapshot.getKey();

                  if(key.equals("motor")){
                      String heading=dataSnapshot.getValue(String.class);
                      if(heading.equals("0")){
                          mConnectedThread.write("0");    // Send "0" via Bluetooth
                          Toast.makeText(getBaseContext(), "light bnd", Toast.LENGTH_SHORT).show();
                      }
                      else if(heading.equals("1")){
                          mConnectedThread.write("3");    // Send "0" via Bluetooth
                          Toast.makeText(getBaseContext(), "online swing", Toast.LENGTH_SHORT).show();
                      }
                      else if(heading.equals("fanon")){
                          mConnectedThread.write("7");    // Send "0" via Bluetooth
                          Toast.makeText(getBaseContext(), "Fan on", Toast.LENGTH_SHORT).show();
                      }
                      else if(heading.equals("fanof")){
                          mConnectedThread.write("8");    // Send "0" via Bluetooth
                          Toast.makeText(getBaseContext(), "Fan only on", Toast.LENGTH_SHORT).show();
                      }

                      else if(heading.equals("sing")){
                          startService(intent);
                         // startService(new Intent(MyService.class.getName())); // Send "0" via Bluetooth
                          Toast.makeText(getBaseContext(), "Music on", Toast.LENGTH_SHORT).show();

                      }
                      else if(heading.equals("video")){
                          Intent i = new Intent(MainActivity.this, MainActivity3.class);
                          i.putExtra(EXTRA_DEVICE_ADDRESS, address);
                          startActivity(i);
                          //Intent i = new Intent(getBaseContext(), MainActivity3.class);
                          //startActivity(i);
                          Toast.makeText(getBaseContext(), "video Monitering", Toast.LENGTH_SHORT).show();


                      }

                      else if(heading.equals("stop")){
                         // stopService(new Intent(getBaseContext(), MyService.class));
                          stopService(intent);
                        //  stopService(new Intent(MyService.class.getName()));
                          // Send "0" via Bluetooth
                          Toast.makeText(getBaseContext(), "song stop", Toast.LENGTH_SHORT).show();
                          //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();

                      }
                      else if(heading.equals("n")){
                          Toast.makeText(getBaseContext(), "n", Toast.LENGTH_SHORT).show();
                      }
                      else {
                          // startService(new Intent(getBaseContext(), MyService.class));

                          //startService(new Intent(MyService.class.getName())); // Send "0" via Bluetooth
                          t1.speak(heading, TextToSpeech.QUEUE_FLUSH, null);

                      }





                  }


              }

              // [END_EXCLUDE]
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
              // Getting Post failed, log a message

              // [END_EXCLUDE]
          }
      };
      mHeadingReference.addValueEventListener(postListener);

    //Get MAC address from DeviceListActivity via intent
    Intent intent = getIntent();
    
    //Get the MAC address from the DeviceListActivty via EXTRA
    address = intent.getStringExtra(EXTRA_DEVICE_ADDRESS);

    //create device and set the MAC address
    BluetoothDevice device = btAdapter.getRemoteDevice(address);
     
    try {
        btSocket = createBluetoothSocket(device);
    } catch (IOException e) {
    	Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
    }  
    // Establish the Bluetooth socket connection.
    try 
    {
      btSocket.connect();
    } catch (IOException e) {
      try 
      {
        btSocket.close();
      } catch (IOException e2)
      {
    	//insert code to deal with this 
      }
    } 
    mConnectedThread = new ConnectedThread(btSocket);
    mConnectedThread.start();
    
    //I send a character when resuming.beginning transmission to check device is connected
    //If it is not an exception will be thrown in the write method and finish() will be called
    mConnectedThread.write("x");
  }
  
  @Override
  public void onPause() 
  {
    super.onPause();
    try
    {
    //Don't leave Bluetooth sockets open when leaving activity
      btSocket.close();
    } catch (IOException e2) {
    	//insert code to deal with this 
    }
  }

 //Checks that the Android device Bluetooth is available and prompts to be turned on if off 
  private void checkBTState() {
 
    if(btAdapter==null) { 
    	Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_SHORT).show();
    } else {
      if (btAdapter.isEnabled()) {
      } else {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, 1);
      }
    }
  }
  
  //create new class for connect thread
  private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
      
        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
            	//Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
      
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        
      
        public void run() {
            byte[] buffer = new byte[256];  
            int bytes; 
 
            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget(); 
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
            	//if you cannot write, close the application
            	Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_SHORT).show();
            	finish();
            	
              }
        	}
    	}
}
    
