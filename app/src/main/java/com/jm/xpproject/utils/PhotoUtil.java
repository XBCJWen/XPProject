package com.jm.xpproject.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.jm.api.util.PermissionTools;
import com.jm.core.common.tools.image.BitmapUtil;
import com.jm.xpproject.ui.common.act.ClipImageActivity;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.jm.core.common.tools.base.FileUtil.getRealFilePathFromUri;


/**
 * 图片处理工具类
 *
 * @author jinXiong.Xie
 */

public class PhotoUtil {

    /**
     * 拍照
     */
    public static final int TAKE_CAMERA = 1;
    /**
     * 拍照后截取
     */
    public static final int CUT_CAMERA = 2;
    /**
     * 选择图片后截取
     */
    public static final int CUT_PHOTO = 3;
    /**
     * 选择图片
     */
    public static final int CHOOSE_PHOTO = 4;

    /**
     * 判断是否剪切
     */
    private boolean isCut = false;

    /**
     * 选择图片所需要的权限
     */
    static final String[] PERMISSIONS_SELECT_PHOTO = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    /**
     * 拍照所需要的权限
     */
    static final String[] PERMISSIONS_TAKE_CAMERA = new String[]{
            Manifest.permission.CAMERA,
    };

    private Activity activity;
    private PermissionTools permissionTools;

    private Uri imageUri;
    private File outputImage;
    private OnShowDialogListener dialogListener;

    /**
     * 记录用户所操作的方式
     */
    private int type = TAKE_CAMERA;

    public PhotoUtil(Context context) {
        this(context, null);

    }

    public PhotoUtil(Context context, OnShowDialogListener dialogListener) {
        this.activity = (Activity) context;
        permissionTools = new PermissionTools(context);
        this.dialogListener = dialogListener;

    }

    public Uri getImageUri() {
        return imageUri;
    }

    public File getOutputImage() {
        return outputImage;
    }

    /**
     * 选择本地图片
     */
    public void choosePhoto(boolean isCut) {
        this.isCut = isCut;

        if (isCut) {
            type = CUT_PHOTO;
        } else {
            type = CHOOSE_PHOTO;
        }

        requestPermission(PERMISSIONS_SELECT_PHOTO, new RequestPermissionCallBack() {
            @Override
            public void success() {
                openAlbum();
            }
        });

    }

    /**
     * 拍照
     */
    public void takeCamera(boolean isCut) {
        this.isCut = isCut;

        if (isCut) {
            type = CUT_CAMERA;
        } else {
            type = TAKE_CAMERA;
        }

        requestPermission(PERMISSIONS_TAKE_CAMERA, new RequestPermissionCallBack() {
            @Override
            public void success() {
                openCamera();
            }
        });
    }

