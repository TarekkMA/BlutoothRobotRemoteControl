package com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.discover;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.R;

import static android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED;
import static android.bluetooth.BluetoothAdapter.EXTRA_STATE;
import static android.bluetooth.BluetoothAdapter.STATE_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_ON;
import static android.bluetooth.BluetoothAdapter.STATE_TURNING_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_TURNING_ON;
import static android.bluetooth.BluetoothDevice.ACTION_NAME_CHANGED;

public class DiscoverDeviceActivity extends AppCompatActivity implements OnDeviceClickListner {

  private static final String TAG = "DiscoverDeviceActivity";
  private static final int REQUEST_COARSE_LOCATION = 676;

  private BluetoothDevice selectedDevice = null;

  private BluetoothAdapter bluetoothAdapter = null;

  boolean supported = false;

  private DiscoveredDevicesAdapter adapter = null;

  @BindView(R.id.list) RecyclerView deviceRecycler;
  @BindView(R.id.status) TextView statusTextView;
  @BindView(R.id.loading) ProgressBar progressBar;

  private final BroadcastReceiver bluetoothStateReceiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (ACTION_STATE_CHANGED.equals(action)) {

        switch (intent.getIntExtra(EXTRA_STATE, -1)) {
          case STATE_ON:
            setStatus("Bluetooth Enabled");
            bluetoothIsEnabled();
            break;
          case STATE_OFF:
            setStatus("Bluetooth Disabled");
            bluetoothIsDisabled();
            break;
          case STATE_TURNING_ON:
            setStatus("Bluetooth is turing ON");
            break;
          case STATE_TURNING_OFF:
            setStatus("Bluetooth is turing OFF");
            break;
        }
      }
    }
  };

  private final BroadcastReceiver bluetoothDiscoveryReceiver = new BroadcastReceiver() {
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (BluetoothDevice.ACTION_FOUND.equals(action) || ACTION_NAME_CHANGED.equals(action)) {
        // Discovery has found a device. Get the BluetoothDevice
        // object and its info from the Intent.
        Log.d(TAG, "onReceive: ");
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        adapter.addDevice(device);
      }
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_discover_device);
    ButterKnife.bind(this);

    deviceRecycler.setLayoutManager(new LinearLayoutManager(this));
    adapter = new DiscoveredDevicesAdapter(this);
    deviceRecycler.setAdapter(adapter);

    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    supported = bluetoothAdapter != null;
    if (supported) {
      if (bluetoothAdapter.isEnabled()) {
        bluetoothIsEnabled();
      } else {
        setStatus("Bluetooth is disabled please enable it.");
      }
    } else {
      setStatus("Bluetooth isn't supported on this device");
    }

    IntentFilter filter = new IntentFilter(ACTION_STATE_CHANGED);
    registerReceiver(bluetoothStateReceiver, filter);
  }

  @Override public void onRequestPermissionsResult(int requestCode, String permissions[],
      int[] grantResults) {
    switch (requestCode) {
      case REQUEST_COARSE_LOCATION: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          startScanning();
        } else {
          Toast.makeText(this,
              "Scan results may be less accurate !For more accuracy please enable the permission",
              Toast.LENGTH_LONG).show();
          startScanning();
        }
        break;
      }
    }
  }

  @Override public void onClick(BluetoothDevice device) {
    selectedDevice = device;
    setResultAndFinish();
  }

  private void bluetoothIsDisabled() {
    unregisterReceiver(bluetoothDiscoveryReceiver);
    unregisterReceiver(bluetoothStateReceiver);
  }

  private void bluetoothIsEnabled() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

      setStatus("You have to grant the application COARSE_LOCATION to scan !");

      ActivityCompat.requestPermissions(this,
          new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_COARSE_LOCATION);
    } else {
      startScanning();
    }
  }

  private void startScanning() {
    setStatus("Searching for bluetooth devices");
    if (!bluetoothAdapter.isDiscovering()) {
      bluetoothAdapter.startDiscovery();
    }
    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    filter.addAction(ACTION_NAME_CHANGED);
    registerReceiver(bluetoothDiscoveryReceiver, filter);
  }

  private void setLoading(boolean isShown) {
    progressBar.setVisibility(isShown ? View.VISIBLE : View.INVISIBLE);
  }

  private void setStatus(String status) {
    statusTextView.setText(status);
  }

  private void setResultAndFinish() {
    Intent intent = new Intent();
    if (selectedDevice != null) {
      intent.putExtra(BluetoothState.EXTRA_DEVICE_ADDRESS, selectedDevice.getAddress());
    }
    setResult(RESULT_OK, intent);
    finish();
  }

  @Override public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    bluetoothAdapter.cancelDiscovery();
    unregisterReceiver(bluetoothDiscoveryReceiver);
    unregisterReceiver(bluetoothStateReceiver);
  }
}
