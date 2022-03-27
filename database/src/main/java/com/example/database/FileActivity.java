package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import jw.database.JwFile;

/***********************************************
 * 文件读取
 */
public class FileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        Init();
    }

    private int mType=0; //内部外部复用同一个Activity 和 布局 mType--0 内部文件存储  1 外部文件存储
    private void Init()
    {
        Intent intent=getIntent();
        mType = intent.getIntExtra("data",0);
        MyTitle myTitle;
        if(mType==0)
        //初始化标题内容
         myTitle=new MyTitle(this,"内部文件存储",true);
        else
            myTitle=new MyTitle(this,"外部文件存储",true);

        mReadTextView=findViewById(R.id.readtext);
        mFileName=findViewById(R.id.filename);
        mWord=findViewById(R.id.word);

    }

    //显示操作内容,文件名，文件内容
    private TextView mReadTextView,mFileName,mWord;

    public void ReadFile(View view) {
        String text="从文件:"+mFileName.getText()+"  读取:\n"+"  内容为: \n";
        if(mType==0)
        {
            //内部文件读取
            text+=JwFile.Read(this,mFileName.getText().toString());
        }
        else
        {
            //外部文件读取
            text+=JwFile.ReadSD(mFileName.getText().toString());
        }

        mReadTextView.setText(text);
    }

    public void WriteFile(View view) {
        String text="写入文件:"+mFileName.getText()+"  内容为:"+mWord.getText();
        if(mType==0)
        {
            //内部文件写入
            if(JwFile.Write(this,mFileName.getText().toString(),mWord.getText().toString()))
            {
                text+="\n"+"写入成功";
            }
            else
            {
                text+="\n"+"写入失败";
            }
        }
        else
        {
            //外部文件写入
            if(JwFile.WriteSD(mFileName.getText().toString(),mWord.getText().toString()))
            {
                text+="\n"+"写入成功";
            }
            else
            {
                text+="\n"+"写入失败";
            }
        }

        mReadTextView.setText(text);
    }
}