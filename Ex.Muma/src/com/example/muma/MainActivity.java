package com.example.muma;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView tv = new TextView(this);
        tv.setText("Hello,I Started!");
        this.setContentView(tv);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void initializeAudio(){
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile("/sdcard/test.amr");
		
		try{
			recorder.prepare();
			recorder.start();
		}
		catch(IllegalStateException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
			dos.writeBytes(twoHyphens+boundary+end);
			dos.writeBytes("Content-Disposition:form-data;name=\"file\";filename=\""+filename.substring(filename.lastIndwxOf("/")+1)+"\""+end);
			dos.writeBytes(end);
			FileInputStream fis = new FileInputStream(filename);
			byte[]buffer = new byte[8192];
			int count = 0;
			while((count = fis.read(buffer))!=-1){
				dos.write(buffer,0,count);
			}
			fis.close();
			dos.writeBytes(end);
			dos.writeBytes(twoHyphens+boundary+twoGyphens+end);
			dos.flush();
			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"utf-8");
			BufferedReader br = new BufferedReader(isr);
			String result = br.readLine();
			System.out.println(result);
			Toast.LENGTH_LONG).show();
			dos.close();
			}
		catch(Exception e){
			System.out.println("Î´ÕÒµ½Â¼ÏñÎÄ¼þ");
		}
	}
}
