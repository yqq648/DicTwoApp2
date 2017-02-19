package com.yqq.dictwoapp;

import android.app.Application;
import android.content.res.Resources;
import android.os.Environment;

import net.youmi.android.AdManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.jpush.android.api.JPushInterface;

public class _App extends Application {
	private String appId = "cc723fc721dea358";
	private String appSecret = "8a69a5d21d75bfb8";
	private boolean isTestModel = true;

	/***
	 * 单例
	 */
	private static _App ourInstance;

	public static _App getInstance() {
		return ourInstance;
	}

//	private _App() {
//	}
	/**
	 * [JPushInterface] action:init
	 .......
	 [PushService] Login succeed!
	 */

	@Override
	public void onCreate() {
		super.onCreate();
		//激光todo 初始化和设置debug
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		//
		ourInstance = this;
		new Thread(){
			@Override
			public void run() {
				copyDic();
			}
		}.start();
		AdManager.getInstance(this)
		.init(appId, appSecret, isTestModel);
	}

	private void copyDic(){
		//拷贝字典数据库文件
		try {
			File file = getFileStreamPath("dictionary.db");

			if (file.exists()&&file.length()==252928){//存在的情况下不用复制
				return;
			}
//			com.alibaba.fastjson.

			Resources resources = getResources();
			InputStream in = resources.openRawResource(R.raw.dictionary);
			//SDCARD根目录
			FileOutputStream out = new FileOutputStream(file);
			byte[] bytes = new byte[50*1024];
			int count;
			while ((count=in.read(bytes))!=-1){
				out.write(bytes,0,count);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
