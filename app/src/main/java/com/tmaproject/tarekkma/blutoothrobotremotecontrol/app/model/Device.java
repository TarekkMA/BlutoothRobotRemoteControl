package com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.model;

import android.bluetooth.BluetoothDevice;

/**
 * Created by tarekkma on 9/7/17.
 */

public class Device {
  private final String name;
  private final String macAddress;
  private final BluetoothDevice device;

  public Device(BluetoothDevice device) {
    this.device = device;
    this.name = device.getName();
    this.macAddress = device.getAddress();
  }

  public String getName() {
    return name;
  }

  public String getMacAddress() {
    return macAddress;
  }

  public BluetoothDevice getDevice() {
    return device;
  }
}
