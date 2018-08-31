package com.example.msm;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final int CONTINUE = 3;//��������
    private final int CHANGE = 0;//�޸ĺ���
    private final int SAVE = 1;//�������
    private final int INPUT = 2;//�������
    private Button button;
    private EditText number;
    private boolean flag;
    private int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.action_settings);
        number = (EditText) findViewById(R.id.action_settings);
        flag = getSettingNote(this,"number").equals("");//�ж��Ƿ�Ϊ��һ�ν������

        if(flag){
            state = INPUT;
            buttonState(state);
        }else {
            state = CHANGE;
            buttonState(state);
        }

        number.setText(getSettingNote(this,"number"));//��ʾ�Ѿ������˵ĺ���
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = s.length();
                Log.i("noco",count+"");
                if(count > 0 && count <11){
                    state = CONTINUE;
                    buttonState(state);
                }else if (count == 11){
                    state = SAVE;
                    buttonState(state);
                }else {
                    button.setEnabled(false);
                }
                if (getSettingNote(MainActivity.this,"number").equals(s.toString())){
                    state = CHANGE;
                    buttonState(state);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberStr = number.getText().toString();
                if(numberStr.length() == 11){
                    if (getSettingNote(MainActivity.this,"number").equals(numberStr)){
                        number.setText("");
                        state = INPUT;
                        buttonState(state);
                    }else {
                        saveSettingNote(MainActivity.this,"number",numberStr);
                        state = CHANGE;
                        buttonState(state);
                        Toast.makeText(MainActivity.this,"�������ɹ���",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"��������������������룡",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buttonState(int state){
        switch (state){
            case INPUT:
                number.setText("");
                button.setText("�������");
                button.setEnabled(false);
                break;
            case SAVE:
                button.setText("�������");
                button.setEnabled(true);
                break;
            case CHANGE:
                button.setText("�޸ĺ���");
                button.setEnabled(true);
                break;
            case CONTINUE:
                button.setText("��������");
                button.setEnabled(false);
                break;
        }
    }

    public static void saveSettingNote(Context context,String key,String saveData){//��������
        SharedPreferences.Editor note = context.getSharedPreferences("number_save", Activity.MODE_PRIVATE).edit();
        note.putString(key, saveData);
        note.commit();
    }
    public static String getSettingNote(Context context,String key){//��ȡ��������
        SharedPreferences read = context.getSharedPreferences("number_save", Activity.MODE_PRIVATE);
        return read.getString(key, "");
    }

}
