package com.example.muma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final String ACTION = "android.intent.action.BOOT_COMPLETED";
		if(intent.getAction().equals(ACTION)){
			Intent sayHelloIntent = new Intent(context,MainActivity.class);
			sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(sayHelloIntent);
		}
	}

}
