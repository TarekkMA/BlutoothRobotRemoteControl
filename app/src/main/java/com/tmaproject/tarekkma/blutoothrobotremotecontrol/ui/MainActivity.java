package com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.R;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.Instruction;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothEnableEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothNotSupportedEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothRequestConnectionEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothRequestEnableEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothStateEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.InstructionEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.arm.ArmFragment;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.discover.DiscoverDeviceActivity;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.discover.DiscoveredDevicesAdapter;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.movement.MovementFragment;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.settings.SettingsFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static app.akexorcist.bluetotohspp.library.BluetoothState.REQUEST_ENABLE_BT;

public class MainActivity extends AppCompatActivity {

  private BluetoothSPP bt = new BluetoothSPP(this);

  private DiscoveredDevicesAdapter adapter;

  private FrameLayout contentView;

  private SettingsFragment settingsFragment;
  private MovementFragment movementFragment;
  private ArmFragment armFragment;
  private UtilsFragment utilsFragment;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.navigation_movement:
              replaceFragment(movementFragment);
              return true;
            case R.id.navigation_arm:
              replaceFragment(armFragment);
              return true;
            case R.id.navigation_settings:
              replaceFragment(settingsFragment);
              return true;
            case R.id.navigation_info:

              return true;
            case R.id.navigation_utils:
              replaceFragment(utilsFragment);
              return true;
          }
          return false;
        }
      };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    contentView = (FrameLayout) findViewById(R.id.content);

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    settingsFragment = new SettingsFragment();
    movementFragment = new MovementFragment();
    armFragment = new ArmFragment();
    utilsFragment = new UtilsFragment();

    navigation.setSelectedItemId(R.id.navigation_settings);

    bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
      @Override public void onDeviceConnected(String name, String address) {
        EventBus.getDefault()
            .postSticky(
                new BluetoothStateEvent(BluetoothStateEvent.STATE_CONNECTED, name, address));
      }

      @Override public void onDeviceDisconnected() {
        EventBus.getDefault()
            .postSticky(new BluetoothStateEvent(BluetoothStateEvent.STATE_DISCONNECTED));
      }

      @Override public void onDeviceConnectionFailed() {
        EventBus.getDefault().postSticky(new BluetoothStateEvent(BluetoothStateEvent.STATE_FAILD));
      }
    });

    bt.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener() {
      @Override public void onServiceStateChanged(int state) {
        //we are making our own connected
        if (state != BluetoothState.STATE_CONNECTED) {
          EventBus.getDefault().postSticky(new BluetoothStateEvent(state));
        }
      }
    });
  }

  private void replaceFragment(Fragment fragment) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.content, fragment);
    transaction.commit();
  }

  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
    if (!bt.isBluetoothAvailable()) {
      EventBus.getDefault().postSticky(new BluetoothNotSupportedEvent());
    } else if (!bt.isBluetoothEnabled()) {
      EventBus.getDefault().postSticky(new BluetoothEnableEvent(false));
    } else {

    }
  }

  @Override public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
      if (resultCode == Activity.RESULT_OK) {
        bt.setupService();
        bt.startService(BluetoothState.DEVICE_OTHER);
        if (data != null) bt.connect(data);
      }
    } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
      if (resultCode == Activity.RESULT_OK) {
        bt.setupService();
        bt.startService(BluetoothState.DEVICE_OTHER);
        EventBus.getDefault().postSticky(new BluetoothEnableEvent(true));
      } else {
        Toast.makeText(this, "Bluetooth is need run this application", Toast.LENGTH_LONG).show();
      }
    }
  }

  @Subscribe public void onRequestConnection(BluetoothRequestConnectionEvent event) {
    //bt.setupService();
    //bt.startService(BluetoothState.DEVICE_ANDROID);
    //Intent intent = new Intent(getApplicationContext(), DeviceList.class);
    //startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
    startActivityForResult(new Intent(this, DiscoverDeviceActivity.class),
        BluetoothState.REQUEST_CONNECT_DEVICE);
  }

  @Subscribe public void onRequestEbable(BluetoothRequestEnableEvent event) {
    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
  }

  @Subscribe public void onInstructionEvent(InstructionEvent instructionEvent) {
    bt.send(new byte[]{instructionEvent.getInstruction().getCommand()}, true);
  }
}
