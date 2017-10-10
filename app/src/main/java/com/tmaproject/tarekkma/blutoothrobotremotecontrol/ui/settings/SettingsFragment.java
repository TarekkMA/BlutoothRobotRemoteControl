package com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.R;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothEnableEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothNotSupportedEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothRequestConnectionEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothRequestEnableEvent;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.BluetoothStateEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

  BluetoothSPP bt;

  @BindView(R.id.settings_error) TextView errorTextView;
  @BindView(R.id.settings_name) TextView nameTextView;
  @BindView(R.id.settings_mac) TextView macTextView;
  @BindView(R.id.connection_status) TextView connectionStatusTextView;
  @BindView(R.id.settings_enable_status) TextView enableTextView;
  @BindView(R.id.settings_connectButton) Button connectButton;
  @BindView(R.id.settings_enable_button) Button enableButton;

  public SettingsFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_settings, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  public void showError(String message) {
    errorTextView.setVisibility(View.VISIBLE);
    errorTextView.setText(message);
  }

  public void hideError() {
    errorTextView.setVisibility(View.GONE);
  }

  @OnClick(R.id.settings_connectButton) void connectClicked() {
    EventBus.getDefault().post(new BluetoothRequestConnectionEvent());
  }

  @OnClick(R.id.settings_enable_button) void enableClicked() {
    EventBus.getDefault().post(new BluetoothRequestEnableEvent());
  }

  @Override public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);

  }

  @Override public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }



  @Subscribe(sticky = true) public void onBluetoothNotSupported(BluetoothNotSupportedEvent event) {
    showError("Bluetooth isn't supported on this device");
  }

  @Subscribe(sticky = true) public void onBluetoothEnableEvent(BluetoothEnableEvent event) {
    String text = "Bluetooth " + (event.isEnabled() ? "is" : "isn't") + " enabled !";
    enableTextView.setText(text);
  }

  @Subscribe(sticky = true) public void onBluetoothStateChanged(BluetoothStateEvent event) {
    switch (event.state) {
      case BluetoothState.STATE_CONNECTING:
        connectionStatusTextView.setText("Connecting");
        break;
      case BluetoothState.STATE_LISTEN:
        connectionStatusTextView.setText("Waiting for connections");
        break;
      case BluetoothState.STATE_NONE:
        connectionStatusTextView.setText("No Connection");
        break;
      case BluetoothStateEvent.STATE_CONNECTED:
        connectionStatusTextView.setText("Connected");
        nameTextView.setText(event.name);
        macTextView.setText(event.mac);
        break;
      case BluetoothStateEvent.STATE_DISCONNECTED:
        connectionStatusTextView.setText("Disconnected");
        break;
      case BluetoothStateEvent.STATE_FAILD:
        connectionStatusTextView.setText("Connection Faild");
        break;
      default:
        connectionStatusTextView.setText(event.state + " is unknown state");
    }
  }
}


