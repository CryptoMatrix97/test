package smart.billard;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    RemoteController remoteController;
    private TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          test=(TextView)findViewById(R.id.test);
        remoteController=new RemoteController();
    }

    boolean is_remote_on=false;
  private  BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public void onSwitch(View v) throws ExecutionException, InterruptedException {
        test.setText("TE");
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        ConnectThread k = null;
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (final BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();

                String deviceHardwareAddress = device.getAddress(); // MAC address
                test.setText(test.getText()+"\n"+deviceName +" "+deviceHardwareAddress);
                if(deviceHardwareAddress.equals("F8:84:F2:B9:B6:52")){
     k=new ConnectThread(test,device);
                }

            }
           // a.getConnected().write(new String("TEEEEEST").getBytes());

            mBluetoothAdapter.cancelDiscovery();
        }
        if(!is_remote_on){
            remoteController.startCollection();
            is_remote_on=true;
        }
        else{
            remoteController.stopCollection();
            is_remote_on=false;
        }
    }


}
