package com.jm.core.common.tools.utils;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;

/**
 * 订阅者模式
 *
 * @author jinXiong.Xie
 */
public class MyNotificationCenter {

    private MyNotificationCenter() {
    }

    private final String TAG = "MyNotificationCenter";
    private static int totalEvents = 1;

    public final static int CHANGE_NETWORK = totalEvents++;

    private int broadcasting = 0;

    private SparseArray<ArrayList<Object>> observers = new SparseArray<ArrayList<Object>>();
    private SparseArray<ArrayList<Object>> removeAfterBroadcast = new SparseArray<ArrayList<Object>>();
    private SparseArray<ArrayList<Object>> addAfterBroadcast = new SparseArray<ArrayList<Object>>();

    public interface NotificationCenterDelegate {
        void didReceivedNotification(int id, Object... args);
    }

    private static volatile MyNotificationCenter Instance = null;

    public static MyNotificationCenter getInstance() {
        if (Instance == null) {
            synchronized (MyNotificationCenter.class) {
                if (Instance == null) {
                    Instance = new MyNotificationCenter();
                }
            }
        }
        return Instance;
    }

    /**
     * 推送消息
     *
     * @param id
     * @param args
     */
    public void postNotificationById(int id, Object... args) {
        Log.d(TAG, "postNotificationById");
        broadcasting++;
        ArrayList<Object> objects = observers.get(id);
        Log.d(TAG, "" + observers.size());
        if (objects != null && !objects.isEmpty()) {
            for (int a = 0; a < objects.size(); a++) {
                Object obj = objects.get(a);
                ((NotificationCenterDelegate) obj).didReceivedNotification(id,
                        args);
            }
        }
        broadcasting--;
        if (broadcasting == 0) {
            if (removeAfterBroadcast.size() != 0) {
                for (int a = 0; a < removeAfterBroadcast.size(); a++) {
                    int key = removeAfterBroadcast.keyAt(a);
                    ArrayList<Object> arrayList = removeAfterBroadcast.get(key);
                    for (int b = 0; b < arrayList.size(); b++) {
                        removeObserver(arrayList.get(b), key);
                    }
                }
                removeAfterBroadcast.clear();
            }
            if (addAfterBroadcast.size() != 0) {
                for (int a = 0; a < addAfterBroadcast.size(); a++) {
                    int key = addAfterBroadcast.keyAt(a);
                    ArrayList<Object> arrayList = addAfterBroadcast.get(key);
                    for (int b = 0; b < arrayList.size(); b++) {
                        addObserver(arrayList.get(b), key);
                    }
                }
                addAfterBroadcast.clear();
            }
        }
    }

    /**
     * 添加订阅者
     *
     * @param observer
     * @param id
     */
    public void addObserver(Object observer, int id) {
        if (broadcasting != 0) {
            ArrayList<Object> arrayList = addAfterBroadcast.get(id);
            if (arrayList == null) {
                addAfterBroadcast.put(id, arrayList = new ArrayList<Object>());
            }
            if (arrayList.contains(observer)) {
                return;
            }
            arrayList.add(observer);
            return;
        }
        ArrayList<Object> objects = observers.get(id);
        if (objects == null) {
            observers.put(id, (objects = new ArrayList<Object>()));
        }
        if (objects.contains(observer)) {
            return;
        }
        objects.add(observer);
    }

    /**
     * 移除订阅者
     *
     * @param observer
     * @param id
     */
    public void removeObserver(Object observer, int id) {
        if (broadcasting != 0) {
            ArrayList<Object> arrayList = removeAfterBroadcast.get(id);
            if (arrayList == null) {
                removeAfterBroadcast.put(id,
                        arrayList = new ArrayList<Object>());
            }
            if (arrayList.contains(observer)) {
                return;
            }
            arrayList.add(observer);
            return;
        }
        ArrayList<Object> objects = observers.get(id);
        if (objects == null) {
            return;
        }
        objects.remove(observer);
    }
}

//implements MyNotificationCenter.NotificationCenterDelegate

//
//    @Override
//    protected void initReceiver() {
//        MyNotificationCenter.getInstance().addObserver(this,
//                MyNotificationCenter.REMOVE_FRIEND);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        MyNotificationCenter.getInstance().removeObserver(this,
//                MyNotificationCenter.REMOVE_FRIEND);
//    }
//


//
//    @Override
//    public void didReceivedNotification(int id, Object... args) {
//
//        if (id == MyNotificationCenter.UPDATA_FRIENDLIST) {
//            updateListFromDB();
//        }
//    }


//
//PocketApplication.getMainHandler().post(new Runnable() {
//
//@Override
//public void run() {
//
//        MyNotificationCenter.getInstance().postNotificationById(
//        MyNotificationCenter.UPDATA_FRIENDLIST);
//        }
//        });