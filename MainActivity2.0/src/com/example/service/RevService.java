package com.example.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import com.example.grehousemon.MainActivity;
import com.example.grehousemon.R;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * 该服务用于与网关建立socket连接，连接成功
 * 开启接收数据线程，发送命令线程及监听线程
 * @author hust
 *
 */
public class RevService extends IntentService implements OnCompletionListener{
	//连接线程服务

	private static Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	MediaPlayer mediaplayer;
    private Context context; 
	public RevService() {
		super("RevService");
		
	}	

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		socket = socket;
	}	
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("RevService","OnHandleTntent");
		context = getBaseContext();
		try {
			Log.d("IP", "当前IP=="+MainActivity.IP);
			socket = new Socket(MainActivity.IP, 8888);
			
			Log.d("socket", "socket=="+socket);
			Log.d("service","RevService被创建");
		}catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//执行重新连接操作
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//执行重新连接功能
			
			
		}
		if(socket!=null){
			
			//开启接受来自网关数据的线程
			new Thread(new revDataThread(socket,MainActivity.getRevHandler(),context)).start();
			//开启监听播放音乐线程
			new Thread(new monitorThread()).start();
			play();
			Timer timer  = new Timer();
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                onStop();
	            }
	        }, 1000*60);
			
			//开启向网关发送命令的线程
			new Thread(new sendDataThread(socket, context)).start();
			
		}else {
			Toast.makeText(context, "socket建立失败，请重新设置IP", Toast.LENGTH_SHORT).show();
			Message msg = new Message();
			msg.what = 0x123;
			MainActivity.getRevHandler().sendMessage(msg);
			
		}
		
	}
	public void play(){
		try {
			
			mediaplayer=MediaPlayer.create(this,R.raw.ppppp);//初始化Mediaplayer对象
			mediaplayer.setOnCompletionListener(this);//......
			mediaplayer.start();//开始播放
	     } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	     } catch (IllegalStateException e) {
	        e.printStackTrace();
	     }
	}
	public void onStop(){
		mediaplayer.stop();//停止播放
		mediaplayer.release();//释放资源
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO 自动生成的方法存根
		System.out.println("oncompletion111111");
		mediaplayer.start();//开始播放
		System.out.println("oncompletion222222");
	}

}
