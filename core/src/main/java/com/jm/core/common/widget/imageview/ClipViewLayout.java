package com.jm.core.common.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jm.core.R;

import java.io.IOException;

import static com.jm.core.common.tools.base.FileUtil.getRealFilePathFromUri;


/**
 * 头像上传原图裁剪容器
 */
public class ClipViewLayout extends RelativeLayout {

    private static final String TAG = "ClipViewLayout";

    /**
     * 裁剪原图
     */
    private ImageView imageView;
    /**
     * 裁剪框
     */
    private ClipView clipView;
    /**
     * 裁剪框水平方向间距，xml布局文件中指定
     */
    private float mHorizontalPadding;
    /**
     * 裁剪框垂直方向间距，计算得出
     */
    private float mVerticalPadding;
    /**
     * 图片缩放、移动操作矩阵
     */
    private Matrix matrix = new Matrix();
    /**
     * 图片原来已经缩放、移动过的操作矩阵
     */
    private Matrix savedMatrix = new Matrix();
    /**
     * 动作标志：无
     */
    private static final int NONE = 0;
    /**
     * 动作标志：拖动
     */
    private static final int DRAG = 1;
    /**
     * 动作标志：缩放
     */
    private static final int ZOOM = 2;
    /**
     * 初始化动作标志
     */
    private int mode = NONE;
    /**
     * 记录起始坐标
     */
    private PointF start = new PointF();
    /**
     * 记录缩放时两指中间点坐标
     */
    private PointF mid = new PointF();
    private float oldDist = 1f;
    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];
    /**
     * 最小缩放比例
     */
    private float minScale;
    /**
     * 最大缩放比例
     */
    private float maxScale = 4;
    /**
     * 图片高度
     */
    private int intrinsicHeight;
    /**
     * 图片宽度
     */
    private int intrinsicWidth;


    public ClipViewLayout(Context context) {
        this(context, null);
    }

    public ClipViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化控件自定义的属性
     *
     * @param context
     * @param attrs
     */
    public void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ClipViewLayout);

        //获取剪切框距离左右的边距, 默认为50dp
        mHorizontalPadding = array.getDimensionPixelSize(R.styleable.ClipViewLayout_mHorizontalPadding,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        //获取裁剪框边框宽度，默认1dp
        int clipBorderWidth = array.getDimensionPixelSize(R.styleable.ClipViewLayout_clipBorderWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        //裁剪框类型(圆或者矩形)
        int clipType = array.getInt(R.styleable.ClipViewLayout_clipType, 1);

        //回收
        array.recycle();
        clipView = new ClipView(context);
        //设置裁剪框类型
        clipView.setClipType(clipType == 1 ? ClipView.ClipType.CIRCLE : ClipView.ClipType.RECTANGLE);
        //设置剪切框边框
        clipView.setClipBorderWidth(clipBorderWidth);
        //设置剪切框水平间距
        clipView.setmHorizontalPadding(mHorizontalPadding);
        imageView = new ImageView(context);
        //相对布局布局参数
        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(imageView, lp);
        this.addView(clipView, lp);
    }


    /**
     * 标记是否初始化过
     */
    private boolean frist = false;

    /**
     * 初始化图片
     */
    public void setImageSrc(final Uri uri) {
        //需要等到imageView绘制完毕再初始化原图
        ViewTreeObserver observer = imageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!frist) {
                    initSrcPic(uri);
                    imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    frist = true;

                    Drawable drawable = imageView.getDrawable();

                    if (drawable == null) {
                        return;
                    }
                    // 初始化bitmap的宽高
                    intrinsicHeight = drawable.getIntrinsicHeight();
                    intrinsicWidth = drawable.getIntrinsicWidth();
                }
            }
        });
    }

    /**
     * 初始化图片
     */
    public void setImageSrc(final String path) {
        //需要等到imageView绘制完毕再初始化原图
        ViewTreeObserver observer = imageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!frist) {
                    initSrcPic(path);
                    imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    frist = true;

                    Drawable drawable = imageView.getDrawable();

                    if (drawable == null) {
                        return;
                    }
                    // 初始化bitmap的宽高
                    intrinsicHeight = drawable.getIntrinsicHeight();
                    intrinsicWidth = drawable.getIntrinsicWidth();
                }
            }
        });
    }

    /**
     * 初始化图片
     * step 1: decode 出 720*1280 左右的照片  因为原图可能比较大 直接加载出来会OOM
     * step 2: 将图片缩放 移动到imageView 中间
     */
    public void initSrcPic(Uri uri) {
        if (uri == null) {
            return;
        }
//        Log.d("evan", "**********clip_view uri*******  " + uri);
        String path = getRealFilePathFromUri(getContext(), uri);
//        Log.d("evan", "**********clip_view path*******  " + path);
        if (TextUtils.isEmpty(path)) {
            return;
        }

        initSrcPic(path);
    }

    /**
     * 初始化图片
     * step 1: decode 出 720*1280 左右的照片  因为原图可能比较大 直接加载出来会OOM
     * step 2: 将图片缩放 移动到imageView 中间
     */
    public void initSrcPic(String path) {
        if (path == null) {
            return;
        }
        if (TextUtils.isEmpty(path)) {
            return;
        }

        //这里decode出720*1280 左右的照片,防止OOM
        Bitmap bitmap = decodeSampledBitmap(path, 720, 1280);
        if (bitmap == null) {
            return;
        }

        //竖屏拍照的照片，直接使用的话，会旋转90度，下面代码把角度旋转过来
        //查询旋转角度
        int rotation = getExifOrientation(path);
        Matrix m = new Matrix();
        m.setRotate(rotation);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);

        //图片的缩放比
        float scale;
        //宽图
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            scale = (float) imageView.getWidth() / bitmap.getWidth();
            //如果高缩放后小于裁剪区域 则将裁剪区域与高的缩放比作为最终的缩放比
            Rect rect = clipView.getClipRect();
            //高的最小缩放比
            minScale = rect.height() / (float) bitmap.getHeight();
            if (scale < minScale) {
                scale = minScale;
            }
        } else {//高图
            //高的缩放比
            scale = (float) imageView.getHeight() / bitmap.getHeight();
            //如果宽缩放后小于裁剪区域 则将裁剪区域与宽的缩放比作为最终的缩放比
            Rect rect = clipView.getClipRect();
            //宽的最小缩放比
            minScale = rect.width() / (float) bitmap.getWidth();
            if (scale < minScale) {
                scale = minScale;
            }
        }
        // 缩放
        matrix.postScale(scale, scale);
        // 平移,将缩放后的图片平移到imageView的中心
        //imageView的中心x
        int midX = imageView.getWidth() / 2;
        //imageView的中心y
        int midY = imageView.getHeight() / 2;
        //bitmap的中心x
        int imageMidX = (int) (bitmap.getWidth() * scale / 2);
        //bitmap的中心y
        int imageMidY = (int) (bitmap.getHeight() * scale / 2);
        matrix.postTranslate(midX - imageMidX, midY - imageMidY);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setImageMatrix(matrix);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 查询图片旋转角度
     */
    public static int getExifOrientation(String filepath) {
        // YOUR MEDIA PATH AS STRING
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
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
                }

            }
        }
        return degree;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                //设置开始点位置
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //开始放下时候两手指间的距离
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                //拖动
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    mVerticalPadding = clipView.getClipRect().top;
                    float[] values = new float[9];
                    matrix.getValues(values);
                    matrix.postTranslate(checkDxBound(values, dx), checkDyBound(values, dy));
