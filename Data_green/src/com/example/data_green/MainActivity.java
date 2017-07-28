package com.example.data_green;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Thread thread_connect, thread_data;
	String inMsg;
	Socket socket;
	DataInputStream in = null;
	Boolean t = true, boo = true;
	DBAdapter db = new DBAdapter(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button start = (Button) findViewById(R.id.button1);
		Button stop=(Button)findViewById(R.id.button2);
		db.open();
		stop.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				db.close();
				System.out.println("关闭数据库：");
				finish();
			   
			}
			
		});
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				if (boo) {
					thread_connect.start();
					boo = !boo;
				} else {
					if(thread_connect.isAlive())
					{
						try{
						thread_connect.interrupted();
						thread_connect.stop();
						}catch(Exception e){
						System.out.println("关闭数据连接出现问题：");
						}
					}
					if(thread_data.isAlive())
					{
						try{
						thread_data.interrupted();
						thread_data.stop();
						}
						catch(Exception e){
							System.out.println("关闭数据接收出现问题");
						}
					}
					System.out.println("停止线程：");
				}
			}
		});
		thread_data = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根

				while (t) {
					System.out.println("开始线程：");
					try {
						Log.d("test", "1111");
						while (in != null) {
							inMsg = in.readUTF();
							if (inMsg!=null) {
								Log.i("TcpClient", "received: " + inMsg);
								System.out.println("获取输入流：" + inMsg);
								// 关闭连接
								String[] data = new String[3];
								data = inMsg.split(",");
								int data0 = Integer.parseInt(data[0]);
								System.out.println(data0);
								String data1 = data[1];
								System.out.println(data1);
								double data2 = Double.parseDouble(data[2]);
								System.out.println(data2);
								db.insert(data0, data1, data2);
								db.get_all_data();
								db.delete_data();
							} else {
								System.out.println("received为空：");
							}
						}
						Log.d("test", "2222");
					} catch (UnknownHostException e) {
						System.out.println("UnknownHostException");
						e.printStackTrace();
					} catch (IOException e) {
						System.out.println("IOException");
						e.printStackTrace();
					}
					try {
						thread_data.sleep(5000);
					} catch (InterruptedException e) {
						System.out.println("线程中断错误：");
					}
				}
			}
		});
		thread_connect = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				try {
					System.out.println("开始连接：");
					// 初始化Socket，TCP_SERVER_PORT为指定的端口，int类型
					socket = new Socket("192.168.1.161", 8888);
					System.out.println("初始化Socket：");
					in = new DataInputStream(socket.getInputStream());
					Log.d("test", "3333");
					thread_data.start();
				} catch (UnknownHostException e) {
					System.out.println("UnknownHostException");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("IOException");
					e.printStackTrace();
				}
			}
		});
	}

}
