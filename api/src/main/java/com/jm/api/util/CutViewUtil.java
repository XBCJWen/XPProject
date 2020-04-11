package com.jm.api.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.jm.core.common.tools.BaseUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 截取View的截图
 *
 * @author bjsdm
 * @date 2019-07-10
 */
public class CutViewUtil extends BaseUtil {

    public CutViewUtil(Context context) {
        super(context);
    }

    /**
     * 截取并保存
     *
     * @param view
     */
    public void cutViewAndSave(View view) {
        PermissionTools permissionTools = new PermissionTools(getActivity());
        permissionTools.requestPermissionDefault(new PermissionTools.PermissionCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onCancel() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        saveImageToGallery(loadBitmapFromView(view));
    }


    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(screenshot);
        canvas.translate(-v.getScrollX(), -v.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        v.draw(canvas);// 将 view 画到画布上
        return screenshot;
    }

    public void saveImageToGallery(Bitmap bitmap) {
        // 首先保存图片
        File file = null;
        String fileName = System.currentTimeMillis() + ".png";
        File root = new File(Environment.getExternalStorageDirectory(), getContext().getPackageName());
        File dir = new File(root, "images");
        if (dir.mkdirs() || dir.isDirectory()) {
            file = new File(dir, fileName);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(getContext(), new String[]{file.getAbsolutePath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            mediaScanIntent.setData(uri);
                            getContext().sendBroadcast(mediaScanIntent);
                        }
                    });
        } else {
            String relationDir = file.getParent();
            File file1 = new File(relationDir);
            getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(file1.getAbsoluteFile())));
        }
    }
}
