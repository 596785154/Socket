package com.example.servicetest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.string;
import android.app.Activity;
import android.text.format.Formatter;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SteMainActivity extends Activity {

	private int tcpSPORT = 8888;
	ServerSocket tcpsso;
	Socket tcpscso;
	Boolean boo = true;
	OutputStream output_one = null;
	DataOutputStream dos;
	Button b_show, b_link;
	TextView t_one, t_two;
	String string = "";
	String t_string = "";
	Boolean boo_S = true;
	Handler h_oneHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			string += msg.getData().getString("str") + "\n";
			t_two.setText(string);
			/*t_string = msg.getData().getString("t_str") + "\n";
			if(!t_string.equals("null"))
			{
			b_link.setText(t_string);
			}*/
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ste_main);
		b_link = (Button) findViewById(R.id.button2);
		b_show = (Button) findViewById(R.id.button1);
		t_one = (TextView) findViewById(R.id.textView1);
		t_two = (TextView) findViewById(R.id.textView2);
		// WifiManager wifiManager = (WifiManager)
		// getSystemService(WIFI_SERVICE);
		// WifiInfo info = wifiManager.getConnectionInfo();
		// String ip = "wifiInfo :" +
		// Formatter.formatIpAddress(info.getIpAddress());
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					if (tcpscso != null) {
						try {
							/*if(boo_S)
							{
							Message message_s = new Message();
							Bundle bundle_s = new Bundle();
							bundle_s.putString("t_str", "开始发送数据");
							message_s.setData(bundle_s);
							h_oneHandler.sendMessage(message_s); 
							boo_S=false;
							}*/
							
							Thread.sleep(5000);
							String str = str();
							dos.writeUTF(str);
							dos.flush();
							Message message = new Message();
							Bundle bundle = new Bundle();
							bundle.putString("str", str);
							message.setData(bundle);
							h_oneHandler.sendMessage(message);
							Log.d("test", "发送" + str);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
		b_show.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t_one.setText(SteMainActivity.this.getLocalIpAddress());
			}
		});
		b_link.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (boo) {
					new ServerThread().start();
				} else {
					try {
						output_one.close();
						dos.close();
						tcpscso.close();
						tcpsso.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	
	}

	public String str() {
		String str_one = "";
		Random r_1 = new Random();
		int a = r_1.nextInt(5) + 1;
		str_one += a + ",";
		long t_1 = System.currentTimeMillis();
		str_one += t_1 + ",";
		float f_1 = 40 * r_1.nextFloat();
		str_one += f_1 + "";

		return str_one;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ste_main, menu);
		return true;
	}

	public String getLocalIpAddress() {
		String ipaddress = "";

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						ipaddress = ipaddress + ";"
								+ inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return ipaddress;
	}

	private class ServerThread extends Thread {
		public void run() {
			initServer();
		}

		void initServer() {

			try {
				tcpsso = new ServerSocket(tcpSPORT);
				Log.d("axc", "开始阻塞");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				
				/*Message message = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("t_str", "等待客户端连接");
				message.setData(bundle);
				h_oneHandler.sendMessage(message);*/
				
				tcpscso = tcpsso.accept();
				output_one = tcpscso.getOutputStream();
				dos = new DataOutputStream(output_one);
				Log.d("axc", "服务器端tcpscso创建");
			} catch (IOException e) {
				// TODO Auto-generated 00catch block
				e.printStackTrace();
			}
		}
	}

}
