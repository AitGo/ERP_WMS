package bluetoothprinter.ui.pos_voucher;

import android.graphics.Bitmap;

import bluetoothprinter.printer.JQPrinter;

public class JQPrinterFirm implements PrinterCommunication 
{
	private JQPrinter printer = new JQPrinter();
	
	@Override
	public void PrinterConnect(String sPrinterAddr) {
		printer.open(JQPrinter.PRINTER_TYPE.VMP02_P);
	}
	
	@Override
	public void PrinterDisConnect() {
		printer.close();		
	}
	
	@Override
	public void PrinterWrite() {
	}
	
	@Override
	public void PrinterWrap() {

	}
	@Override
	public boolean PrinterConnectionStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void PrinterInit() {
		printer.esc.wakeUp();
	}

	@Override
	public void PrinterText(String sPrinterStr) {
		printer.esc.text.drawOut(sPrinterStr);
	}

	@Override
	public void PrinterBitmap(Bitmap bmp, int PaperWidth, int nGrayThreshold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PrinterFontSize(int iFontSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PrinterFont(int iFont) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PrinterAlignment(int iAlign) {
		// TODO Auto-generated method stub
		
	}	
}

