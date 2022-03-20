package com.example.linkgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FaceMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_main);
        //获得屏幕显示分辨率
        GetLcdSize();
        //初始化算法
        InitAlgorithm();
        //初始化界面
        InitView();
        //重新显示图片
        ResetImageDate();

        //显示游戏时间
        mTimeView = findViewById(R.id.time);
        RestGameTime();
        //启动游戏时间
        mGameTime.start_time();

        //显示游戏获得金币
        InitGoldView();
    }

    /***************************************************
     * 为了动态创建组件，先获取屏幕尺寸，用于动态适配屏幕
     */
    private int mLCDW,mLCDH; //屏幕分辨率 宽和高
    private void GetLcdSize() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mLCDW = outMetrics.widthPixels;
        mLCDH = outMetrics.heightPixels;
        Log.i("jason", "widthPixels = " + mLCDW + ",heightPixels = " + mLCDH);
    }

    /**************************************************
     * 连连看主界面动态实现
     * A*B的图像表
     */
    private RelativeLayout mMainLayout; //界面最外围RelativeLayout布局
    private RelativeLayout mFaceLayout; //包围A*B图像表的布局
    //private int mA=5,mB=6; //表格的行和列
    private int mA=6,mB=7,mDiff=4; //表格的列 行 难度（重复出现的图标个数2的倍数）
    private ImageView[] mImgItem = new ImageView[mA*mB]; //A*B图像表的图像
    private int mImgW,mImgH,mMid=2; //图片的大小,两张图片之间的间隔
    private int mBaseID=0x999; //控件的基础ID
    //图片资源ID 放入数组便于管理
    private int[] mImgID={
            R.drawable.sg1,R.drawable.sg2,R.drawable.sg3,R.drawable.sg4,R.drawable.sg5,
            R.drawable.sg6,R.drawable.sg7,R.drawable.sg8,R.drawable.sg9,R.drawable.sg10,
            R.drawable.sg11,R.drawable.sg12,R.drawable.sg13,R.drawable.sg14,R.drawable.sg15,
            R.drawable.sg16,
    };

    /************************
     * 初始化所有控件元素
     */
    private void InitView()
    {
        //获取界面最外围的RelativeLayout布局
        mMainLayout=findViewById(R.id.main_layout);

        //新建一个RelativeLayout
        mFaceLayout=new RelativeLayout(this);

        /***布局文件居中属性 ****/
        RelativeLayout.LayoutParams rlp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);//addRule参数对应RelativeLayout XML布局的属性

        //布局加入系统主布局中
        mMainLayout.addView(mFaceLayout,rlp);

        InitImageView();
    }

    private void InitImageView()
    {
        mImgW = (mLCDW-(mMid*mA*2)-20)/mA; //动态计算图片的大小
        mImgH = mImgW;
        /****初始化详细Image，并加入布局 ***/
        for(int j=0;j<mB;j++)
            for(int i=0;i<mA;i++)
            {
                int id=i+j*mA;
                Log.d("jason","InitView id="+id);
                //实例化图像控件
                mImgItem[id]=new ImageView(this);
                //把图像赋予控件
                mImgItem[id].setImageDrawable(getDrawable(mImgID[0]));
                mImgItem[id].setBackground(getDrawable(R.drawable.bkcolor1));

                //设置ID
                mImgItem[id].setId(mBaseID+id);

                //控件加载到布局
                mFaceLayout.addView(mImgItem[id]);

                //设置图片点击事件
                mImgItem[id].setOnClickListener(mClick);

                /****设置图片的 宽，高 位置*****/

                RelativeLayout.LayoutParams para1;
                para1 = (RelativeLayout.LayoutParams)mImgItem[id].getLayoutParams();
                para1.width = mImgW;
                para1.height = mImgH;
                para1.setMargins(mMid,mMid,mMid,mMid);

                if(i==0&&j==0) {
                    //第一个位于最左边
                }
                else if(i==0)
                {
                    //换行 在上一行下方
                    para1.addRule(RelativeLayout.BELOW,mImgItem[(j-1)*mA].getId());
                }
                else if(i>0&&j==0)
                {
                    //在上一个控件右方
                    para1.addRule(RelativeLayout.RIGHT_OF ,mImgItem[id-1].getId());
                }
                else if(i>0&&j>0)
                {
                    //在上一个控件右方
                    para1.addRule(RelativeLayout.RIGHT_OF ,mImgItem[id-1].getId());
                    //换行 在上一行下方
                    para1.addRule(RelativeLayout.BELOW,mImgItem[(j-1)*mA].getId());
                }
                mImgItem[id].setLayoutParams(para1);


            }

    }

    /************* 图片点击事件 ****************/
    private View.OnClickListener mClick = new View.OnClickListener()
    {

        @Override
        public void onClick(View v) {
            if(mDelayTime.IsRuning==true)
            {
                //定时器开启的时候，点击无效
                return;
            }
            if(mAlgorithm.IsDown(v.getId()-mBaseID)) {
                v.setBackground(getDrawable(R.drawable.bkcolor2));
            }
            //获取两张图片按下的状态
            int type=mAlgorithm.IsDowbleDown();
            Log.d("jason","GetTs IsDowbleDown="+type);
            if(type==1)
            {
                //两张图片均与按下，但是未联通
                //重新设置图片状态
                ResetImageDate();
            }
            else if(type==2)
            {
                //两张图片均已按下，并且两张图片能够消除
                //启动定时器 定时器完成消除图片
                mDelayTime.start_time();

                //获得金币增加
                mGold+=100;
                //刷新金币显示
                ShowGold();
            }
        }
    };


    /******************************************************
     * 重新设置图片显示内容
     */
    private void ResetImageDate()
    {
        for(int j=0;j<mB;j++)
            for(int i=0;i<mA;i++)
        {
            int id1=j*mA+i;
            int id2=(j+1)*(mA+2)+i+1;

            if(mAlgorithm.mData[id2].mImgID==0)
            {
                //已消除图片 显示透明图片
                mImgItem[id1].setImageDrawable(getDrawable(R.drawable.sg0));
                mImgItem[id1].setBackground(getDrawable(R.drawable.sg0));
            }
            else
            {
                //重新显示图片
                mImgItem[id1].setImageDrawable(getDrawable(mImgID[mAlgorithm.mData[id2].mImgID]));
                mImgItem[id1].setBackground(getDrawable(R.drawable.bkcolor1));
            }
        }
    }

    /**************************************************
     * 提示功能
     * @param i
     */
    public void GetTs(View i)
    {
        Log.d("jason","GetTs mAlgorithm.GetRight");
        if(mAlgorithm.GetRight()==true)
        {
            Log.d("jason","GetTs mAlgorithm.GetRight true");
            //找到两个完全匹配的图片
            mImgItem[ChageID(mAlgorithm.mFID1)].setBackground(getDrawable(R.drawable.bkcolor2));
            mImgItem[ChageID(mAlgorithm.mFID2)].setBackground(getDrawable(R.drawable.bkcolor2));
            //启动定时器
            mDelayTime.start_time();
        }
    }

    /*************************************************
     * 返回功能
     * @param i
     */
    public void GoToBack(View i)
    {
        finish();
    }

    //算法图片位置转换成显示的位置  算法位置比显示位置上下左右多一行列
    private int ChageID(int id)
    {
        int mx1=id%(mA+2);
        int my1=id/(mA+2);

        int rid=(my1-1)*mA+(mx1-1);
        return rid;
    }


    //开启定时器 -- 两个图片选择之后延时 0.5秒 取消两个图片
    private JwTime mDelayTime = new JwTime(500)
    {
        @Override
        public int run() {
            //定时器到时间执行
            stop_time();
            //重新设置图片状态
            mAlgorithm.RestDate(mAlgorithm.mFID1,0);
            mAlgorithm.RestDate(mAlgorithm.mFID2,0);

            //刷新图片
            ResetImageDate();

            if(mAlgorithm.IsWin()==true)
            {
                //游戏胜利
                ShowWin();
                mGameTime.stop_time();
            }
            return super.run();
        }
    };

    /***************************************************
     * 游戏时间定时器 开始时间60秒，60秒内完成游戏,每秒更新时间
     */
    private int mMaxTime=60,mTime;
    private TextView mTimeView;  //显示游戏时间的控件
    private JwTime mGameTime = new JwTime(1000)
    {
        @Override
        public int run() {
            mTime--;
            if(mTime<=0)
            {
                //游戏时间到，游戏结束
                super.stop_time();
                ShowLost();
            }
            else
            {
                start_time();
            }
            //重新刷新游戏时间
            RestGameTime();
            return super.run();
        }
    };

    /********************************************
     * 重新刷新游戏时间
     */
    private void RestGameTime()
    {
        mTimeView.setText("时间: "+mTime+" 秒");
    }

    /**********************************************
     * 游戏金币的获得
     */
    private int mGold = 0;
    private TextView mGoldText;  //显示金币的文本控件
    private void InitGoldView()
    {
        mGoldText = findViewById(R.id.gold);
        ShowGold();
    }
    private void ShowGold()
    {
        mGoldText.setText(""+mGold);
    }
    /**************************************************
     * 连连看主界面实现结束
     */

    /**********************************************************************
     * 连连看算法功能部分
     */
    private Algorithm mAlgorithm;

    //初始化算法
    private void InitAlgorithm()
    {
        mAlgorithm = new Algorithm(mA,mB,mDiff);
        //重置游戏时间
        mTime=mMaxTime;
    }


    /******************************************************************
     * 游戏结束对话框
     * type 0--胜利界面 1--失败界面
     * ***/
    private void ShowDialog(int type) {

        /***显示自定义对话框 以R.layout.layout_win 布局为主 **/
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        AlertDialog dlg = builder.create();
        View dialogView = View.inflate(this,R.layout.layout_win,null);
        dlg.setView(dialogView);
        dlg.show();

        //根据状态切换显示图片为胜利图片还是失败图片
        ImageView tWinImg= dialogView.findViewById(R.id.winimg);
        if(type==0)
            tWinImg.setImageDrawable(getDrawable(R.drawable.win));
        else
            tWinImg.setImageDrawable(getDrawable(R.drawable.lost));

        //重新游戏按键事件
        dialogView.findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dlg.dismiss();
                GoRestart();
            }

        });

        //返回游戏按键事件
        dialogView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dlg.dismiss();
                GoToBack(null);
            }

        });

    }

    /******************************************
     * 重新开始
     */
    public void GoRestart()
    {
        Log.d("jason","GoRestart");
        //初始化算法
        InitAlgorithm();
        //重新显示图片
        ResetImageDate();

        mTimeView = findViewById(R.id.time);
        RestGameTime();
        //启动游戏时间
        mGameTime.start_time();

    }

    //显示胜利界面
    private void ShowWin()
    {
        ShowDialog(0);
    }

    //显示失败图片
    private void ShowLost()
    {
        ShowDialog(1);
    }
}