package bluetoothprinter.utils;

import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;



import com.rbu.erp_wms.R;
import com.rbu.erp_wms.utils.LogUtils;
import com.rbu.erp_wms.utils.PicassoUtil;
import com.rbu.erp_wms.utils.TransformUtil;
import com.rbu.erp_wms.utils.UiUtil;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 打印模板
 */
public class PrintContract {
    private static String TAG = "PrintContract***";

    //打印调拨箱唛
//    public static void printTest1(CartonResponse.ParamsBean.DataBean dataBean, BluetoothSocket bluetoothSocket) {
//        LogUtils.e(TAG + "printTest1***dataBean:" + dataBean.toString());
//        //初始化数据
//        try {
//            PrintUtil pUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK");
//            //            //打印二维码
//            //            String url = dataBean.getQrCodeURl();
//            //            if (!TextUtils.isEmpty(url)) {
//            //                pUtil.printBitmap(dataBean.getQrCodeURl());
//            //            } else {
//            //                pUtil.printBitmap(BitmapFactory.decodeResource(UiUtil.getContext().getResources(), R.drawable.def_img));
//            //            }
//
//            //打印二维码右边部分
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD);
//            pUtil.printText("  报关 ：" + TransformUtil.isCustomsTostring(dataBean.getIsCustoms()));
//            pUtil.printLine();
//            pUtil.printText("  箱号 ：" + dataBean.getCartonIndex() + "   " + dataBean.getPno());
//            pUtil.printLine();
//            pUtil.printText("  编号 ：" + dataBean.getCartonNo());
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD_CANCEL);
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printDashLine();
//            pUtil.printLine();
//
//            //打印仓库
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printText("   始发仓");
//            pUtil.printTabSpace(1);
//            pUtil.printText("   中转仓   ");
//            pUtil.printTabSpace(1);
//            pUtil.printText("   目的仓   ");
//            pUtil.printLine();
//            String whId = dataBean.getWhId();
//            String transitWhId = dataBean.getTransitWhId();
//            String toWhId = dataBean.getToWhId();
//            pUtil.printFontSize(PrintUtil.FONT_MIDDLE);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD);
//            pUtil.printText(whId != null ? "   " + whId : "   ------");
//            pUtil.printTabSpace(1);
//            pUtil.printText(transitWhId != null ? "   " + transitWhId + "   " : "   ------   ");
//            pUtil.printTabSpace(1);
//            pUtil.printText(toWhId != null ? "   " + toWhId + "   " : "   ------   ");
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD_CANCEL);
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printDashLine();
//
//            pUtil.printLine();
//            pUtil.printText("  重量 ：" + dataBean.getMaxWeight() + "kg");
//            pUtil.printLine();
//            double length = dataBean.getMaxLength();
//            double width = dataBean.getMaxWidth();
//            double height = dataBean.getMaxHeight();
//            double volume = length * width * height;
//            pUtil.printText("  体积 ：" + length + "*" + width + "*" + height + "cm=" + volume + "cm3");
//            pUtil.printLine();
//            pUtil.printText("  物品 ：" + dataBean.getProductCategoryCount() + "种/" + dataBean.getProductCount() + "PCS");
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printDashLine();
//            pUtil.printLine();
//
//            List<CartonResponse.ParamsBean.DataBean.InfosBean> infos = dataBean.getInfos();
//            if (infos.size() > 0) {
//                for (int i = 0; i < 2; i++) {
//
//                    if(infos.size()>2){
//                        if(i == 1){
//                            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//                            pUtil.printTabSpace(2);
//                            pUtil.printText("  sku ：" + infos.get(i).getSkuNo());
//                            pUtil.printLine();
//                            pUtil.printText("  物品名称 ：" + infos.get(i).getProductName());
//                            pUtil.printLine();
//                            pUtil.printText("  备货计划 ：" + infos.get(i).getStockNo());
//                            pUtil.printLine();
//                            pUtil.printText("  数量 ：" + infos.get(i).getProductCount() + "PCS......");
//                            pUtil.printLine();
//                        }else {
//                            pUtil.printText("  sku ：" + infos.get(i).getSkuNo());
//                            pUtil.printLine();
//                            pUtil.printText("  物品名称 ：" + infos.get(i).getProductName());
//                            pUtil.printLine();
//                            pUtil.printText("  备货计划 ：" + infos.get(i).getStockNo());
//                            pUtil.printLine();
//                            pUtil.printText("  数量 ：" + infos.get(i).getProductCount() + "PCS");
//                            pUtil.printLine();
//                        }
//
//                    }else if(infos.size()<2 ){
//                        if(i == 1){
//                            pUtil.printText("  sku ：");
//                            pUtil.printLine();
//                            pUtil.printText("  物品名称 ：");
//                            pUtil.printLine();
//                            pUtil.printText("  备货计划 ：");
//                            pUtil.printLine();
//                            pUtil.printText("  数量 ：");
//                            pUtil.printLine();
//                        }else {
//                            pUtil.printText("  sku ：" + infos.get(i).getSkuNo());
//                            pUtil.printLine();
//                            pUtil.printText("  物品名称 ：" + infos.get(i).getProductName());
//                            pUtil.printLine();
//                            pUtil.printText("  备货计划 ：" + infos.get(i).getStockNo());
//                            pUtil.printLine();
//                            pUtil.printText("  数量 ：" + infos.get(i).getProductCount() + "PCS");
//                            pUtil.printLine();
//                        }
//
//                    }else {
//                        pUtil.printText("  sku ：" + infos.get(i).getSkuNo());
//                        pUtil.printLine();
//                        pUtil.printText("  物品名称 ：" + infos.get(i).getProductName());
//                        pUtil.printLine();
//                        pUtil.printText("  备货计划 ：" + infos.get(i).getStockNo());
//                        pUtil.printLine();
//                        pUtil.printText("  数量 ：" + infos.get(i).getProductCount() + "PCS");
//                        pUtil.printLine();
//                    }
//
//                    // 分隔线
//                    pUtil.printDashLine();
//                }
//            }
//            pUtil.printLargeText(" 最多2个装箱物品，最后一个采购订单编号/SKU数量含有...表示有更多！请通过手持终端扫描查看！");
//            pUtil.printLine(2);
//            pUtil = null;
//
//        } catch (IOException e) {
//
//        }
//    }

    //打印调拨箱唛
