package com.example.service;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.example.grehousemon.R;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
/**
 * 该线程用于播放音乐，当收到的数据不符合数据库中设置的规则表，则向该线程发送handler，触发播放音乐起警示作用
 * @author hust
 *
 */
public class monitorThread implements Runnable{
	//播放音乐线程

	private static Handler handler;
	public static Handler getHandler() {
		return handler;
	}

	public static void setHandler(Handler handler) {
		monitorThread.handler = handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.d("monitor", "monitor is run");
		Log.d("Music", "播放音乐222");
		Looper.prepare();
		Log.d("Music", "播放音乐333");	
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.obj.equals("Play")){
					//播放音乐;
					Log.d("Music", "播放音乐");
				}				
			}
		};	
		setHandler(handler);
		Looper.loop();

	}

	
}
