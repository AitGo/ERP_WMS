package com.rbu.erp_wms.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rbu.erp_wms.R;

/**
 * @创建者 liuyang
 * @创建时间 2018/11/14 11:11
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

    private CheckBox cbDevice;

    public DeviceListAdapter(@NonNull Context context) {
        super(context, 0);
    }

    public void setChecked(boolean isChecked) {
        cbDevice.setChecked(isChecked);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BluetoothDevice device = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        }

        TextView tvDeviceName = (TextView) convertView.findViewById(R.id.tv_device_name);
        cbDevice = (CheckBox) convertView.findViewById(R.id.cb_device);

        tvDeviceName.setText(device.getName());
        return convertView;
    }
}