//    public static void printTest2(final UnallocateBoxedCartonResponse dataBean, final BluetoothSocket bluetoothSocket) {
//        LogUtils.e(TAG + "printTest2***dataBean:" + dataBean.toString());
//        //        new Thread() {
//        //            public void run() {
//        //初始化数据
//        try {
//            PrintUtil pUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK");
//            String url = dataBean.getQrCodeURl();
//            //打印二维码的图片 -- 如果提供了二维码的截图则用该方法
//            //                    if (!TextUtils.isEmpty(url)){
//            //                        pUtil.printBitmap(PicassoUtil.returnBitMap(url));
//            //                    }else {
//            //                        pUtil.printBitmap(BitmapFactory.decodeResource(UiUtil.getContext().getResources(), R.drawable.def_img));
//            //                    }
//
//            //打印二维码右边部分
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD);
//            pUtil.printText("  报关 ：" + TransformUtil.isCustomsTostring(dataBean.getIsCustoms()));
//            pUtil.printLine();
//            pUtil.printText("  箱号 ：" + dataBean.getCartonIndex() + "   " + dataBean.getPno());
//            pUtil.printLine();
//            pUtil.printText("  编号 ：" + dataBean.getCartonNo());
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD_CANCEL);
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printDashLine();
//            pUtil.printLine();
//
//            //打印仓库
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printText("   始发仓");
//            pUtil.printTabSpace(1);
//            pUtil.printText("   中转仓   ");
//            pUtil.printTabSpace(1);
//            pUtil.printText("   目的仓   ");
//            pUtil.printLine();
//            String whId = dataBean.getWhId();
//            String transitWhId = dataBean.getTransitWhId();
//            String toWhId = dataBean.getToWhId();
//            pUtil.printFontSize(PrintUtil.FONT_MIDDLE);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD);
//            pUtil.printText(whId != null ? "   " + whId : "   ------");
//            pUtil.printTabSpace(1);
//            pUtil.printText(transitWhId != null ? "   " + transitWhId + "   " : "   ------   ");
//            pUtil.printTabSpace(1);
//            pUtil.printText(toWhId != null ? "   " + toWhId + "   " : "   ------   ");
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD_CANCEL);
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printDashLine();
//
//            pUtil.printLine();
//            pUtil.printText("  重量 ：" + dataBean.getMaxWeight() + "kg");
//            pUtil.printLine();
//            double length = dataBean.getMaxLength();
//            double width = dataBean.getMaxWidth();
//            double height = dataBean.getMaxHeight();
//            double volume = length * width * height;
//            pUtil.printText("  体积 ：" + length + "*" + width + "*" + height + "cm=" + volume + "cm3");
//            pUtil.printLine();
//            pUtil.printText("  物品 ：" + dataBean.getProductCategoryCount() + "种/" + dataBean.getProductCount() + "PCS");
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printDashLine();
//            pUtil.printLine();
//
//            List<UnallocateBoxedCartonResponse.InfosBean> infos = dataBean.getInfos();
//            if (infos.size() > 0) {
//                for (int i = 0; i < 2; i++) {
//
//                    if(infos.size()>2){
//                        if(i == 1){
//                            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//                            pUtil.printTabSpace(2);
//                            pUtil.printText("  sku ：" + infos.get(i).getSkuNo());
//                            pUtil.printLine();
//                            pUtil.printText("  物品名称 ：" + infos.get(i).getProductName());
//                            pUtil.printLine();
//                            pUtil.printText("  备货计划 ：" + infos.get(i).getStockNo());
//                            pUtil.printLine();
//                            pUtil.printText("  数量 ：" + infos.get(i).getProductCount() + "PCS......");
//                            pUtil.printLine();
//                        }else {
//                            pUtil.printText("  sku ：" + infos.get(i).getSkuNo());
//                            pUtil.printLine();
//                            pUtil.printText("  物品名称 ：" + infos.get(i).getProductName());
//                            pUtil.printLine();
//                            pUtil.printText("  备货计划 ：" + infos.get(i).getStockNo());
//                            pUtil.printLine();
//                            pUtil.printText("  数量 ：" + infos.get(i).getProductCount() + "PCS");
//                            pUtil.printLine();
//                        }
//
//                    }else if(infos.size()<2 ){
//                        if(i == 1){
//                            pUtil.printText("  sku ：");
//                            pUtil.printLine();
//                            pUtil.printText("  物品名称 ：");
//                            pUtil.printLine();
//                            pUtil.printText("  备货计划 ：");
//                            pUtil.printLine();
//                            pUtil.printText("  数量 ：");
//                            pUtil.printLine();
//                        }else {
//                            pUtil.printText("  sku ：" + infos.get(i).getSkuNo());
//                            pUtil.printLine();
//                            pUtil.printText("  物品名称 ：" + infos.get(i).getProductName());
//                            pUtil.printLine();
//                            pUtil.printText("  备货计划 ：" + infos.get(i).getStockNo());
//                            pUtil.printLine();
//                            pUtil.printText("  数量 ：" + infos.get(i).getProductCount() + "PCS");
//                            pUtil.printLine();
//                        }
//
//                    }else {
//                        pUtil.printText("  sku ：" + infos.get(i).getSkuNo());
//                        pUtil.printLine();
//                        pUtil.printText("  物品名称 ：" + infos.get(i).getProductName());
//                        pUtil.printLine();
//                        pUtil.printText("  备货计划 ：" + infos.get(i).getStockNo());
//                        pUtil.printLine();
//                        pUtil.printText("  数量 ：" + infos.get(i).getProductCount() + "PCS");
//                        pUtil.printLine();
//                    }
//
//                    // 分隔线
//                    pUtil.printDashLine();
//                }
//            }
//            pUtil.printLargeText(" 最多2个装箱物品，最后一个采购订单编号/SKU数量含有...表示有更多！请通过手持终端扫描查看！");
//            pUtil.printLine(2);
//            pUtil = null;
//        } catch (IOException e) {
//            LogUtils.e(TAG + "printTest2错误信息：" + e.toString());
//        }
//        //            }
//        //        }.start();
//
//    }

    //打印卡板标签
