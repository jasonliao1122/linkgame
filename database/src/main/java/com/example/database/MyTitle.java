package com.example.database;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/************************************************************
 * 标题功能  布局为 layout_title
 */
public class MyTitle {

    /*******************************************************************
     * 实例化标题
     * @param iContext  上下文句柄
     * @param iTitleName  标题名称
     * @param iShowBack  是否显示返回功能
     */
    public MyTitle(Context iContext,String iTitleName,boolean iShowBack)
    {
        //设置标题内容显示
        TextView tTitle=((Activity)iContext).findViewById(R.id.titletext);
        tTitle.setText(iTitleName);

        ImageView mBackIMG=((Activity)iContext).findViewById(R.id.titleimg);

        //设置返回功能
        mBackIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) iContext).finish();
            }
        });

        //不显示返回功能，返回ImageView 隐藏
        if(iShowBack==false)
            mBackIMG.setVisibility(View.GONE);
    }


}
