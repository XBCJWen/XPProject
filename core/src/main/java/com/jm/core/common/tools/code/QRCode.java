package com.jm.core.common.tools.code;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * 二维码的图片生成
 */
public class QRCode {

    private QRCode() {
    }

    private static final int BLACK = 0xff000000;

    public static Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
//使用实例
//    /**
//     * 设置小组二维码
//     * @param id
//     */
//    private void setQRCodeView(long id){
//        String code = String.valueOf(id);
//        Bitmap qrCodeBitmap = null;
//        try {
//            qrCodeBitmap = EncodingHandler.createQRCode(code, 350);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        if (qrCodeBitmap != null)
//            mCodeImageView.setImageBitmap(qrCodeBitmap);
//    }