//    public static void printTest3(final UnallocatedScanBoardResponse.ParamsBean.BoardInfoBean dataBean, final BluetoothSocket bluetoothSocket) {
//        LogUtils.e(TAG + "printTest3***dataBean:" + dataBean.toString());
//        //        new Thread() {
//        //            public void run() {
//        //初始化数据
//        try {
//            PrintUtil pUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK");
//
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD);
//            pUtil.printText("  报关 ：" + TransformUtil.isCustomsTostring(dataBean.getIsCustoms()));
//            pUtil.printLine();
//            pUtil.printText("  编号 ：" + dataBean.getBoardNo());
//            pUtil.printLine();
//            pUtil.printText("  重量总计 ：" + dataBean.getTotalWeight());
//            pUtil.printLine();
//            double length = dataBean.getTotalLength();
//            double width = dataBean.getTotalWidth();
//            double height = dataBean.getTotalHeight();
//            double volume = length * width * height;
//            pUtil.printText("  体积总计 ：" + length + "*" + width + "*" + height + "cm=" + volume + "cm3");
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD_CANCEL);
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printDashLine();
//            pUtil.printLine();
//
//            //打印仓库
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printText("   始发仓");
//            pUtil.printTabSpace(1);
//            pUtil.printText("   中转仓   ");
//            pUtil.printTabSpace(1);
//            pUtil.printText("   目的仓   ");
//            pUtil.printLine();
//            String whId = dataBean.getWhId();
//            String transitWhId = dataBean.getTransitWhId();
//            String toWhId = dataBean.getWIdStore();
//            pUtil.printFontSize(PrintUtil.FONT_MIDDLE);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD);
//            pUtil.printText(whId != null ? "   " + whId : "   ------");
//            pUtil.printTabSpace(1);
//            pUtil.printText(transitWhId != null ? "   " + transitWhId + "   " : "   ------   ");
//            pUtil.printTabSpace(1);
//            pUtil.printText(toWhId != null ? "   " + toWhId + "   " : "   ------   ");
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD_CANCEL);
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printDashLine();
//
//            pUtil.printLine();
//            pUtil.printText("  毛重总计 ：" + dataBean.getMaxWeight() + "kg");
//            pUtil.printLine();
//            pUtil.printText("  箱数总计 ：" + dataBean.getBoxCount() + "箱");
//            pUtil.printLine();
//            pUtil.printText("  物品个数总计 ：" + dataBean.getProductCategoryCount() + "种");
//            pUtil.printLine();
//            pUtil.printText("  物品数量总计 ：" + dataBean.getProductCount() + "PCS");
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printDashLine();
//
//            //打印二维码
//            pUtil.printLine();
//            String url = dataBean.getQrCodeUrl();
//            if (!TextUtils.isEmpty(url)) {
//                pUtil.printAlignment(PrintUtil.ALIGN_CENTER);
//                pUtil.printQrCodeBitmap(PicassoUtil.returnBitMap(url));
//            } else {
//                pUtil.printAlignment(PrintUtil.ALIGN_CENTER);
//                pUtil.printBitmap(BitmapFactory.decodeResource(UiUtil.getContext().getResources(), R.mipmap.def_img));
//            }
//            pUtil.printLine(2);
//
//            //切纸
//            pUtil.feedAndCut();
//            pUtil = null;
//
//        } catch (IOException e) {
//            LogUtils.e(TAG + "printTest3错误信息：" + e.toString());
//        }
//        //        //            }
//        //        //        }.start();
//
//    }

    //打印采购箱唛
