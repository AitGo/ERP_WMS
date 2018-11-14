package bluetoothprinter.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.github.promeg.pinyinhelper.Pinyin;
import com.rbu.erp_wms.utils.LogUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


/**
 * 打印工具类
 */
public class PrintUtil {
    private        OutputStreamWriter mWriter       = null;
    private        OutputStream       mOutputStream = null;
    private static String             encoding      = null;//定义编码方式

    public final static int    WIDTH_PIXEL      = 384;
    public final static int    IMAGE_SIZE_WIDTH = 500;//320 865
    public final static int    IMAGE_SIZE_HIGHT = 680;//320 491
    private static      String TAG              = "PrintUtil***";

    // 对齐方式
    public static final int ALIGN_LEFT = 0;   // 靠左
    public static final int ALIGN_CENTER = 1;  // 居中
    public static final int ALIGN_RIGHT = 2;  // 靠右
    //字体大小
    public static final int FONT_NORMAL = 0;  // 正常
    public static final int FONT_MIDDLE = 1;  // 中等
    public static final int FONT_BIG = 2;    // 大
    //加粗模式
    public static final int FONT_BOLD = 1;       // 字体加粗
    public static final int FONT_BOLD_CANCEL = 0;    // 取消加粗

    /**
     * 初始化Pos实例
     *
     * @param encoding 编码
     * @throws IOException
     */
    public PrintUtil(OutputStream outputStream, String encoding) throws IOException {
        this.encoding = encoding;
        mWriter = new OutputStreamWriter(outputStream, encoding);
        mOutputStream = outputStream;
        initPrinter();
    }

    public void print(byte[] bs) throws IOException {
        mOutputStream.write(bs);
    }

    public void printRawBytes(byte[] bytes) throws IOException {
        mOutputStream.write(bytes);
        mOutputStream.flush();
    }

    /**
     * 初始化打印机
     *
     * @throws IOException
     */
    public void initPrinter() throws IOException {
        mWriter.write(0x1B);
        mWriter.write(0x40);
        mWriter.flush();
    }

    /**
     * 打印换行
     *
     * @return length 需要打印的空行数
     * @throws IOException
     */
    public void printLine(int lineNum) throws IOException {
        for (int i = 0; i < lineNum; i++) {
            mWriter.write("\n");
        }
        mWriter.flush();
    }

    /**
     * 打印换行(只换一行)
     *
     * @throws IOException
     */
    public void printLine() throws IOException {
        printLine(1);
    }

    /**
     * 打印空白(一个Tab的位置，约4个汉字)
     *
     * @param length 需要打印空白的长度,
     * @throws IOException
     */
    public void printTabSpace(int length) throws IOException {
        for (int i = 0; i < length; i++) {
            mWriter.write("\t");
        }
        mWriter.flush();
    }

    /**
     * 绝对打印位置
     *
     * @return
     * @throws IOException
     */
    public byte[] setLocation(int offset) throws IOException {
        byte[] bs = new byte[4];
        bs[0] = 0x1B;
        bs[1] = 0x24;
        bs[2] = (byte) (offset % 256);
        bs[3] = (byte) (offset / 256);
        return bs;
    }

    public byte[] getGbk(String stText) throws IOException {
        byte[] returnText = stText.getBytes("GBK"); // 必须放在try内才可以
        return returnText;
    }