//                    checkDxBound(values, dx);
//                    checkDyBound(values, dy);
//                    //检查边界
//                    checkBorder();
                    //缩放
                } else if (mode == ZOOM) {
                    //缩放后两手指间的距离
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        //手势缩放比例
                        float scale = newDist / oldDist;
                        //缩小
                        if (scale < 1) {
                            if (getScale() > minScale) {
                                matrix.set(savedMatrix);
                                mVerticalPadding = clipView.getClipRect().top;
                                matrix.postScale(scale, scale, mid.x, mid.y);
                                //缩放到最小范围下面去了，则返回到最小范围大小
                                while (getScale() < minScale) {
                                    //返回到最小范围的放大比例
                                    scale = 1 + 0.01F;
                                    matrix.postScale(scale, scale, mid.x, mid.y);
                                }
                            }
                            //边界检查
                            checkBorder();
                        } else { //放大
                            if (getScale() <= maxScale) {
                                matrix.set(savedMatrix);
                                mVerticalPadding = clipView.getClipRect().top;
                                matrix.postScale(scale, scale, mid.x, mid.y);
                            }
                        }
                    }
                }
                imageView.setImageMatrix(matrix);
                break;
            default:
        }
        return true;
    }


    /**
     * 边界检测
     */
    private void checkBorder() {
        RectF rect = getMatrixRectF(matrix);
        float deltaX = 0;
        float deltaY = 0;
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        // 如果宽或高大于屏幕，则控制范围 ; 这里的0.001是因为精度丢失会产生问题，但是误差一般很小，所以我们直接加了一个0.01
        if (rect.width() >= width - 2 * mHorizontalPadding) {
            if (rect.left > mHorizontalPadding) {
                deltaX = -rect.left + mHorizontalPadding;
            }
            if (rect.right < width - mHorizontalPadding) {
                deltaX = width - mHorizontalPadding - rect.right;
            }
        }
        if (rect.height() >= height - 2 * mVerticalPadding) {
            if (rect.top > mVerticalPadding) {
                deltaY = -rect.top + mVerticalPadding;
            }
            if (rect.bottom < height - mVerticalPadding) {
                deltaY = height - mVerticalPadding - rect.bottom;
            }
        }
        matrix.postTranslate(deltaX, deltaY);
    }


    /**
     * 和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
     *
     * @param values
     * @param dy
     * @return
     */
    private float checkDyBound(float[] values, float dy) {

        float height = imageView.getHeight();
//        Log.e(TAG, "intrinsicHeight * values[Matrix.MSCALE_Y]: " + intrinsicHeight * values[Matrix.MSCALE_Y]);
//        Log.e(TAG, "height: " + height);
//        Log.e(TAG, "mVerticalPadding: " + mVerticalPadding);
//        Log.e(TAG, "dy: " + dy);
//        Log.e(TAG, "values[Matrix.MTRANS_Y]: " + values[Matrix.MTRANS_Y]);

        //当前图片实际高度
        float nowHeight = intrinsicHeight * values[Matrix.MSCALE_Y];
        if (intrinsicHeight * values[Matrix.MSCALE_Y] <= height - mVerticalPadding * 2) {
            return 0;
        }
        //下移
        if (dy > 0) {
            if (dy < mVerticalPadding - values[Matrix.MTRANS_Y]) {
                return dy;
            } else {
                return mVerticalPadding - values[Matrix.MTRANS_Y];
            }
        }
        //上移
        if (dy < 0) {
            if (-(values[Matrix.MTRANS_Y] + dy) < nowHeight - (height - mVerticalPadding)) {
                return dy;
            } else {
                return -((nowHeight - (height - mVerticalPadding)) + values[Matrix.MTRANS_Y]);
            }
        }
        return dy;

    }

    /**
     * 和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
     *
     * @param values
     * @param dx
     * @return
     */
    private float checkDxBound(float[] values, float dx) {
        float width = imageView.getWidth();

//        Log.e(TAG, "intrinsicWidth * values[Matrix.MSCALE_X]: " + intrinsicWidth * values[Matrix.MSCALE_X]);
//        Log.e(TAG, "width: " + width);
//        Log.e(TAG, "mHorizontalPadding: " + mHorizontalPadding);
//        Log.e(TAG, "dx: " + dx);
//        Log.e(TAG, "values[Matrix.MTRANS_X]: " + values[Matrix.MTRANS_X]);
        //当前图片实际高度
        float nowWidth = intrinsicWidth * values[Matrix.MSCALE_X];
        if (intrinsicWidth * values[Matrix.MSCALE_X] <= width - mHorizontalPadding * 2) {
            return 0;
        }
        //右移
        if (dx > 0) {
            if (dx < mHorizontalPadding - values[Matrix.MTRANS_X]) {
                return dx;
            } else {
                return mHorizontalPadding - values[Matrix.MTRANS_X];
            }
        }
        //左移
        if (dx < 0) {
            if (-(values[Matrix.MTRANS_X] + dx) < nowWidth - (width - mHorizontalPadding)) {
                return dx;
            } else {
                return -((nowWidth - (width - mHorizontalPadding)) + values[Matrix.MTRANS_X]);
            }
        }
        return dx;
    }

