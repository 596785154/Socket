package com.example.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import com.example.Tools.getOrders;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
/**
 * ���߳�������׶˷������������ֶ��Զ�����Ӧ����
 * @author hust
 *
 */
public class sendDataThread implements Runnable {
	//��׶˷�������

	private Socket socket;
	private OutputStream outputStream;
	private PrintWriter pw;
	private Context context;
	private static Handler handler;

	public static Handler getHandler() {
		return handler;
	}

	public static void setHandler(Handler handler) {
		sendDataThread.handler = handler;
	}

	public sendDataThread(Socket socket, Context context) {
		this.socket = socket;
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Log.d("sendData", "sendData is Run");

		try {
			outputStream = socket.getOutputStream();
			pw = new PrintWriter(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��������
		Looper.prepare();
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub

				int type = msg.what;
                Log.d("tag", "tag try");
				switch (type) {
				case 0x00://���ڷ��͵�ǰ�ֶ�/�Զ�ģʽ;
					if(msg.obj.toString().equals("manual")){
						pw.write("control.write;manual");
						pw.flush();
						Toast.makeText(context, "control.write,manual", Toast.LENGTH_SHORT).show();
					}
					if(msg.obj.toString().equals("auto")){
						pw.write("control.write;auto");
						pw.flush();
						Toast.makeText(context, "control.write,auto", Toast.LENGTH_SHORT).show();
					}
						
				case 0x1010:// �ֶ�����
					if(msg.obj.toString().equals("LightOn")){
						pw.write(getOrders.getManOrders(1, "Light", "ON"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� LightOn", Toast.LENGTH_SHORT).show();
					}
						
					if(msg.obj.toString().equals("LightOff")){
						pw.write(getOrders.getManOrders(1, "Light", "OFF"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� LightOff", Toast.LENGTH_SHORT).show();
					}
						
					if(msg.obj.toString().equals("SunOn")){
						pw.write(getOrders.getManOrders(1, "Sun", "ON"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� SunOn", Toast.LENGTH_SHORT).show();

					}
					if(msg.obj.toString().equals("SunOff")){
						pw.write(getOrders.getManOrders(1, "Sun", "OFF"));
						pw.flush();							
						Toast.makeText(context, "�ֶ����� SunOff", Toast.LENGTH_SHORT).show();

					}
					if(msg.obj.toString().equals("WindOn")){
						pw.write(getOrders.getManOrders(1, "Wind", "ON"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� WindOn", Toast.LENGTH_SHORT).show();

					}
					if(msg.obj.toString().equals("WindOff")){
						pw.write(getOrders.getManOrders(1, "Wind", "OFF"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� WindOff", Toast.LENGTH_SHORT).show();

					}
					if(msg.obj.toString().equals("LiquidOn")){
						pw.write(getOrders.getManOrders(1, "Liquid", "ON"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� LiquidOn", Toast.LENGTH_SHORT).show();

					}
					if(msg.obj.toString().equals("LiquidOff")){
						pw.write(getOrders.getManOrders(1, "Liquid", "OFF"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� LiquidOff", Toast.LENGTH_SHORT).show();

					}
					
					break;

				case 0x1011://�ֶ�����

					if(msg.obj.toString().equals("LightOn")){
						pw.write(getOrders.getManOrders(0, "Light", "ON"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� LightOn", Toast.LENGTH_SHORT).show();
					}
						
					if(msg.obj.toString().equals("LightOff")){
						pw.write(getOrders.getManOrders(0, "Light", "OFF"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� LightOff", Toast.LENGTH_SHORT).show();
					}
						
					if(msg.obj.toString().equals("SunOn")){
						pw.write(getOrders.getManOrders(0, "Sun", "On"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� SunOn", Toast.LENGTH_SHORT).show();

					}
					if(msg.obj.toString().equals("SunOff")){
						pw.write(getOrders.getManOrders(0, "Sun", "OFF"));
						pw.flush();							
						Toast.makeText(context, "�ֶ����� SunOff", Toast.LENGTH_SHORT).show();

					}
					if(msg.obj.toString().equals("WindOn")){
						pw.write(getOrders.getManOrders(0, "Wind", "ON"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� WindOn", Toast.LENGTH_SHORT).show();

					}
					if(msg.obj.toString().equals("WindOff")){
						pw.write(getOrders.getManOrders(0, "Wind", "OFF"));
						pw.flush();
						Toast.makeText(context, "�ֶ����� WindOff", Toast.LENGTH_SHORT).show();

					}
					break;
				case 0x1110://�Զ�����
					pw.write(getOrders.getAutoOrder(1));
					pw.flush();
					Toast.makeText(context, "�ѽ����Զ�����ģʽ", Toast.LENGTH_SHORT).show();
					break;
				case 0x1111://�Զ�����
					/*Bundle bundle = msg.getData();
					String tempMin = bundle.getString("tempMin");
					String tempMax = bundle.getString("tempMax");

					String humiMin = bundle.getString("humiMin");
					String humiMax = bundle.getString("humiMax");

					String lightMin = bundle.getString("lightMin");
					String lightMax = bundle.getString("lightMax");

					String info = "Atuo;0111Temp,"+tempMin+","+tempMax+";0111Humi,"+humiMin+","+humiMax+";Light,"+lightMin+","+lightMax+";";*/
					pw.write(getOrders.getAutoOrder(0));
					pw.flush();
					Toast.makeText(context, "�ѽ����Զ�����ģʽ", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}

			}

		};
		setHandler(handler);
		Looper.loop();
	}
}
