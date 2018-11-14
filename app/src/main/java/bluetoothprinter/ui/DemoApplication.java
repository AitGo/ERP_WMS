package bluetoothprinter.ui;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;

import bluetoothprinter.printer.JQPrinter;

public class DemoApplication extends Application {
	public JQPrinter        printer   = null;
	public BluetoothAdapter btAdapter = null;
	@Override
    public void onCreate() {
            super.onCreate();            
    }
}
