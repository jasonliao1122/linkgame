package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import jw.database.JwShared;

public class SPActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_p);

        Init();
    }

    private void Init()
    {
        //初始化标题内容
        MyTitle myTitle=new MyTitle(this,"SP数据存储",true);

        InitSharedPreferences();
    }

    /**************************************************************************
     * sharedpreferences 存储示例
     */

    private JwShared mJwShared;

    private void InitSharedPreferences()
    {
        mJwShared = new JwShared(this,"data");

        mJwShared.put("name","张三");
        mJwShared.put("age",18);
        mJwShared.put("english",78);
        mJwShared.put("android",80);

        mJwShared.commit();

        ((TextView)findViewById(R.id.sptext1)).setText("输入数据:\n name:张三 \n"+ "age:18 \n"+ "english:78 \n"+ "android:80 \n"
                );
    }

    public void ReadSP(View view) {
        String tStr="输出数据:\n";
        tStr+="name:"+mJwShared.get("name","")+" \n";
        tStr+="age:"+mJwShared.get("age",0)+" \n";
        tStr+="english:"+mJwShared.get("english",0)+" \n";
        tStr+="android:"+mJwShared.get("android",0)+" \n";

        ((TextView)findViewById(R.id.sptext2)).setText(tStr);
    }
}