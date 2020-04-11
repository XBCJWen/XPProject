package com.jm.xpproject.http;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jm.core.common.http.okhttp.ResultListener;
import com.jm.core.common.tools.base.CacheMapUtil;
import com.jm.core.common.tools.base.StringUtil;
import com.jm.core.common.tools.log.LogUtil;
import com.jm.core.common.tools.net.NetStatus;
import com.jm.core.common.tools.utils.VersionUtil;
import com.jm.core.common.widget.toast.MyToast;
import com.jm.xpproject.database.NetworkData;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 网络访问工具类
 *
 * @author jinXiong.Xie
 */

public class HttpTool {

    /**
     * get方式
     */
    public static final int GET = 0;
    /**
     * post方式
     */
    public static final int POST = 1;
    /**
     * 发送文件
     */
    public static final int FILE = 2;
    /**
     * 以POST方式发送Json
     */
    public static final int POST_JSON = 3;

    private static final String TAG = "HttpTool";

//    public final static int CONNECT_TIMEOUT = 180;
//    public final static int READ_TIMEOUT = 180;
//    public final static int WRITE_TIMEOUT = 180;

    private static HttpTool instance;
    private static OkHttpClient okHttpClient;
    private static SoftReference<Context> wrContext;

    public static HttpTool getInstance(Context context) {
        Context beforeContext = getContext();
        if (instance == null || beforeContext == null || beforeContext instanceof Application) {
            instance = new HttpTool(context);
        } else if (beforeContext instanceof Activity) {
            //假如绑定的Activity被销毁，则重新创建
            Activity activity = (Activity) beforeContext;
            if (activity.isFinishing()) {
                instance = new HttpTool(context);
            }
        }
        return instance;
    }

    private HttpTool(Context context) {
        wrContext = new SoftReference<>(context);
        if (getContext() == null) {
            return;
        }
        initOkHttpClient(wrContext.get());

    }

    /**
     * 初始化OkHttpClient
     *
     * @param context
     */
    private void initOkHttpClient(Context context) {
        //添加Cookie持久化
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
//            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
//            .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
//            .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .build();
    }

    /**
     * 只有参数的Post请求
     *
     * @param url      请求的url
     * @param map      请求的参数
     * @param listener 请求返回
     */
    public void httpLoadPost(final String url, Map<String, String> map,
                             final ResultListener listener) {
        new HttpTool.Builder().url(url).map(map).listener(listener).build(this);
    }


    /**
     * 只有参数的Post请求
     *
     * @param url      请求的url
     * @param map      请求的参数
     * @param listener 请求返回
     */
    public void httpLoadPostSaveData(final String url, Map<String, String> map,
                                     final ResultListener listener) {
        new HttpTool.Builder().url(url).map(map).listener(listener).saveNetworkData(true).build(this);
    }

    /**
     * 传输文件
     *
     * @param url      请求的url
     * @param map      请求的参数
     * @param listener 请求返回
     */
    public void httpLoadFile(final String url, Map<String, String> map, Map<String, File> fileMap,
                             final ResultListener listener) {
        new HttpTool.Builder().url(url).map(map).fileMap(fileMap).linkType(FILE).listener(listener).build(this);
    }

    /**
     * 只有参数的Post请求
     *
     * @param url      请求的url
     * @param map      请求的参数
     * @param listener 请求返回
     */
    public void httpLoadNoNetTipPost(final String url, Map<String, String> map,
                                     final ResultListener listener) {
        new Builder().url(url).map(map).listener(listener).showNoNetTip(false).build(this);
    }


