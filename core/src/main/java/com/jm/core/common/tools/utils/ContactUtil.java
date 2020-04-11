package com.jm.core.common.tools.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.jm.core.common.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

import static com.jm.core.common.tools.base.StringUtil.isMobile;

/**
 * 联系人工具类
 *
 * @author jinXiong.Xie
 */

public class ContactUtil {

    private ContactUtil() {
    }

    /**
     * 获取所有拥有手机号的联系人
     *
     * @param context
     * @return
     */
    public static void getAllPhoneContacts(Context context, RequestCallBack requestCallBack) {
        if (requestCallBack != null) {
            requestCallBack.start();
        }
        List<ContactBean> listContacts = new ArrayList<ContactBean>();
        int id = 0;
        ContentResolver cr = context.getContentResolver();
        String[] mContactsProjection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID
        };

        String contactsId;
        String phoneNum;
        String name;
        long photoId;
        byte[] photoBytes = null;

        //查询contacts表中的所有数据
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, mContactsProjection, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                contactsId = cursor.getString(0);
                phoneNum = cursor.getString(1);
                name = cursor.getString(2);
                photoId = cursor.getLong(3);

                if (photoId > 0) {//有头像
                    Cursor cursorPhoto = cr.query(ContactsContract.RawContactsEntity.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO},
                            ContactsContract.RawContactsEntity.CONTACT_ID + " = ? and " + ContactsContract.RawContactsEntity.MIMETYPE + " = ? and " + ContactsContract.RawContactsEntity.DELETED + " = ?",
                            new String[]{contactsId, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE, "0"},
                            null);
                    if (cursorPhoto.moveToNext()) {
                        photoBytes = cursorPhoto.getBlob(0);
                    }
                    cursorPhoto.close();
                } else {
                    photoBytes = null;
                }

                // 对手机号码进行预处理（去掉号码前的+86、首尾空格、“-”号等）
                phoneNum = phoneNum.replaceAll("^(\\+86)", "");
                phoneNum = phoneNum.replaceAll("^(86)", "");
                phoneNum = phoneNum.replaceAll("-", "");
                phoneNum = phoneNum.replaceAll(" ", "");
                phoneNum = phoneNum.trim();
                // 如果当前号码是手机号码
                if (isMobile(phoneNum)) {
                    ContactBean user = new ContactBean();
                    user.setId(String.valueOf(id));
                    user.setName(name);
                    user.setMobile(phoneNum);
                    user.setContactPhoto(photoBytes);
                    listContacts.add(user);
                    id += 1;
                }
            }
        }
        cursor.close();
        if (requestCallBack != null) {
            requestCallBack.finish(listContacts);
        }
    }

    public interface RequestCallBack {
        /**
         * 开始
         */
        void start();

        /**
         * 结束
         *
         * @param beanList
         */
        void finish(List<ContactBean> beanList);
    }
}
