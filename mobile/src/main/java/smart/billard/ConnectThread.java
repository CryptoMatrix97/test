package smart.billard;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by moham_000 on 2/19/2017.
 */
class ConnectThread extends Thread {
    private static final String TAG = "";
    private final BluetoothServerSocket  mmServerSocket;
private BluetoothSocket f;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final TextView mTextView;
    public ConnectThread(TextView mTextView, BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.

        this.mTextView=mTextView;
        mTextView.setText("WAITING FOR THE PHONE");
        BluetoothServerSocket tmp = null;
        while(tmp==null) {
            try {
                // MY_UUID is the app's UUID string, also used by the client code.
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(device.getName(), UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            } catch (IOException e) {
                Log.e(TAG, "Socket's listen() method failed", e);
            }
        }
        mmServerSocket = tmp;
        run();

    }
    public BluetoothSocket  getsocket(){return f;}

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread
                mTextView.setText("WORKS");
                break;
            }
        }
    }



    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}