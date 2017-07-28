package com.example.Tools;

import java.util.ResourceBundle.Control;

import android.util.Log;

public class getOrders {

	/**
	 * 
	 * @param type  ������������  0-->����   1-->����
	 * @param attr  Ҫ�ı������ Light,Wind,Sun,Liquid
	 * @param order ��������  ON-->��  OFF-->��
	 * @return
	 */
	public static String getManOrders(int type,String attr,String order){

		if(order.equals("ON")){
			order = "ffff01";
		}else {
			order = "ffff02";
		}
		String orderInfo = "";
		if(attr.equals("Light")){
			orderInfo = "light.write,"+order;
		}
		if(attr.equals("Wind")){
			if(type==0){
				orderInfo = "wind0.write,"+order;
			}else {
				orderInfo = "wind1.write,"+order;
			}
		}
		if(attr.equals("Sun")){
			orderInfo = "sun.write,"+order;
		}
		if(attr.equals("Liquid")){
			orderInfo = "liquid.write,"+order;
		}
		Log.d("tag", "manual :"+orderInfo);
		return orderInfo;
	}

	/**
	 * 
	 * @param type ����/��������  0-->����   1-->����
	 * @return String[]
	 *  ����:[0]ͨ����  control
	 *      [1]����
	 *      [2]��ʪ
	 *      [3]����
	 *      [4]��ʪ
	 *      [5]��ǿ
	 *  ����:[0]ͨ����  control
	 *      [1]����
	 *      [2]��ʪ
	 *      [3]��ǿ
	 */
	private static String[] getAutoData(int type){
		String[] datas = null;
		if(type==0){//����
			 datas = new String[4];
			 datas[0] = "control.write";
			 //��ѯ����
			/* datas[1] = 
			 datas[2] = 
			 datas[3] = */
		}else {//����
			datas = new String[6];
			 datas[0] = "control.write";
			 /*datas[2] = 
			 datas[3] = 
			 datas[4] = 
			 datas[5] = */
					 
			//��ѯ����
		}
		
		return datas;
	}
	/**
	 * 
	 * @param type �Զ�����ģʽ�µ���������  0-->����   1-->����
	 * @return String order �Զ������µ�����
	 */
	public static String getAutoOrder(int type){
		String order = "";
		String[] datas = getAutoData(type);
		for(int i=0;i<datas.length;i++){
			if(i==0){
				order += datas[i]+",";
			}
			order += datas[i]+";";
		}
		Log.d("tag", "auto :"+order);
		return order;
	}
	
	
}
