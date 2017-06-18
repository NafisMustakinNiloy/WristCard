package com.example.nafismustakin.wristcard.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;


/**
 * Created by Nafis Mustakin on 18-Jun-17.
 */

public class BluetoothReceiver {

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket socket;
    BluetoothDevice bluetoothDevice;
    InputStream inputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    String deviceName = "HC-05";

    private static final String TAG = "BluetoothReceiver";

    void findBlueTooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Log.d(TAG, "No bluetooth adapter availabe");
        }
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size()>0){
            for(BluetoothDevice device:pairedDevices){
                if(device.getName().equals(deviceName)){
                    bluetoothDevice = device;
                    break;
                }
            }
        }
    }

    void openBlueTooth() throws IOException{
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        socket =bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        socket.connect();
        inputStream = socket.getInputStream();

        startListeningForData();
    }

    void startListeningForData(){
        final Handler handler = new Handler();
        final byte delimiter = (byte)'\n';

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        workerThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while(!Thread.currentThread().isInterrupted() && !stopWorker){
                            try {
                                int bytesAvailable = inputStream.available();
                                if(bytesAvailable>0){
                                    byte[] packet = new byte[bytesAvailable];
                                    inputStream.read(packet);
                                    for (int i = 0; i<bytesAvailable;i++){
                                        byte b = packet[i];
                                        if(b == delimiter){
                                            byte[] encodedBytes = new byte[readBufferPosition];
                                            System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                            final String data = new String(encodedBytes, "US-ASCII");
                                            readBufferPosition = 0;
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    System.out.println(data);
                                                }
                                            });
                                        }
                                        else{
                                            readBuffer[readBufferPosition++] = b;
                                        }
                                    }

                                }
                            }catch (IOException e) {
                                e.printStackTrace();
                                stopWorker = true;
                            }
                        }
                    }
                }
        );

        workerThread.start();
    }

    void closeBlueTooth() throws IOException{
        stopWorker = true;
        inputStream.close();
        socket.close();
    }
}
