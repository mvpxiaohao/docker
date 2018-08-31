package com.ex.button;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
private Button btn1 = null;
private Button btn2 = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
    }
    private OnClickListener listener = new OnClickListener()
    {
    	public void onClick(View v)
    	{
    		Button btn = (Button)v;
    		switch(btn.getId())
    		{
    		case R.id.btn1:
    			Toast.makeText(MainActivity.this, "你点击了按钮1", Toast.LENGTH_LONG).show();
    			break;
    		case R.id.btn2:
    			Toast.makeText(MainActivity.this, "你点击了按钮2", Toast.LENGTH_LONG).show();
        	}
    	}
      };
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
