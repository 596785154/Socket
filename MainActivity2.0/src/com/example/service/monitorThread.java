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
 * ���߳����ڲ������֣����յ������ݲ��������ݿ������õĹ����������̷߳���handler����������������ʾ����
 * @author hust
 *
 */
public class monitorThread implements Runnable{
	//���������߳�

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
		Log.d("Music", "��������222");
		Looper.prepare();
		Log.d("Music", "��������333");	
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.obj.equals("Play")){
					//��������;
					Log.d("Music", "��������");
				}				
			}
		};	
		setHandler(handler);
		Looper.loop();

	}

	
}
