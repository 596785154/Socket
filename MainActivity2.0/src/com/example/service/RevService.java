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
 * �÷������������ؽ���socket���ӣ����ӳɹ�
 * �������������̣߳����������̼߳������߳�
 * @author hust
 *
 */
public class RevService extends IntentService implements OnCompletionListener{
	//�����̷߳���

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
			Log.d("IP", "��ǰIP=="+MainActivity.IP);
			socket = new Socket(MainActivity.IP, 8888);
			
			Log.d("socket", "socket=="+socket);
			Log.d("service","RevService������");
		}catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//ִ���������Ӳ���
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//ִ���������ӹ���
			
			
		}
		if(socket!=null){
			
			//�������������������ݵ��߳�
			new Thread(new revDataThread(socket,MainActivity.getRevHandler(),context)).start();
			//�����������������߳�
			new Thread(new monitorThread()).start();
			play();
			Timer timer  = new Timer();
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                onStop();
	            }
	        }, 1000*60);
			
			//���������ط���������߳�
			new Thread(new sendDataThread(socket, context)).start();
			
		}else {
			Toast.makeText(context, "socket����ʧ�ܣ�����������IP", Toast.LENGTH_SHORT).show();
			Message msg = new Message();
			msg.what = 0x123;
			MainActivity.getRevHandler().sendMessage(msg);
			
		}
		
	}
	public void play(){
		try {
			
			mediaplayer=MediaPlayer.create(this,R.raw.ppppp);//��ʼ��Mediaplayer����
			mediaplayer.setOnCompletionListener(this);//......
			mediaplayer.start();//��ʼ����
	     } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	     } catch (IllegalStateException e) {
	        e.printStackTrace();
	     }
	}
	public void onStop(){
		mediaplayer.stop();//ֹͣ����
		mediaplayer.release();//�ͷ���Դ
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO �Զ����ɵķ������
		System.out.println("oncompletion111111");
		mediaplayer.start();//��ʼ����
		System.out.println("oncompletion222222");
	}

}
