package sms.ply;

  
import java.util.List;  
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sms.ply.SMSReceiver;
  
import android.app.Activity;  
import android.content.Intent;  
import android.net.Uri;  
import android.os.Bundle;  
import android.telephony.SmsManager;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.EditText;
import android.widget.Toast;  



/**
 * Usage:
 * <pre>
 * String crypto = SimpleCrypto.encrypt(masterpassword, cleartext)
 * ...
 * String cleartext = SimpleCrypto.decrypt(masterpassword, crypto)
 * </pre>
 * @author ferenc.hechler
 */
class SimpleCrypto {

        public static String encrypt(String seed, String cleartext) throws Exception  {
                byte[] rawKey = getRawKey(seed.getBytes());
                byte[] result = encrypt(rawKey, cleartext.getBytes());
                return toHex(result);
        }
        
        public static String decrypt(String seed, String encrypted) throws Exception {
                byte[] rawKey = getRawKey(seed.getBytes());
                byte[] enc = toByte(encrypted);
                byte[] result = decrypt(rawKey, enc);
                return new String(result);
        }

        private static byte[] getRawKey(byte[] seed) throws Exception {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
                sr.setSeed(seed);
            kgen.init(128, sr); // 192 and 256 bits may not be available
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            return raw;
        }

        
        private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(clear);
                return encrypted;
        }

        private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
                return decrypted;
        }

        public static String toHex(String txt) {
                return toHex(txt.getBytes());
        }
        public static String fromHex(String hex) {
                return new String(toByte(hex));
        }
        
        public static byte[] toByte(String hexString) {
                int len = hexString.length()/2;
                byte[] result = new byte[len];
                for (int i = 0; i < len; i++)
                        result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
                return result;
        }

        public static String toHex(byte[] buf) {
                if (buf == null)
                        return "";
                StringBuffer result = new StringBuffer(2*buf.length);
                for (int i = 0; i < buf.length; i++) {
                        appendHex(result, buf[i]);
                }
                return result.toString();
        }
        private final static String HEX = "0123456789ABCDEF";
        private static void appendHex(StringBuffer sb, byte b) {
                sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
        }
        
}





  
public class SmsActivity extends Activity {  
    /** Called when the activity is first created. */  
    private SMSReceiver recevier; 
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  

        recevier = new SMSReceiver(); 
        setComponent(); 

    }  

    
    
    private void setComponent() {  
        final EditText message = (EditText)findViewById(R.id.message); 
        final EditText phoneno = (EditText)findViewById(R.id.phoneno);

        final EditText attno = (EditText)findViewById(R.id.attno); 
     
        
        Button bt1 = (Button) findViewById(R.id.Button01);  
        bt1.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	 String smsContent = message.getText().toString(); 
                 String ply = null;
				try {
					ply = SimpleCrypto.encrypt("123456", smsContent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            	 // note: SMS must be divided before being sent    
                SmsManager sms = SmsManager.getDefault();  

				List<String> texts = sms.divideMessage(ply);  

                for (String text : texts) {  
                    sms.sendTextMessage(phoneno.getText().toString(), null, text, null, null);  

                }
                // note: not checked success or failure yet  
                Toast.makeText(  
                        SmsActivity.this,   
                        "加密短信已发送：" + ply,  
                        Toast.LENGTH_LONG ).show();  
            }  
        });    
        Button bt2 = (Button) findViewById(R.id.Button02);  
        bt2.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
        //        String smsContent = "102";  
            	 String smsContent = message.getText().toString(); 
            	 String cishu = attno.getText().toString();
            	 int number = Integer.valueOf(cishu).intValue();
                // note: SMS must be divided before being sent    
                SmsManager sms = SmsManager.getDefault();  
                List<String> texts = sms.divideMessage(smsContent);  
                int i = 0;
                while (i < number)
                {
                for (String text : texts) {  
                    sms.sendTextMessage(phoneno.getText().toString(), null, text, null, null);  
                }
                i = i + 1;
                
                }
                // note: not checked success or failure yet  
                Toast.makeText(  
                        SmsActivity.this,   
                        "短信已发送",  
                        Toast.LENGTH_SHORT ).show();  
            }  
        });  
          
  
    }  
} 