//    public static void printPoCarton1(final CreatedPurchaseCartonGenerateResponse dataBean, final BluetoothSocket bluetoothSocket) {
//        //        LogUtils.e(TAG + "printTest3***dataBean:" + dataBean.toString());
//        //        new Thread() {
//        //            public void run() {
//        //初始化数据
//        try {
//            PrintUtil pUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK");
//
//            pUtil.printAlignment(PrintUtil.ALIGN_CENTER);
//            pUtil.printFontSize(PrintUtil.FONT_MIDDLE);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD);
//            pUtil.printText(UiUtil.getString(R.string.company_name));
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printDashLine();
//            pUtil.printLine();
//
//            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD);
//            pUtil.printText("  箱唛编号 ：");//+ dataBean.getBoardNo()
//            pUtil.printLine();
//            pUtil.printText("  采购编号 ：（共" + "个）");//+ dataBean.采购单个数
//            pUtil.printLine();
//            //循环集合打印
//            //数据测试
//            List<UnallocateBoxedCartonResponse.InfosBean> datas = new ArrayList<>();
//            datas.add(new UnallocateBoxedCartonResponse.InfosBean());
//            datas.add(new UnallocateBoxedCartonResponse.InfosBean());
//            datas.add(new UnallocateBoxedCartonResponse.InfosBean());
//            datas.add(new UnallocateBoxedCartonResponse.InfosBean());
//            for (int i = 0; i < datas.size(); i++) {
//                if (i > 2) {
//                    break;
//                } else if (i == 2) {
//                    pUtil.printText("    " + i + "......");
//                    pUtil.printLine();
//                } else {
//                    pUtil.printText("    " + i);//+ dataBean.采购单个数
//                    pUtil.printLine();
//                }
//
//            }
//
//            // 分隔线
//            pUtil.printDashLine();
//            pUtil.printLine();
//
//            pUtil.printFontSize(PrintUtil.FONT_NORMAL);
//            pUtil.printFontBold(PrintUtil.FONT_BOLD_CANCEL);
//            pUtil.printText("  重量 ：" + "kg");//dataBean.getTotalWeight()
//            pUtil.printLine();
//            //            double length = dataBean.getTotalLength();
//            //            double width =  dataBean.getTotalWidth();
//            //            double height = dataBean.getTotalHeight();
//            //            double volume = length * width * height;
//            //            pUtil.printText("  体积 ：" + length + "*" + width + "*" + height + "cm=" + volume + "cm3");
//            pUtil.printText("  体积 ：" + "5.0*6.0*7.0" + "cm=" + "210.0cm3");
//            pUtil.printLine();
//            pUtil.printText("  物品个数 ：" + "种");
//            pUtil.printLine();
//
//            // 分隔线
//            pUtil.printDashLine();
//            pUtil.printLine();
//
//            //            List<UnallocateBoxedCartonResponse.InfosBean> infos = dataBean.getInfos();
//            //数据测试
//            List<UnallocateBoxedCartonResponse.InfosBean> infos = new ArrayList<>();
//            infos.add(new UnallocateBoxedCartonResponse.InfosBean());
//            infos.add(new UnallocateBoxedCartonResponse.InfosBean());
//            infos.add(new UnallocateBoxedCartonResponse.InfosBean());
//            if (infos.size() > 0) {
//                for (int i = 0; i < infos.size(); i++) {
//                    if (i == 2) {
//                        break;
//                    }
//                    pUtil.printText("  sku ：" + infos.get(i).getSkuNo() + i);
//                    pUtil.printLine();
//                    pUtil.printText("  物品名称 ：" + infos.get(i).getProductName());
//                    pUtil.printLine();
//                    pUtil.printText("  装箱/套袋数量 ：" + infos.get(i).getStockNo());
//                    pUtil.printLine();
//                    // 分隔线
//                    pUtil.printDashLine();
//                    pUtil.printLine();
//                }
//
//                if (infos.size() < 1) {
//                    pUtil.printLine(8);
//                }
//            }
//            pUtil.printLine(2);
//
//            // 分隔线
//            //            pUtil.printAlignment(PrintUtil.ALIGN_LEFT);
//            //            pUtil.printDashLine();
//            //
//            //            //打印二维码
//            //            pUtil.printLine();
//            //            String url = dataBean.getQrCodeUrl();
//            //            if (!TextUtils.isEmpty(url)) {
//            //                pUtil.printAlignment(PrintUtil.ALIGN_CENTER);
//            //                pUtil.printBitmap(PicassoUtil.returnBitMap(url));
//            //            } else {
//            //                pUtil.printAlignment(PrintUtil.ALIGN_CENTER);
//            //                pUtil.printBitmap(BitmapFactory.decodeResource(UiUtil.getContext().getResources(), R.drawable.def_img));
//            //            }
//            //            pUtil.printLine();
//
//            //切纸
//            pUtil.feedAndCut();
//            pUtil = null;
//
//        } catch (IOException e) {
//            LogUtils.e(TAG + "printPoCarton错误信息：" + e.toString());
//        }
//        //        //            }
//        //        //        }.start();
//
//    }

    //打印采购箱唛图片/调拨箱唛图片
    public static void printPdfBitmap(final String response, final BluetoothSocket bluetoothSocket) {
//        new Thread() {
//            public void run() {
                //初始化数据
                try {
                    PrintUtil pUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK");
                    pUtil.printLine();
                    pUtil.printBitmap(PicassoUtil.convertStringToIcon(response));
//                    pUtil.printLine();
                    pUtil.feedAndCut();
                    pUtil = null;

                } catch (IOException e) {
                    LogUtils.e(TAG + "printPoCarton错误信息：" + e.toString());
                }
//            }
//        }.start();



    }

    //打印采购箱唛图片/调拨箱唛图片
    public static void printPdfBitmap(final Bitmap bitmap, final BluetoothSocket bluetoothSocket) {
        //        new Thread() {
        //            public void run() {
        //初始化数据
        try {
            PrintUtil pUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK"); //utf-8
            pUtil.printLine();
            pUtil.printBitmap(bitmap);
            pUtil.printLine(2);

//            pUtil.feedAndCut();
            Thread.sleep(400);

        } catch (Exception e) {
            LogUtils.e(TAG + "printPoCarton错误信息：" + e.toString());
        }
        //            }
        //        }.start();

    }

    //打印调拨箱唛图片
    public static void printStockCartonBitmaps(final List<String> datas, final BluetoothSocket bluetoothSocket) {
        //        new Thread() {
        //            public void run() {
        //初始化数据
        try {
            PrintUtil pUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK");
//            pUtil.printLine();
            for (String data : datas){
                LogUtils.e(TAG+"data:"+data);
                Bitmap bitmap = PicassoUtil.convertStringToIcon(data);
                pUtil.printAlignment(PrintUtil.ALIGN_CENTER);
                pUtil.printBitmap(bitmap);
                pUtil.printLine();
            }

            //
            pUtil.feedAndCut();
            pUtil = null;

        } catch (IOException e) {
            LogUtils.e(TAG + "printPoCarton错误信息：" + e.toString());
        }
        //            }
        //        }.start();

    }

    //打印sku图片
    public static void printSkuBitmap(final String response, final BluetoothSocket bluetoothSocket) {
        //        new Thread() {
        //            public void run() {
        //初始化数据
        try {
            PrintUtil pUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK");
//            pUtil.printLine();
            pUtil.printAlignment(PrintUtil.ALIGN_CENTER);
            pUtil.printSkuBitmap(PicassoUtil.convertStringToIcon(response));
            pUtil.printLine();
            pUtil.feedAndCut();
            pUtil = null;

        } catch (IOException e) {
            LogUtils.e(TAG + "printPoCarton错误信息：" + e.toString());
        }
        //            }
        //        }.start();

    }


    /**
     * 打印内容
     */
