package com.jm.core.common.tools.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences封装类
 * Created by Jinxiong.Xie on 2017/3/9 14:58.
 * 邮箱：173500512@qq.com
 */

public class SharedPreferencesTool {

  private SharedPreferences mPreferences;

  public SharedPreferencesTool(Context context, String fileName) {
    this(context, fileName, Context.MODE_PRIVATE);
  }

  public SharedPreferencesTool(Context context, String fileName, int mode) {
    mPreferences = context.getSharedPreferences(fileName, mode);
  }

  /**
   * 保存多个键值对
   */
  public void setParams(Object... objects) {
    if (objects.length % 2 != 0) {
      new Exception("参数个数不对，应为key1,value1,key2,value2...，2的倍数").printStackTrace();
      return;
    }
    SharedPreferences.Editor editor = mPreferences.edit();
    int index = 0;
    while (index < objects.length - 1) {
      putParam((String) objects[index++], objects[index], editor);
    }
    editor.apply();
  }

  /**
   * 保存一个键值对
   */
  public void setParam(String key, Object value) {

    SharedPreferences.Editor editor = mPreferences.edit();
    putParam(key, value, editor);
    editor.apply();
  }

  /**
   * 存放键值对
   *
   * @param key 键
   * @param value 值
   */
  private void putParam(String key, Object value, SharedPreferences.Editor editor) {
    String type = value.getClass().getSimpleName();
    if ("String".equals(type)) {
      editor.putString(key, (String) value);
    } else if ("Integer".equals(type)) {
      editor.putInt(key, (Integer) value);
    } else if ("Boolean".equals(type)) {
      editor.putBoolean(key, (Boolean) value);
    } else if ("Float".equals(type)) {
      editor.putFloat(key, (Float) value);
    } else if ("Long".equals(type)) {
      editor.putLong(key, (Long) value);
    }
  }

  /**
   * 获取值
   */
  public Object getParam(String key, Object defaultObject) {
    String type = defaultObject.getClass().getSimpleName();
    if ("String".equals(type)) {
      return mPreferences.getString(key, (String) defaultObject);
    } else if ("Integer".equals(type)) {
      return mPreferences.getInt(key, (Integer) defaultObject);
    } else if ("Boolean".equals(type)) {
      return mPreferences.getBoolean(key, (Boolean) defaultObject);
    } else if ("Float".equals(type)) {
      return mPreferences.getFloat(key, (Float) defaultObject);
    } else if ("Long".equals(type)) {
      return mPreferences.getLong(key, (Long) defaultObject);
    }
    return null;
  }

  /**
   * 清除里面全部数据
   */
  public void clearAll(){
    mPreferences.edit().clear().apply();
  }

  /**
   * 清除里面的某个字段
   * @param name
   */
  public void clear(String name) {
    mPreferences.edit().remove(name).commit();
  }
}
