package sms.ply;

  
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;  
import android.os.Bundle;  
import android.telephony.SmsMessage;
import android.widget.Toast; 



public class SMSReceiver extends BroadcastReceiver {   
private static final String strRes = "android.provider.Telephony.SMS_RECEIVED";
 @Override
  public void onReceive(Context context, Intent intent) {
        /*
         * 判断是否是SMS_RECEIVED事件被触发
          */
        if (intent.getAction().equals(strRes)) {
            StringBuilder sb = new StringBuilder();
           Bundle bundle = intent.getExtras();
             if (bundle != null) {
                 Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] msg = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                     msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                 }
                 for (SmsMessage currMsg : msg) {
                     sb.append("您收到了来自:【");
                    sb.append(currMsg.getDisplayOriginatingAddress());
                     sb.append("】\n的信息，内容：");
                    sb.append(currMsg.getDisplayMessageBody());
                 }
                 Toast toast = Toast.makeText(context, "收到了短消息: " + sb.toString(),Toast.LENGTH_LONG);   
                 toast.show(); 
                 System.out.println("888888888888888888888888888888");
             }
         }
     }  
} 





