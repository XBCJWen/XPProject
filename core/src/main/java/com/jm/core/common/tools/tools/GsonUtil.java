package com.jm.core.common.tools.tools;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gson工具类
 *
 * @author jinXiong.Xie
 */

public class GsonUtil {
    private static Gson gson = null;

    static {
        initGson();
    }

    private GsonUtil() {
    }

    public static void initGson() {
        if (gson == null) {
            gson = new Gson();
        }
    }

//    /**
//     * 转成json
//     *
//     * @param object
//     * @return
//     */
//    public static String gsonString(Object object) {
//        String gsonString = null;
//        initGson();
//        if (gson != null) {
//            gsonString = gson.toJson(object);
//        }
//        return gsonString;
//    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        if (TextUtils.isEmpty(gsonString)) {
            return null;
        }
        T t = null;
        initGson();
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成bean
     *
     * @param jsonObject
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(JSONObject jsonObject, Class<T> cls) {
        return gsonToBean(jsonObject == null ? "" : jsonObject.toString(), cls);
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = new ArrayList<>();
        if (TextUtils.isEmpty(gsonString)){
            return list;
        }
        initGson();
        if (gson != null) {
            JsonArray array = new JsonParser().parse(gsonString).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }

//        List<T> beans = new Gson()
//                    .fromJson(dataObj.optJSONArray("list").toString(), new TypeToken<List<T>>() {
//                    }.getType());

        return list;
    }

    /**
     * 转成list
     *
     * @param jsonArray
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(JSONArray jsonArray, Class<T> cls) {
        return gsonToList(jsonArray == null ? "" : jsonArray.toString(), cls);
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        initGson();
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        initGson();
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }


    /**
     * 将对象转化为 json 格式的字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