//    /**
//     * MSCALE用于处理缩放变换
//     *
//     *
//     * MSKEW用于处理错切变换
//     *
//     *
//     * MTRANS用于处理平移变换
//     */
//
//    /**
//     * 检验scale，使图像缩放后不会超出最大倍数
//     *
//     * @param scale
//     * @param values
//     * @return
//     */
//    private float checkFitScale(float scale, float[] values) {
//        if (scale * values[Matrix.MSCALE_X] > mMaxScale)
//            scale = mMaxScale / values[Matrix.MSCALE_X];
//        if (scale * values[Matrix.MSCALE_X] < mMinScale)
//            scale = mMinScale / values[Matrix.MSCALE_X];
//        return scale;
//    }


    /**
     * 根据当前图片的Matrix获得图片的范围
     */
    private RectF getMatrixRectF(Matrix matrix) {
        RectF rect = new RectF();
        Drawable d = imageView.getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    /**
     * 获得当前的缩放比例
     */
    public final float getScale() {
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }


    /**
     * 多点触控时，计算最先放下的两指距离
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 多点触控时，计算最先放下的两指中心坐标
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


    /**
     * 获取剪切图
     */
    public Bitmap clip() {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Rect rect = clipView.getClipRect();
        Bitmap cropBitmap = null;
        Bitmap zoomedCropBitmap = null;
        try {
            cropBitmap = Bitmap.createBitmap(imageView.getDrawingCache(), rect.left, rect.top, rect.width(), rect.height());
            zoomedCropBitmap = zoomBitmap(cropBitmap, 720, 720);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cropBitmap != null) {
            cropBitmap.recycle();
        }
        // 释放资源
        imageView.destroyDrawingCache();
        return zoomedCropBitmap;
    }


    /**
     * 图片等比例压缩
     *
     * @param filePath
     * @param reqWidth  期望的宽
     * @param reqHeight 期望的高
     * @return
     */
    public static Bitmap decodeSampledBitmap(String filePath, int reqWidth,
                                             int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //bitmap is null
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算InSampleSize
     * 宽的压缩比和高的压缩比的较小值  取接近的2的次幂的值
     * 比如宽的压缩比是3 高的压缩比是5 取较小值3  而InSampleSize必须是2的次幂，取接近的2的次幂4
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            int ratio = heightRatio < widthRatio ? heightRatio : widthRatio;
            // inSampleSize只能是2的次幂  将ratio就近取2的次幂的值
            if (ratio < 3) {
                inSampleSize = ratio;
            } else if (ratio < 6.5) {
                inSampleSize = 4;
            } else if (ratio < 8) {
                inSampleSize = 8;
            } else {
                inSampleSize = ratio;
            }
        }

        return inSampleSize;
    }

    /**
     * 图片缩放到指定宽高
     * <p/>
     * 非等比例压缩，图片会被拉伸
     *
     * @param bitmap 源位图对象
     * @param w      要缩放的宽度
     * @param h      要缩放的高度
     * @return 新Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        return newBmp;
    }


}
