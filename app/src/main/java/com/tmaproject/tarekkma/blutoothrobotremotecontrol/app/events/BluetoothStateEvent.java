package com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events;

/**
 * Created by tarekkma on 9/8/17.
 */

public class BluetoothStateEvent {

  public static final int STATE_DISCONNECTED = 869;
  public static final int STATE_CONNECTED = 809;
  public static final int STATE_FAILD = 899;

  public final int state;
  public final String name;
  public final String mac;

  public BluetoothStateEvent(int state) {
    this.state = state;
    name = "";
    mac = "";
  }

  public BluetoothStateEvent(int state, String name, String mac) {
    this.state = state;
    this.name = name;
    this.mac = mac;
  }
}
