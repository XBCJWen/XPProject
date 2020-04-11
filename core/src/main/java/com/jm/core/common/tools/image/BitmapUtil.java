package com.jm.core.common.tools.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 关于Bitmap工具类
 *
 * @author jinXiong.Xie
 */

public class BitmapUtil {

    private BitmapUtil() {
    }

    /**
     * 保存Bitmap到文件中
     * @param bitmap
     * @param file
     */
    public static void saveBitmapFile(Bitmap bitmap, File file) {
        //将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用RGB_565法进行bitmap压缩
     *
     * @param file
     * @return
     */
    public static Bitmap compressionBitmap(File file) {
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeFile(file.getPath(), options2);
    }

    /**
     * 固定尺寸压缩
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createScaledBitmap(Bitmap bitmap, int size) {
        return Bitmap.createScaledBitmap(bitmap, size, size, true);
    }

    /**
     * 按比例压缩
     *
     * @param bitmap
     */
    public static Bitmap martixBitmap(Bitmap bitmap, int size) {

//    Log.e("wechat", "压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
//        + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        int max = height > width ? height : width;
        if (max <= size) {
            return bitmap;
        }
        float scale = size * 1.0f / max;
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
//    Log.e("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 )
//        + "Kb宽度为" + bm.getWidth() + "高度为" + bm.getHeight());
        return bm;
    }

    /**
     * 获取Bitmap
     *
     * @param file
     * @return
     */
    public static Bitmap getBitmap(File file) {
        return BitmapUtil.compressionBitmap(file);
    }

    /**
     * 获取Bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmap(String path) {
        return getBitmap(new File(path));
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转Bitmap
     *
     * @param img
     * @param degree
     * @return
     */
    public static Bitmap toTurn(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
         /*翻转90度*/
        matrix.postRotate(degree);
        int width = img.getWidth();
        int height = img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }
}