    /**
     * 获取权限
     *
     * @param permission
     * @param callBack
     */
    private void requestPermission(String[] permission, final RequestPermissionCallBack callBack) {
        if (dialogListener == null) {
            permissionTools.requestPermissionDefault(new PermissionTools.PermissionCallBack() {
                @Override
                public void onSuccess() {
                    if (callBack != null) {
                        callBack.success();
                    }
                }

                @Override
                public void onCancel() {

                }
            }, permission);
        } else {
            permissionTools.requestPermission(new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    if (callBack != null) {
                        callBack.success();
                    }
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    dialogListener.showDialog();
                }
            }, permission);
        }
    }

    /**
     * 获取权限回调
     */
    interface RequestPermissionCallBack {
        void success();
    }

    private void openCamera() {
        //创建File对象，用于存储拍照后的图片
        outputImage = new File(activity.getExternalCacheDir(), "output_image" + System.currentTimeMillis() + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (VERSION.SDK_INT >= 24) {
            imageUri = FileProvider
                    .getUriForFile(activity, activity.getPackageName() + ".FileProvider",
                            outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动照相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, TAKE_CAMERA);
    }

    private void openAlbum() {
//        //启动选择图片
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
//        activity.startActivityForResult(intent, CHOOSE_PHOTO);
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(Intent.createChooser(intent, "请选择图片"), CHOOSE_PHOTO);
    }

    private void handleImage(Intent data, OnPhotoBackListener listener) {

        if (VERSION.SDK_INT >= 19) {
            handleImageOnKitKat(data, listener);
        } else {
            handleImageBeforeKitKat(data, listener);
        }
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data, OnPhotoBackListener listener) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(activity, uri)) {
            //如果是document类型的uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = Media._ID + "=" + id;
                imagePath = getImagePath(Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris
                        .withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath, listener);
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = activity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void handleImageBeforeKitKat(Intent data, OnPhotoBackListener listener) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath, listener);
    }

    private void displayImage(String imagePath, OnPhotoBackListener listener) {
        if (imagePath == null || !new File(imagePath).exists()) {
            Toast.makeText(activity, "找不到该图片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imagePath != null) {
            if (isCut) {
                cutPhoto(imagePath);
            } else {
                int degree = BitmapUtil.readPictureDegree(imagePath);
                if (degree != 0) {
                    BitmapUtil.saveBitmapFile(BitmapUtil.toTurn(BitmapUtil.getBitmap(imagePath), degree), new File(imagePath));
                }
//                if (listener != null) {
//                    listener.onSuccess(getBitmap(), new File(imagePath));
//                }
                if (listener != null) {
                    listener.onSuccess(BitmapFactory.decodeFile(imagePath), new File(imagePath));
                }
            }
        } else {
            Toast.makeText(activity, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void cutPhoto(String imagePath) {
        //      Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//      imgPhoto.setImageBitmap(bitmap);
        File inputImage = new File(imagePath);
        if (!inputImage.exists()) {
            Toast.makeText(activity, "找不到该图片", Toast.LENGTH_SHORT).show();
            return;
        }
//      Bitmap bm = BitmapUtil.compressionBitmap(outputImage);
//      BitmapUtil.saveBitmapFile(BitmapUtil.martixBitmap(bm), outputImage);
        //创建File对象，用于存储拍照后的图片
        outputImage = new File(activity.getExternalCacheDir(), "output_image" + System.currentTimeMillis() + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Uri inImageUri;
        if (VERSION.SDK_INT >= 24) {
//            inImageUri = FileProvider
//                    .getUriForFile(activity, activity.getPackageName() + ".FileProvider",
//                            inputImage);
            imageUri = FileProvider
                    .getUriForFile(activity, activity.getPackageName() + ".FileProvider",
                            outputImage);
        } else {
//            inImageUri = Uri.fromFile(inputImage);
            imageUri = Uri.fromFile(outputImage);
        }

        turnClipAct(imagePath);
//        Crop.of(inImageUri, imageUri).asSquare().start(activity, CUT_PHOTO);
//        Toast.makeText(activity, "请长按蓝框直角移动进行缩放", Toast.LENGTH_SHORT).show();

    }

    /**
     * 跳转至剪切图片页面
     */
    private void turnClipAct(String imagePath) {
        Intent intent = new Intent();
        intent.setClass(activity, ClipImageActivity.class);
        intent.putExtra("FilePath", imagePath);
        activity.startActivityForResult(intent, CUT_PHOTO);
    }

    /**
     * 压缩图片（只能压缩得到拍照和剪切后的Bitmap）
     */
    private Bitmap compressionBitMap(int size) {
        Bitmap bm = BitmapUtil.compressionBitmap(outputImage);
        BitmapUtil.saveBitmapFile(BitmapUtil.martixBitmap(bm, size), outputImage);

        return BitmapFactory.decodeFile(outputImage.getPath());
    }

    /**
     * 获取权限失败的监听
     */
    public interface OnShowDialogListener {
        void showDialog();
    }

    /**
     * 图片处理结果
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data,
                                 OnPhotoBackListener listener) {
        switch (requestCode) {
            case TAKE_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (outputImage == null) {
                        return;
                    }
                    if (isCut) {
//                        Crop.of(imageUri, imageUri).asSquare().start(activity, CUT_CAMERA);
                        turnClipAct(outputImage.getPath());
//                        Toast.makeText(activity, "请长按蓝框直角移动进行缩放", Toast.LENGTH_SHORT).show();
                    } else {
                        int degree = BitmapUtil.readPictureDegree(outputImage.getPath());
                        if (degree != 0) {
                            BitmapUtil.saveBitmapFile(BitmapUtil.toTurn(BitmapUtil.getBitmap(outputImage), degree), outputImage);
                        }
                        if (listener != null) {
                            listener.onSuccess(getBitmap(), outputImage);
                        }
                    }
                }
                break;
            case CUT_CAMERA:
            case CUT_PHOTO:
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();
                    if (uri == null) {
                        //重新选择
                        switch (type) {
                            case TAKE_CAMERA:
                            case CUT_CAMERA:
                                takeCamera(isCut);
                                break;
                            case CHOOSE_PHOTO:
                            case CUT_PHOTO:
                                choosePhoto(isCut);
                                break;
                            default:

                        }
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(activity.getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    if (listener != null) {
                        listener.onSuccess(bitMap, new File(cropImagePath));
                    }

                }
//                if (resultCode == RESULT_OK) {
//                    //将拍摄的照片显示出来
////            Bitmap bitmap = BitmapFactory
////                .decodeStream(getContentResolver().openInputStream(imageUri));
//                    int degree = BitmapUtil.readPictureDegree(outputImage.getPath());
//                    if (degree != 0) {
//                        BitmapUtil.saveBitmapFile(BitmapUtil.toTurn(BitmapUtil.getBitmap(outputImage), degree), outputImage);
//                    }
//                    if (listener != null) {
//                        listener.onSuccess(getBitmap(), outputImage);
//                    }
//                }

                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    handleImage(data, listener);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取图片（只能获取得到拍照和剪切后的Bitmap）
     */
    private Bitmap getBitmap() {
        if (outputImage != null) {
            return BitmapFactory.decodeFile(outputImage.getPath());
        }
        return null;
    }

    /**
     * 图片返回数据
     */

    public interface OnPhotoBackListener {

        void onSuccess(Bitmap bitmap, File file);
    }


}