//    public static StringBuilder createXxTxt(UnallocateBoxedCartonResponse dataBean) {
//
//        StringBuilder builder = new StringBuilder();
//
//        //设置大号字体以及加粗
//        builder.append(PrintUtil.getFontSizeCmd(PrintUtil.FONT_BIG));
//        builder.append(PrintUtil.getFontBoldCmd(PrintUtil.FONT_BOLD));
//
//        // 标题
//        builder.append("Title");
//        //换行，调用次数根据换行数来控制
//        addLineSeparator(builder);
//
//        //设置普通字体大小、不加粗
//        builder.append(PrintUtil.getFontSizeCmd(PrintUtil.FONT_NORMAL));
//        builder.append(PrintUtil.getFontBoldCmd(PrintUtil.FONT_BOLD_CANCEL));
//
//        //内容
//        builder.append("  报关 ：" + TransformUtil.isCustomsTostring(dataBean.getIsCustoms()));
//        addLineSeparator(builder);
//        builder.append("  箱号 ：" + dataBean.getCartonIndex() + "   " + dataBean.getPno());
//        addLineSeparator(builder);
//        builder.append("  编号 ：" + dataBean.getCartonNo());
//        addLineSeparator(builder);
//
//        builder.append("   始发仓");
//        builder.append("   中转仓   ");
//        builder.append("   目的仓   ");
//        String whId = dataBean.getWhId();
//        String transitWhId = dataBean.getTransitWhId();
//        String toWhId = dataBean.getToWhId();
//        builder.append(whId != null ? "   " + whId : "   ------");
//        builder.append(transitWhId != null ? "   " + transitWhId + "   " : "   ------   ");
//        builder.append(toWhId != null ? "   " + toWhId + "   " : "   ------   ");
//
//        //设置某两列文字间空格数, x需要计算出来
//        addIdenticalStrToStringBuilder(builder, 4, " ");
//
//        //切纸
//        builder.append(PrintUtil.getCutPaperCmd());
//
//        return builder;
//    }

    /**
     * 向StringBuilder中添加指定数量的相同字符
     *
     * @param printCount   添加的字符数量
     * @param identicalStr 添加的字符
     */

    private static void addIdenticalStrToStringBuilder(StringBuilder builder, int printCount, String identicalStr) {
        for (int i = 0; i < printCount; i++) {
            builder.append(identicalStr);
        }
    }

    /**
     * 根据字符串截取前指定字节数,按照GBK编码进行截取
     *
     * @param str 原字符串
     * @param len 截取的字节数
     * @return 截取后的字符串
     */
    private static String subStringByGBK(String str, int len) {
        String result = null;
        if (str != null) {
            try {
                byte[] a = str.getBytes("GBK");
                if (a.length <= len) {
                    result = str;
                } else if (len > 0) {
                    result = new String(a, 0, len, "GBK");
                    int length = result.length();
                    if (str.charAt(length - 1) != result.charAt(length - 1)) {
                        if (length < 2) {
                            result = null;
                        } else {
                            result = result.substring(0, length - 1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 添加换行符
     */
    private static void addLineSeparator(StringBuilder builder) {
        builder.append("\n");
    }

    /**
     * 在GBK编码下，获取其字符串占据的字符个数
     */
    private static int getCharCountByGBKEncoding(String text) {
        try {
            return text.getBytes("GBK").length;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 打印相关配置
     */
  static class PrintConfig {
        public int maxLength = 30;

        public boolean printBarcode = false; // 打印条码
        public boolean printQrCode  = false;  // 打印二维码
        public boolean printEndText = true;  // 打印结束语
        public boolean needCutPaper = false; // 是否切纸
    }

}