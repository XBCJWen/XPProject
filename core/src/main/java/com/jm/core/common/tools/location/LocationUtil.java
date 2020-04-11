package com.jm.core.common.tools.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.io.IOException;
import java.util.List;

/**
 * 位置工具类
 *
 * @author xiaoYan
 */

public class LocationUtil {

    private LocationUtil() {
    }

    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    public static String getLngAndLat(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //从gps获取经纬度
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //判断是否有权限
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                } else {
                    //当GPS信号弱没获取到位置的时候又从网络获取
                    return getLngAndLatWithNetwork(context);
                }
            }

        } else {
            //判断是否有权限
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                //从网络获取经纬度
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

        }
        return longitude + "," + latitude;
    }

    /**
     * 从网络获取经纬度
     *
     * @param context
     * @return
     */
    public static String getLngAndLatWithNetwork(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }

        return longitude + "," + latitude;
    }

    /**
     * 获取详细地址
     *
     * @param context
     * @param location
     * @return
     */
    private static String getAddressFromLocation(final Context context, Location location) {
        //Geocoder初始化
        Geocoder geocoder = new Geocoder(context);
        //判断Geocoder地理编码是否可用
        boolean falg = Geocoder.isPresent();
        try {
            //获取纬度和经度
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            //根据经纬度获取地理信息
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                //返回当前位置，精度可调
                Address address = addresses.get(0);
                String sAddress;
                if (!TextUtils.isEmpty(address.getLocality())) {
                    if (!TextUtils.isEmpty(address.getFeatureName())) {
                        //存储 市 + 周边地址
//                        sAddress = address.getLocality() + "," + address.getFeatureName();
                        sAddress = address.getLocality() + "," + longitude + "," + latitude;

                        //address.getCountryName() 国家
                        //address.getPostalCode() 邮编
                        //address.getCountryCode() 国家编码
                        //address.getAdminArea() 省份
                        //address.getSubAdminArea() 二级省份
                        //address.getThoroughfare() 道路
                        //address.getSubLocality() 二级城市
                    } else {
                        sAddress = address.getLocality();
                    }
                } else {
                    sAddress = "定位失败";
                }
                return sAddress;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    static LocationListener locationListener = new LocationListener() {

        /**
         * Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         * @param provider
         * @param status
         * @param extras
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        /**
         * Provider被enable时触发此函数，比如GPS被打开
         * @param provider
         */
        @Override
        public void onProviderEnabled(String provider) {

        }

        /**
         * Provider被disable时触发此函数，比如GPS被关闭
         * @param provider
         */
        @Override
        public void onProviderDisabled(String provider) {

        }

        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         * @param location
         */
        @Override
        public void onLocationChanged(Location location) {
        }
    };
}
