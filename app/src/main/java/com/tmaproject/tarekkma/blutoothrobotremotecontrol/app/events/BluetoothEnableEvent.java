package com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events;

/**
 * Created by tarekkma on 9/8/17.
 */

public class BluetoothEnableEvent {
  private boolean enabled;

  public BluetoothEnableEvent(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
