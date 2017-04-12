package com.bravesoft.bravegogo.utils;

import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetWorkUtils {
	/**
	 * 
	 * @param context
	 * @return 记住要开权限
	 * <!-- 读取网络状态权限 -->
     *<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     *<!--网络状态发生变化权限 -->
     *<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
	 * 
	 */
	public static boolean isConnect(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 通过网络管理器对象获取到网络信息对象
		NetworkInfo info = manager.getActiveNetworkInfo();
		// 如果网络信息不为空，并且可以获取到，并且已经连接上或者正在连接
		if (info != null && info.isAvailable()&& info.isConnectedOrConnecting()) {
			String type = info.getTypeName();
			Log.i("info", "网络类型为："+type);
			return true;

		} else {
			return false;
		}
	}
}
