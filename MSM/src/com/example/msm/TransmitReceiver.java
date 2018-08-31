package com.example.msm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class TransmitReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());//ʱ��
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                String number = msg.getOriginatingAddress();
                String message = msg.getDisplayMessageBody();
                message = "ת���������ԣ�"+number+"\n"+"ת���������ݣ�"+message+"\n"+
                        "ת������ʱ�䣺"+receiveTime;
                Log.i("noco",message);
                String transmitNunmber = MainActivity.getSettingNote(context,"number");
                if (transmitNunmber.equals("")){//��һ�ΰ�װ���ʱ����û������ת�������ʱ��ת��

                }else {//����˺���
                    transmitMessageTo(transmitNunmber, message);
                }
            }
        }
    }

    public void transmitMessageTo(String phoneNumber,String message){//ת������
        SmsManager manager = SmsManager.getDefault();
        /** �зֶ��ţ�ÿ��ʮ��������һ�������ų������Ʋ�����ʮ��ֻ��һ�������ص����ַ�����List����*/
        List<String> texts =manager.divideMessage(message);//���������
        for(String text:texts){
            manager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }
}