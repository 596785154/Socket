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
				// TODO �Զ����ɵķ������
				db.close();
				System.out.println("�ر����ݿ⣺");
				finish();
			   
			}
			
		});
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
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
						System.out.println("�ر��������ӳ������⣺");
						}
					}
					if(thread_data.isAlive())
					{
						try{
						thread_data.interrupted();
						thread_data.stop();
						}
						catch(Exception e){
							System.out.println("�ر����ݽ��ճ�������");
						}
					}
					System.out.println("ֹͣ�̣߳�");
				}
			}
		});
		thread_data = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO �Զ����ɵķ������

				while (t) {
					System.out.println("��ʼ�̣߳�");
					try {
						Log.d("test", "1111");
						while (in != null) {
							inMsg = in.readUTF();
							if (inMsg!=null) {
								Log.i("TcpClient", "received: " + inMsg);
								System.out.println("��ȡ��������" + inMsg);
								// �ر�����
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
								System.out.println("receivedΪ�գ�");
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
						System.out.println("�߳��жϴ���");
					}
				}
			}
		});
		thread_connect = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				try {
					System.out.println("��ʼ���ӣ�");
					// ��ʼ��Socket��TCP_SERVER_PORTΪָ���Ķ˿ڣ�int����
					socket = new Socket("192.168.1.161", 8888);
					System.out.println("��ʼ��Socket��");
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
