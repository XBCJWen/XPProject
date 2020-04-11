package com.jm.core.common.tools.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.List;

/**
 *
 * 不经过第三方SDK分享
 *
 */
public class AndroidShare {

	private Context context;
	/** 文本类型 */
	public static int TEXT = 0;
	/** 图片类型 */
	public static int DRAWABLE = 1;

	public AndroidShare(Context context) {
		this.context = context;
	}

	/**
	 * 分享到QQ好友
	 *
	 * @param msgTitle
	 *            （分享标题）
	 * @param msgText
	 *            （分享内容）
	 * @param type
	 *            （分享类型）
	 * @param drawable
	 *            （分享图片，若分享类型为AndroidShare.TEXT，则可以为null）
	 */
	public void shareQQFriend(String msgTitle, String msgText, int type,
							  Drawable drawable) {

		shareMsg("com.tencent.mobileqq",
				"com.tencent.mobileqq.activity.JumpActivity", "QQ", msgTitle,
				msgText, type, drawable);
	}

	/**
	 * 分享到微信好友
	 *
	 * @param msgTitle
	 *            （分享标题）
	 * @param msgText
	 *            （分享内容）
	 * @param type
	 *            （分享类型）
	 * @param drawable
	 *            （分享图片，若分享类型为AndroidShare.TEXT，则可以为null）
	 */
	public void shareWeChatFriend(String msgTitle, String msgText, int type,
								  Drawable drawable) {

		shareMsg("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI", "微信",
				msgTitle, msgText, type, drawable);
	}

	/**
	 * 分享到微信朋友圈（分享朋友圈一定需要图片）
	 *
	 * @param msgTitle
	 *            （分享标题）
	 * @param msgText
	 *            （分享内容）
	 * @param drawable
	 *            （分享图片）
	 */
	public void shareWeChatFriendCircle(String msgTitle, String msgText,
										Drawable drawable) {

		shareMsg("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI",
				"微信", msgTitle, msgText, AndroidShare.DRAWABLE, drawable);
	}

	/**
	 * 分享到其它
	 *
	 * @param msgTitle
	 *            （分享标题）
	 * @param msgText
	 *            （分享内容）
	 * @param type
	 *            （分享类型）
	 * @param drawable
	 *            （分享图片，若分享类型为AndroidShare.TEXT，则可以为null）
	 */
	public void shareOthers(String msgTitle, String msgText, int type,
							Drawable drawable) {

		shareMsg("", "", "其他", msgTitle, msgText, type, drawable);
	}

	/**
	 * 点击分享的代码
	 *
	 * @param packageName
	 *            （包名）
	 * @param activityName
	 *            （类名）
	 * @param appname
	 *            （应用名）
	 * @param msgTitle
	 *            （标题）
	 * @param msgText
	 *            （内容）
	 * @param type
	 *            （发送类型：text or pic 微信朋友圈只支持pic）
	 */
	@SuppressLint("NewApi")
	private void shareMsg(String packageName, String activityName,
						  String appname, String msgTitle, String msgText, int type,
						  Drawable drawable) {
		if (!packageName.isEmpty() && !isAvilible(context, packageName)) {// 判断APP是否存在
			Toast.makeText(context, "请先安装" + appname, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		Intent intent = new Intent("android.intent.action.SEND");
		if (type == AndroidShare.TEXT) {
			intent.setType("text/plain");
		} else if (type == AndroidShare.DRAWABLE) {
			intent.setType("image/png");
			BitmapDrawable bd = (BitmapDrawable) drawable;
			Bitmap bt = bd.getBitmap();
			// if ((imgPath == null) || (imgPath.equals(""))) {
			// intent.setType("text/plain");
			// } else {
			// File f = new File(imgPath);
			// if ((f != null) && (f.exists()) && (f.isFile())) {
			// intent.setType("image/png");
			// intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
			// }
			// }
			final Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
					context.getContentResolver(), bt, null, null));
			intent.putExtra(Intent.EXTRA_STREAM, uri);
		}

		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (!packageName.isEmpty()) {
			intent.setComponent(new ComponentName(packageName, activityName));
			context.startActivity(intent);
		} else {
			context.startActivity(Intent.createChooser(intent, msgTitle));
		}
	}

	/**
	 * 判断相对应的APP是否存在
	 *
	 * @param context
	 * @param packageName(包名)(若想判断QQ，则改为com.tencent.mobileqq，若想判断微信，则改为com.tencent.mm，若想判断支付宝，则改为com.eg.android.AlipayGphone)
	 * @return
	 */
	public static boolean isAvilible(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();

		//获取手机系统的所有APP包名，然后进行一一比较
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < pinfo.size(); i++) {
			if (((PackageInfo) pinfo.get(i)).packageName
					.equalsIgnoreCase(packageName))
				return true;
		}
		return false;
	}
}
