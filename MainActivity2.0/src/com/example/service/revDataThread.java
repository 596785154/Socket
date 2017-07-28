package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;

import com.example.Tools.analysisData;
import com.example.grehousemon.MainActivity;

import android.R.integer;
import android.content.Context;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * 该线程在成功建立连接时启动，接受来自网关发送的临时数据，并存入数据库，
 * 与数据库规则表设置规则对比，若违规，向monitorThread发送handler，触发警报
 * @author hust
 *
 */
public class revDataThread implements Runnable {
	//获取数据线程，获取数据插入临时表

	private Handler handler;
	private InputStream inputStream;
	private BufferedReader br;
	private Context context;
	private Socket socket;
    private static long dayOriTime;
    private static long monthOriTime;
	private int times = 0;
	private int datatimes = 0;

	public static long getDayOriTime() {
		return dayOriTime;
	}

	public static void setDayOriTime(long dayOriTime) {
		revDataThread.dayOriTime = dayOriTime;
	}

	public static long getMonthOriTime() {
		return monthOriTime;
	}

	public static void setMonthOriTime(long monthOriTime) {
		revDataThread.monthOriTime = monthOriTime;
	}


	public revDataThread(Socket socket, Handler handler, Context context) {
		this.socket = socket;
		this.handler = handler;
		this.context = context;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.d("revData", "revData thread is Run");
		Log.d("revData", "*****");
		dayOriTime = System.currentTimeMillis();
		monthOriTime = System.currentTimeMillis();
		String data = "";
		while (true) {
			times++;
			
			try {
				//Log.d("tag", "times=="+times);
				inputStream = socket.getInputStream();
				br = new BufferedReader(new InputStreamReader(
						inputStream, Charset.forName("UTF-8")));
				
			
				data = br.readLine();

				if (data != null) {//接受底端上传的所有数据数据

					//用于存放超越规则的键值对
					datatimes++;
					HashMap<String, Integer> map = null;
					Log.d("tag", "dataTimes================"+times);
					Log.d("tag", "data==" + data);
					// 若收到的信息属于正常指定的指令信息，则进行解析
					// e.g: "FF,0010Temp.read,2013-12-23 21:10:00,18.456,FD;
					// FF,0011Temp.write,2013-12-23 21:10:00,18.00,FD;"
					
					// 将各模块数值分割
					String[] datas = analysisData.spiltData(data, ";");
					Log.d("datas", "datas.length=="+datas.length);
					for (int i = 0; i < datas.length; i++) {
						Log.d("data", "data["+i+"]=="+datas[i]);
						if (datas[i].startsWith("ff,")
								&& datas[i].endsWith("fd")) {
							// 分割并将各个通道的数据插入相应临时表
							String[] info = analysisData.spiltData(datas[i],
									",");
							Boolean isSuccess = analysisData.execInsert(info,
									context);
							map = analysisData.getOutLimit(info);
							if (isSuccess) {
								Log.d("success", "插入成功");
							}else{
								Log.d("success","插入失败");
							}
						}
					}
					Log.d("music", "map=="+map+"  map.length=="+map.size());
					if(!map.isEmpty()){
						//播放音乐
						Log.d("music", "map is not empty,send handler");
						Message msg = new Message();
						msg.obj = "Play";
						monitorThread.getHandler().sendMessage(msg);
						map = null;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}