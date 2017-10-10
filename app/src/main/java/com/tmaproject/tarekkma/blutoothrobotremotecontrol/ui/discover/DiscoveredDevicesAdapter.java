package com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.discover;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tarekkma on 9/8/17.
 */

public class DiscoveredDevicesAdapter extends RecyclerView.Adapter<DiscoveredDevicesAdapter.VH> {

  List<BluetoothDevice> deviceList = new ArrayList<>();
  Set<BluetoothDevice> uniqueList = new HashSet<>();
  private OnDeviceClickListner listner;

  public DiscoveredDevicesAdapter(OnDeviceClickListner listner) {
    this.listner = listner;
  }

  public void addDevice(BluetoothDevice device){
    if(uniqueList.add(device)){
      int posAdded = deviceList.size();
      deviceList.add(device);
      notifyItemInserted(posAdded);
    }
  }

  @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device,parent,false));
  }

  @Override public void onBindViewHolder(VH holder, int position) {
    final BluetoothDevice device = deviceList.get(position);
    holder.macTextView.setText(device.getAddress());
    holder.nameTextView.setText(device.getName());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listner.onClick(device);
      }
    });
  }

  @Override public int getItemCount() {
    return deviceList.size();
  }

  public class VH extends RecyclerView.ViewHolder{
    public TextView nameTextView,macTextView;
    public VH(View itemView) {
      super(itemView);
      nameTextView = itemView.findViewById(R.id.deviceName);
      macTextView = itemView.findViewById(R.id.deviceMac);
    }
  }
}