    private int getStringPixLength(String str) {
        int pixLength = 0;
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Pinyin.isChinese(c)) {
                pixLength += 24;
            } else {
                pixLength += 12;
            }
        }
        return pixLength;
    }

    public int getOffset(String str) {
        return WIDTH_PIXEL - getStringPixLength(str);
    }

    public void printLargeText(String text) throws IOException {

        mWriter.write(0x1b);
        mWriter.write(0x21);
        mWriter.write(0);

        mWriter.write(text);

        mWriter.write(0x1b);
        mWriter.write(0x21);
        mWriter.write(0);

        mWriter.flush();
    }

    public void printTwoColumn(String title, String content) throws IOException {
        int iNum = 0;
        byte[] byteBuffer = new byte[100];
        byte[] tmp;

        tmp = getGbk(title);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = setLocation(getOffset(content));
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(content);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);

        print(byteBuffer);
    }

    public void printThreeColumn(String left, String middle, String right) throws IOException {
        int iNum = 0;
        byte[] byteBuffer = new byte[200];
        byte[] tmp = new byte[0];

        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(left);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        int pixLength = getStringPixLength(left) % WIDTH_PIXEL;
        if (pixLength > WIDTH_PIXEL / 2 || pixLength == 0) {
            middle = "\n\t\t" + middle;
        }

        tmp = setLocation(192);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(middle);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = setLocation(getOffset(right));
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(right);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);

        print(byteBuffer);
    }

    public void printDashLine() throws IOException {
        printText("----------------------------------------------");
    }
    public void printBitmap(Bitmap bmp) throws IOException {
//        bmp = compressPic(bmp);
        byte[] bmpByteArray = draw2PxPoint(bmp);
        printRawBytes(bmpByteArray);
    }

    public void printSkuBitmap(Bitmap bmp) throws IOException { //width:538,height:358
//        bmp = compressPic(bmp,420,300);
        byte[] bmpByteArray = draw2PxPoint(bmp);
        printRawBytes(bmpByteArray);
    }

    public void printQrCodeBitmap(Bitmap bmp) throws IOException {
        bmp = compressPic(bmp,250,250);
        byte[] bmpByteArray = draw2PxPoint(bmp);
        printRawBytes(bmpByteArray);
    }

    /**
     * 打印二维码
     *
     * @param qrData 二维码的内容
     * @throws IOException
     */
    public void qrCode(String qrData) throws IOException {
        int moduleSize = 8;
        int length = qrData.getBytes(encoding).length;

        //打印二维码矩阵
        mWriter.write(0x1D);// init
        mWriter.write("(k");// adjust height of barcode
        mWriter.write(length + 3); // pl
        mWriter.write(0); // ph
        mWriter.write(49); // cn
        mWriter.write(80); // fn
        mWriter.write(48); //
        mWriter.write(qrData);

        mWriter.write(0x1D);
        mWriter.write("(k");
        mWriter.write(3);
        mWriter.write(0);
        mWriter.write(49);
        mWriter.write(69);
        mWriter.write(48);

        mWriter.write(0x1D);
        mWriter.write("(k");
        mWriter.write(3);
        mWriter.write(0);
        mWriter.write(49);
        mWriter.write(67);
        mWriter.write(moduleSize);

        mWriter.write(0x1D);
        mWriter.write("(k");
        mWriter.write(3); // pl
        mWriter.write(0); // ph
        mWriter.write(49); // cn
        mWriter.write(81); // fn
        mWriter.write(48); // m
        mWriter.flush();

    }

    /**
     * 进纸并全部切割
     */
    public void feedAndCut() throws IOException {
        mWriter.write(0x1D);
        mWriter.write(86);
        mWriter.write(65);
        //    writer.write(0);
        //切纸前走纸多少
        mWriter.write(3000); //
        mWriter.flush();

        //另外一种切纸的方式
//            byte[] bytes = {29, 86, 0};
//            socketOut.write(bytes);
    }

    /**
     * 切纸
     * @return
     */
    public static String getCutPaperCmd() {
        // 走纸并切纸，最后一个参数控制走纸的长度
        byte[] data = {(byte) 0x1d, (byte) 0x56, (byte) 0x42, (byte) 0x15};

        return new String(data);
    }

    /**
     * 打印文字
     *
     * @param text
     * @throws IOException
     */
    public void printText(String text) throws IOException {
        mWriter.write(text);
        mWriter.flush();
    }

    /**
     * 对齐0:左对齐，1：居中，2：右对齐
     */
    public void printAlignment(int alignment) throws IOException {
        mWriter.write(0x1b);
        mWriter.write(0x61);
        mWriter.write(alignment);
    }

    /**
     * 字体大小
     */
    public void printFontSize(int fontSize) throws IOException {
        mWriter.write(0x1d);
        mWriter.write(0x21);
        mWriter.write(fontSize);
    }

    /**
     * 加粗模式
     */
    public void printFontBold(int fontBold) throws IOException {
        mWriter.write(0x1b);
        mWriter.write(0x45);
        mWriter.write(fontBold);
    }

    /**
     * 图片二值化，黑色是1，白色是0
     *
     * @param x   横坐标
     * @param y   纵坐标
     * @param bit 位图
     * @return
     */
    private static byte px2Byte(int x, int y, Bitmap bit) {
        if (x < bit.getWidth() && y < bit.getHeight()) {
            byte b;
            int pixel = bit.getPixel(x, y);
            int red = (pixel & 0x00ff0000) >> 16; // 取高两位
            int green = (pixel & 0x0000ff00) >> 8; // 取中两位
            int blue = pixel & 0x000000ff; // 取低两位
            int gray = RGB2Gray(red, green, blue);
            if (gray < 128) {
                b = 1;
            } else {
                b = 0;
            }
            return b;
        }
        return 0;
    }

    /**
     * 把一张Bitmap图片转化为打印机可以打印的字节流
     *
     * @return
     */
    public static byte[] draw2PxPoint(Bitmap bit) {
        //用来存储转换后的 bitmap 数据。为什么要再加1000，这是为了应对当图片高度无法
        //整除24时的情况。比如bitmap 分辨率为 240 * 250，占用 7500 byte，
        //但是实际上要存储11行数据，每一行需要 24 * 240 / 8 =720byte 的空间。再加上一些指令存储的开销，
        //所以多申请 1000byte 的空间是稳妥的，不然运行时会抛出数组访问越界的异常。
        int size = bit.getWidth() * bit.getHeight() / 8 + 9000;//
        byte[] data = new byte[size];
        int k = 0;
        //设置行距为0的指令
        data[k++] = 0x1B;
        data[k++] = 0x33;
        data[k++] = 0x00;
        // 居中打印
        data[k++] = 0x1B;
        data[k++] = 0x61;
        data[k++] = 1;//1居中，0居左
        // 逐行打印
        for (int j = 0; j < bit.getHeight() / 24f; j++) {
            //打印图片的指令
            data[k++] = 0x1B;
            data[k++] = 0x2A;
            data[k++] = 33;
            data[k++] = (byte) (bit.getWidth() % 256); //nL
            data[k++] = (byte) (bit.getWidth() / 256); //nH
            //对于每一行，逐列打印
            for (int i = 0; i < bit.getWidth(); i++) {
                //每一列24个像素点，分为3个字节存储
                for (int m = 0; m < 3; m++) {
                    //每个字节表示8个像素点，0表示白色，1表示黑色
                    for (int n = 0; n < 8; n++) {
                        byte b = px2Byte(i, j * 24 + m * 8 + n, bit);
                        data[k] += data[k] + b;
                    }
                    k++;
                }
            }
            data[k++] = 10;//换行
        }
        // 恢复默认行距
        data[k++] = 0x1B;
        data[k++] = 0x32;

        return data;
    }


    /**
     * 图片灰度的转化
     */
    private static int RGB2Gray(int r, int g, int b) {
        int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b); // 灰度转化公式
        return gray;
    }

    /**
     * 对图片进行压缩（去除透明度）
     *
     * @param bitmapOrg
     */
    public static Bitmap compressPic(Bitmap bitmapOrg, int newWidth, int newHeight) {
        // 获取这个图片的宽和高
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        LogUtils.e(TAG+"scaleBitmap***width:"+width+",height:"+height);
        // 定义预转换成的图片的宽度和高度
        // 计算缩放比例
        Bitmap targetBmp = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas targetCanvas = new Canvas(targetBmp);
        targetCanvas.drawColor(0xffffffff);
        targetCanvas.drawBitmap(bitmapOrg, new Rect(0, 0, width, height), new Rect(0, 0, newWidth, newHeight), null);

        return targetBmp;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap) {
        // 获取这个图片的宽和高 width:865,height:491
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        LogUtils.e(TAG+"scaleBitmap***width:"+width+",height:"+height);
        // 定义预转换成的图片的宽度和高度
        int newWidth = IMAGE_SIZE_WIDTH;
        int newHeight = IMAGE_SIZE_HIGHT;
        // 计算缩放率，新尺寸除原始尺寸
        int scaleWidth =  width / 10;
        int scaleHeight = height /10;
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        // 旋转图片 动作
        // matrix.postRotate(45);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return resizedBitmap;
    }

    public void printBitmap(String qrCode) throws IOException {
        byte[] bmpByteArray = getQrCodeCmd(qrCode);
        printRawBytes(bmpByteArray);
    }

    /**
     * 打印二维码
     * @param qrCode
     * @return
     */
    public static byte[] getQrCodeCmd(String qrCode) {
        byte[] data;
        int store_len = qrCode.length() + 3;
        byte store_pL = (byte) (store_len % 256);
        byte store_pH = (byte) (store_len / 256);

        // QR Code: Select the model
        //       Hex   1D   28   6B   04   00   31   41   n1(x32)   n2(x00) - size of model
        // set n1 [49 x31, model 1] [50 x32, model 2] [51 x33, micro qr code]
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=140
        byte[] modelQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x04, (byte)0x00, (byte)0x31, (byte)0x41, (byte)0x32, (byte)0x00};

        // QR Code: Set the size of module
        // Hex   1D   28   6B   03   00   31   43   n
        // n depends on the printer
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=141
        byte[] sizeQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x03, (byte)0x00, (byte)0x31, (byte)0x43, (byte)0x08};

        //     Hex   1D   28   6B   03   00   31   45   n
        // Set n for error correction [48 x30 -> 7%] [49 x31-> 15%] [50 x32 -> 25%] [51 x33 -> 30%]
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=142
        byte[] errorQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x03, (byte)0x00, (byte)0x31, (byte)0x45, (byte)0x31};

        // QR Code: Store the data in the symbol storage area
        // Hex   1D   28   6B   pL   pH   31   50   30   d1...dk
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=143
        //            1D     28     6B     pL     pH cn(49->x31) fn(80->x50) m(48->x30) d1…dk
        byte[] storeQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, store_pL, store_pH, (byte)0x31, (byte)0x50, (byte)0x30};

        // QR Code: Print the symbol data in the symbol storage area
        // Hex   1D   28   6B   03   00   31   51   m
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=144
        byte[] printQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x03, (byte)0x00, (byte)0x31, (byte)0x51, (byte)0x30};

        data = byteMerger(modelQR, sizeQR);
        data = byteMerger(data, errorQR);
        data = byteMerger(data, storeQR);
        data = byteMerger(data, qrCode.getBytes());
        data = byteMerger(data, printQR);

        return data;
    }

    /**
     * 打印条码
     * @param barcode
     * @return
     */
    public static String getBarcodeCmd(String barcode) {
        // 打印 Code-128 条码时需要使用字符集前缀
        // "{A" 表示大写字母
        // "{B" 表示所有字母，数字，符号
        // "{C" 表示数字，可以表示 00 - 99 的范围

        byte[] data;
        String btEncode;
        if (barcode.length() < 18) {
            // 字符长度小于15的时候直接输出字符串
            btEncode = "{B" + barcode;
        } else {
            // 否则做一点优化
            int startPos = 0;
            btEncode = "{B";
            for (int i = 0; i < barcode.length(); i++) {
                char curChar = barcode.charAt(i);
                if (curChar < 48 || curChar > 57 || i == (barcode.length() - 1)) {
                    // 如果是非数字或者是最后一个字符
                    if (i - startPos >= 10) {
                        if (startPos == 0) {
                            btEncode = "";
                        }

                        btEncode += "{C";
                        boolean isFirst = true;
                        int numCode = 0;
                        for (int j = startPos; j < i; j++) {
                            if (isFirst) { // 处理第一位
                                numCode = (barcode.charAt(j) - 48) * 10;
                                isFirst = false;
                            } else { // 处理第二位
                                numCode += (barcode.charAt(j) - 48);
                                btEncode += (char) numCode;
                                isFirst = true;
                            }
                        }

                        btEncode += "{B";

                        if (!isFirst) {
                            startPos = i - 1;
                        } else {
                            startPos = i;
                        }
                    }

                    for (int k = startPos; k <= i; k++) {
                        btEncode += barcode.charAt(k);
                    }
                    startPos = i + 1;
                }

            }
        }


        // 设置 HRI 的位置，02 表示下方
        byte[] hriPosition = {(byte) 0x1d, (byte) 0x48, (byte) 0x02};
        // 最后一个参数表示宽度 取值范围 1-6 如果条码超长则无法打印
        byte[] width = {(byte) 0x1d, (byte) 0x77, (byte) 0x02};
        byte[] height = {(byte) 0x1d, (byte) 0x68, (byte) 0xfe};
        // 最后两个参数 73 ： CODE 128 || 编码的长度
        byte[] barcodeType = {(byte) 0x1d, (byte) 0x6b, (byte) 73, (byte) btEncode.length()};
        byte[] print = {(byte) 10, (byte) 0};

        data = PrintUtil.byteMerger(hriPosition, width);
        data = PrintUtil.byteMerger(data, height);
        data = PrintUtil.byteMerger(data, barcodeType);
        data = PrintUtil.byteMerger(data, btEncode.getBytes());
        data = PrintUtil.byteMerger(data, print);

        return new String(data);
    }

    /**
     * 对齐方式
     * @param alignMode
     * @return
     */
    public static String getAlignCmd(int alignMode) {
        byte[] data = {(byte) 0x1b, (byte) 0x61, (byte) 0x0};
        if (alignMode == ALIGN_LEFT) {
            data[2] = (byte) 0x00;
        } else if (alignMode == ALIGN_CENTER) {
            data[2] = (byte) 0x01;
        } else if (alignMode == ALIGN_RIGHT) {
            data[2] = (byte) 0x02;
        }

        return new String(data);
    }

    /**
     * 字体大小
     * @param fontSize
     * @return
     */
    public static String getFontSizeCmd(int fontSize) {
        byte[] data = {(byte) 0x1d, (byte) 0x21, (byte) 0x0};
        if (fontSize == FONT_NORMAL) {
            data[2] = (byte) 0x00;
        } else if (fontSize == FONT_MIDDLE) {
            data[2] = (byte) 0x01;
        } else if (fontSize == FONT_BIG) {
            data[2] = (byte) 0x11;
        }

        return new String(data);
    }

    /**
     * 加粗模式
     * @param fontBold
     * @return
     */
    public static String getFontBoldCmd(int fontBold) {
        byte[] data = {(byte) 0x1b, (byte) 0x45, (byte) 0x0};

        if (fontBold == FONT_BOLD) {
            data[2] = (byte) 0x01;
        } else if (fontBold == FONT_BOLD_CANCEL) {
            data[2] = (byte) 0x00;
        }

        return new String(data);
    }

    /**
     * 打开钱箱
     * @return
     */
    public static String getOpenDrawerCmd() {
        byte[] data = new byte[4];
        data[0] = 0x10;
        data[1] = 0x14;
        data[2] = 0x00;
        data[3] = 0x00;

        return new String(data);
    }

    /**
     * 字符串转字节数组
     * @param str
     * @return
     */
    public static byte[] stringToBytes(String str) {
        byte[] data = null;

        try {
            byte[] strBytes = str.getBytes("utf-8");

            data = (new String(strBytes, "utf-8")).getBytes("gbk");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return data;
    }

    /**
     * 字节数组合并
     * @param bytesA
     * @param bytesB
     * @return
     */
    public static byte[] byteMerger(byte[] bytesA, byte[] bytesB) {
        byte[] bytes = new byte[bytesA.length + bytesB.length];
        System.arraycopy(bytesA, 0, bytes, 0, bytesA.length);
        System.arraycopy(bytesB, 0, bytes, bytesA.length, bytesB.length);
        return bytes;
    }


}