    public static class Builder {
        /**
         * id标识
         */
        private int id;
        /**
         * true：在主线程运行，false：不在
         */
        private boolean runMain;
        /**
         * 是否显示无网络时的提示
         */
        private boolean showNoNetTip;
        /**
         * 无网络的提示
         */
        private String noNetTip;
        /**
         * 连接方式
         */
        private int linkType;
        /**
         * 访问的链接
         */
        private String url;
        /**
         * 普通传参
         */
        private Map<String, String> map;
        /**
         * 上传文件的参数
         */
        private Map<String, File> fileMap;
        /**
         * 联网回调
         */
        private ResultListener listener;
        /**
         * 附带的参数
         */
        private Object[] data;
        /**
         * 保存接口数据，一定时间间隔内，同个接口，不重复请求，直接返回所存储的接口数据
         */
        private int effectiveTime;
        /**
         * 保存网络数据
         */
        private boolean saveNetworkData;

        public Builder() {
            getDefault();
        }

        /**
         * 获取默认值
         */
        private void getDefault() {
            id = -1;
            runMain = true;
            showNoNetTip = true;
            noNetTip = null;
            linkType = POST;
            url = null;
            map = new HashMap<>();
            fileMap = new HashMap<>();
            listener = null;
            data = null;
            effectiveTime = -1;
            saveNetworkData = false;
        }


        public HttpTool.Builder id(int id) {
            this.id = id;
            return this;
        }

        public HttpTool.Builder runMain(boolean runMain) {
            this.runMain = runMain;
            return this;
        }

        public HttpTool.Builder showNoNetTip(boolean showNoNetTip) {
            this.showNoNetTip = showNoNetTip;
            return this;
        }

        public HttpTool.Builder noNetTip(String noNetTip) {
            this.noNetTip = noNetTip;
            return this;
        }

        public HttpTool.Builder linkType(int linkType) {
            this.linkType = linkType;
            return this;
        }

        public HttpTool.Builder url(String url) {
            this.url = url;
            return this;
        }

        public HttpTool.Builder map(Map<String, String> map) {
            this.map = map;
            return this;
        }

        public HttpTool.Builder fileMap(Map<String, File> fileMap) {
            this.fileMap = fileMap;
            return this;
        }


        public HttpTool.Builder listener(ResultListener listener) {
            this.listener = listener;
            return this;
        }

        public HttpTool.Builder data(Object[] data) {
            this.data = data;
            return this;
        }

        public HttpTool.Builder effectiveTime(int effectiveTime) {
            this.effectiveTime = effectiveTime;
            return this;
        }

        public HttpTool.Builder saveNetworkData(boolean saveNetworkData) {
            this.saveNetworkData = saveNetworkData;
            return this;
        }

        public void build(HttpTool httpTool) {
            Context context = getContext();

            if (httpTool == null && context == null) {
                return;
            }
            //判断网络是否可以接
            NetStatus.requestNetStatus(context, new NetStatus.NetStatusCallBack() {
                @Override
                public void onNet() {

                    requestHttpLoad(Builder.this);
                }

                @Override
                public void onNoNet() {
//                    listener.fail(id, null, new Exception("网络不给力，请检查网络设置。"), null);
//                    if (showNoNetTip) {
                        showNetError(showNoNetTip, noNetTip);
//                    } else {
//
//                    }

                    if (saveNetworkData){
                        requestHttpLoad(Builder.this);
                    }
                }
            });
        }

    }

    /**
     * 联网
     *
     * @param builder
     */
    private static void requestHttpLoad(Builder builder) {
        switch (builder.linkType) {
            case GET:
                requestHttpLoadGet(builder);
                break;
            case POST:
                requestHttpLoadPost(builder);
                break;
            case FILE:
                requestHttpLoadFile(builder);
                break;
            case POST_JSON:
                requestHttpLoadPostJson(builder);
                break;
            default:
                break;
        }
    }

