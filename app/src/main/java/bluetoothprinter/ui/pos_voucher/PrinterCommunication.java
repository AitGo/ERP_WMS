package bluetoothprinter.ui.pos_voucher;

import android.graphics.Bitmap;

public interface  PrinterCommunication
{
	void PrinterConnect(String sPrinterAddr);
	void PrinterWrite();
	void PrinterDisConnect();
	boolean PrinterConnectionStatus();
	void PrinterInit();
	void PrinterText(String sPrinterStr);
	void PrinterWrap();
	void PrinterBitmap(Bitmap bmp, int PaperWidth, int nGrayThreshold);
	void PrinterFontSize(int iFontSize);
	void PrinterFont(int iFont);
	void PrinterAlignment(int iAlign);
}