    /**
     * 请求网络
     */
    private static void requestHttpLoadGet(Builder builder) {
        LogUtil.e("Request-url:" + builder.url + ",map:" + builder.map.toString());
        builder.listener.state(builder.id, true, builder.data);

        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : builder.map.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            try {
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(builder.map.get(key), "utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            pos++;
        }
        String requestUrl;
        if (TextUtils.isEmpty(tempParams.toString())) {
            requestUrl = builder.url;
        } else {
            requestUrl = String.format("%s?%s", builder.url, tempParams.toString());
        }
        LogUtil.e("Request-url:" + requestUrl);
        final Request request = addHeaders().url(requestUrl).build();

        enqueueLink(request, builder);

    }

    /**
     * 请求网络
     */
    private static void requestHttpLoadPost(Builder builder) {
        LogUtil.e("Request-url:" + builder.url + ",map:" + builder.map.toString());
        builder.listener.state(builder.id, true, builder.data);
        FormBody.Builder formBody = new FormBody.Builder();

        for (String key : builder.map.keySet()) {
            String value = builder.map.get(key);
            formBody.add(key, value);
        }
        FormBody body = formBody.build();
        Request request = new Request.Builder()
                .post(body)
                .url(builder.url)
                .build();

        enqueueLink(request, builder);
    }

    /**
     * 请求网络
     */
    private static void requestHttpLoadPostJson(Builder builder) {
        LogUtil.e("Request-url:" + builder.url + ",map:" + builder.map.toString());
        builder.listener.state(builder.id, true, builder.data);


        MediaType mediaType = MediaType.parse("application/json");
        //使用JSONObject封装参数
        JSONObject jsonObject = new JSONObject();
        try {
            for (String key : builder.map.keySet()) {
                String value = builder.map.get(key);
                if (!StringUtil.isEmpty(key) && !StringUtil.isEmpty(value)) {
                    jsonObject.put(key, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //创建RequestBody对象，将参数按照指定的MediaType封装
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .post(requestBody)
                .url(builder.url)
                .build();

        enqueueLink(request, builder);
    }

    /**
     * 请求网络
     */
    private static void requestHttpLoadFile(Builder builder) {
        LogUtil.e("Request-url:" + builder.url + ",map:" + builder.map.toString() + ",fileMap:" + builder.fileMap.toString());
        builder.listener.state(builder.id, true, builder.data);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加文件
        if (builder.fileMap != null) {
            for (Map.Entry<String, File> entry : builder.fileMap.entrySet()) {
                String key = entry.getKey();
                File value = entry.getValue();
                if (value == null || !value.exists()) {
                    continue;
                }
                RequestBody fileBody = RequestBody.create(
                        MediaType.parse(getMediaType(value.getName())), value);
                bodyBuilder.addFormDataPart(key, value.getName(), fileBody);

            }
        }
        //添加参数
        if (builder.map != null) {
            for (Map.Entry<String, String> entry : builder.map.entrySet()) {
                bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().url(builder.url).post(bodyBuilder.build()).build();
        enqueueLink(request, builder);
    }

    /**
     * 排队联网
     *
     * @param request
     */
    private static void enqueueLink(Request request, final Builder builder) {
        //本地不需要或者没有保存
        if (builder.effectiveTime == -1 || TextUtils.isEmpty(CacheMapUtil.getValue(builder.url, builder.effectiveTime))) {
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failure(call, e, builder);

                    if (builder.saveNetworkData) {
                        String linkData = requestNativeNetworkData(builder);
                        if (!TextUtils.isEmpty(linkData)) {
                            try {
                                success(new JSONObject(linkData), call, null, builder);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    builder.listener.state(builder.id, false, builder.data);
                    if (response.isSuccessful()) {
                        String s = response.body().string();
                        LogUtil.e("onResponse: " + builder.url + ":" + s);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(s);
                        } catch (JSONException e) {
                            builder.listener.fail(builder.id, call, e, builder.data);
                            e.printStackTrace();
                        }
                        if (obj == null) {
                            obj = new JSONObject();
                            try {
                                obj.put("code", 0);
                                obj.put("desc", "服务器连接异常");
                                obj.put("data", "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (obj.optInt("code") == 2) {
                            otherLogin(builder.listener, builder.runMain);

                        } else {
                            if (builder.effectiveTime != -1) {
                                CacheMapUtil.putValue(builder.url, obj.toString());
                            }
                            if (builder.saveNetworkData) {
                                saveNetworkData(builder, obj);
                            }
                            success(obj, call, response, builder);

                        }
                    } else {
                        LogUtil.e("服务器连接异常:" + response.toString());
//                    NetworkErrorException e = new NetworkErrorException();
//                    listener.fail(id, call, e, data);
                        builder.listener.fail(builder.id, call, new Exception("服务器连接异常"), builder.data);

                    }

                }
            });
        } else {
            simulationLink(builder);
        }

    }

    /**
     * 获取本地所存储的网络请求数据
     *
     * @param builder
     * @return
     */
    private static String requestNativeNetworkData(Builder builder) {

        String link = builder.url + builder.map.toString() + builder.fileMap.toString();

        List<NetworkData> networkDataList = LitePal.where("link = ?", link).find(NetworkData.class);
        if (networkDataList == null || networkDataList.size() <= 0) {
            return null;
        }
        return networkDataList.get(0).getLinkData();
    }

    /**
     * 保存访问网络返回的数据
     *
     * @param builder
     * @param jsonObject
     */
    private static void saveNetworkData(Builder builder, JSONObject jsonObject) {

        final NetworkData networkData = new NetworkData();
        networkData.setLink(builder.url + builder.map.toString() + builder.fileMap.toString());
        networkData.setLinkData(jsonObject.toString());

        //先删后增
        LitePal.deleteAllAsync(NetworkData.class, "link = ?", networkData.getLink()).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                networkData.save();
            }
        });
    }

    /**
     * 模拟联网获取数据并返回结果
     */
    public static void simulationLink(Builder builder) {
        builder.listener.state(builder.id, false, builder.data);
        try {
            success(new JSONObject(CacheMapUtil.getValue(builder.url)), null, null, builder);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 联网失败
     *
     * @param call
     * @param e
     */
    private static void failure(Call call, IOException e, Builder builder) {
        builder.listener.state(builder.id, false, builder.data);
        LogUtil.e("服务器连接异常:" + e.toString());
        builder.listener.fail(builder.id, call, e, builder.data);
    }

    /**
     * 返回结果成功
     *
     * @param obj
     * @param call
     * @param response
     */
    private static void success(final JSONObject obj, final Call call, final Response response, final Builder builder) {
        if (builder.runMain) {
            if (getContext() != null) {
                ((Activity) wrContext.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        builder.listener.success(builder.id, call, response, obj, builder.data);
                    }
                });
            }

        } else {
            builder.listener.success(builder.id, call, response, obj, builder.data);

        }
    }

    /**
     * 帐号已经在其它地方登录
     *
     * @param listener
     * @param runMain
     */
    private static void otherLogin(final ResultListener listener, boolean runMain) {
        if (runMain) {
            if (getContext() != null) {
                ((Activity) wrContext.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onOtherLogin();
                    }
                });
            }

        } else {
            listener.onOtherLogin();
        }
    }

    /**
     * 显示网络错误
     *
     * @param showNoNetTip
     * @param noNetTip
     */
    private static void showNetError(boolean showNoNetTip, String noNetTip) {
        if (showNoNetTip && wrContext != null && wrContext.get() != null) {
            if (TextUtils.isEmpty(noNetTip)) {
                MyToast.showToast(wrContext.get(), "网络不给力，请检查网络设置。");
            } else {
                MyToast.showToast(wrContext.get(), noNetTip);
            }
        }
    }

    /**
     * 获取Context
     *
     * @return
     */
    public static Context getContext() {
        Context context = null;
        if (wrContext != null && wrContext.get() != null) {
            context = wrContext.get();
        }
        return context;
    }


    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private static Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", VersionUtil.getAppVersionName(getContext()));
        return builder;
    }

    /**
     * 根据文件的名称判断文件的Mine值
     */
    private static String getMediaType(String fileName) {
        FileNameMap map = URLConnection.getFileNameMap();
        String contentTypeFor = map.getContentTypeFor(fileName);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}