package com.example.linkgame;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***********************************************
 * 连连看算法部分
 */
public class Algorithm {

    /*********************
     * 算法数据结构
     */
    class AData
    {
        //图片数据 0 -无图片  >1 图片ID
        public int mImgID;

        //遍历数据表是判断状态，是否已遍历
        public boolean mIsLook;

        //成功找到路径，存储下一个路径的ID
        public int mNextID;
    }
    //图片数据 0 -无图片  >1 图片ID
    public AData[] mData;

    private int mA,mB,mDiff; //数据列表的 列 行  难度

    //第一次，二次按下的图片ID，如未按下为-1
    private int mDown1=-1,mDown2=-1;

    /************************************
     * 实例化对象
     * @param a  数据的列
     * @param b  数据的行
     */
    public Algorithm(int a,int b,int diff)
    {
        mA=a;
        mB=b;
        mDiff=diff;

        mData=new AData[(mA+2)*(mB+2)];

        RandomData();
    }


    /****************************************
     * 产生随机数据
     */
    private void RandomData()
    {
        //缓存数据，用于缓存随机数据
        List<Integer> tTemp = new ArrayList<Integer>();
        int i=0,tRand;
        //重置所有数据
        for(i=0;i<mData.length;i++) {
            mData[i]=new AData();
            mData[i] .mImgID = 0;
            mData[i] .mIsLook = false;
            mData[i] .mNextID = -1;
        }

        /***给缓存数据赋值 ***/
        for(i=0;i<(mA*mB)/mDiff;i++)
        {
            int id=i*mDiff;
            for(int j=0;j<mDiff;j++)
                tTemp.add(i+1);
        }

        for(int j=0;j<(mA*mB)%mDiff;j++)
            tTemp.add(i+1);

        /*****缓存数据赋值结束 ****/

        /*****产生随机数，把缓存数据赋值到数据中，并删除缓存数据 ****/
        for(int j=1;j<mB+1;j++) {
            for (i = 1; i < mA+1; i++) {
                int id = j * (mA + 2) + i;
                int tMax = tTemp.size();
                tRand = getNum(tMax);
                mData[id].mImgID = tTemp.get(tRand);
                tTemp.remove(tRand);
            }
        }


    }

    /**********
     * 生成 0--endnum 之间的随机数
     * @param endNum
     * @return
     */
    public static int getNum(int endNum){
        if(endNum > 0){
            Random random = new Random();
            return random.nextInt(endNum);
        }
        return 0;
    }

    /***********************************
     * 重新把数据恢复默认值
     */
    private void InitData()
    {
        for(int i=0;i<mData.length;i++) {
            mData[i] .mIsLook = false;
            mData[i] .mNextID = -1;
        }
        mFID1=-1;
        mFID2=-1;
    }
    /**********************************************
     * 图片被按下事件
     * @param id 图片ID
     * @return false 重复按下同一张图片 true 未重复按下同一张图片
     */
    public boolean IsDown(int id)
    {
        if(mDown1==-1)
        {
            mDown1=id;
            return true;
        } else if(mDown2==-1)
        {
            mDown2=id;
            return true;
        }

        return false;
    }

    /**************************************************
     * 两张图片均已按下
     * @int  0--只按下一张图片  1--两张图片按下，但是路径未联通  2--两张图片按下，并且路径联通
     */
    public int IsDowbleDown()
    {
        if(mDown1!=-1&&mDown2!=-1)
        {
            if(mData[ChageID(mDown1)].mImgID!=mData[ChageID(mDown2)].mImgID)
            {
                //两张图片不相同
                mDown1=-1;
                mDown2=-1;
                return 1;
            }
            if(IsRight(ChageID(mDown1),ChageID(mDown2),0)==true) {
                //两个图片能够联通
                mFID1=ChageID(mDown1);
                mFID2=ChageID(mDown2);
                mDown1=-1;
                mDown2=-1;
                return 2;
            }
            mDown1=-1;
            mDown2=-1;
            return 1;
        }
        return 0;
    }

    //显示的位置转换成算法图片位置  算法位置比显示位置上下左右多一行列
    private int ChageID(int id)
    {
        int mx1=id%(mA);
        int my1=id/(mA);

        int rid=(my1+1)*(mA+2)+(mx1+1);
        return rid;
    }
    /***********************************************
     * 自动查找是否有配对的
     */
    public int mFID1,mFID2; //存储两个能够找到匹配的图片ID
    public boolean GetRight()
    {
        for(int j=0;j<mB+2;j++)
            for(int i=0;i<mA+2;i++)
            {
                int id1=j*(mA+2)+i;
                if(mData[id1].mImgID>0)
                {
                    //图片存在
                    for(int tj=0;tj<mB+2;tj++)
                        for(int ti=0;ti<mA+2;ti++)
                        {
                            int id2=tj*(mA+2)+ti;
                            if(id2>id1)
                            {
                                //比对id1后面的图标，前面图标已比对过，无需比对
                                if(mData[id2].mImgID>0&&mData[id1].mImgID==mData[id2].mImgID)
                                {
                                    Log.d("jason","GetTs IsRight i="+i+"  j="+j+" ti="+ti+" tj="+tj);
                                    //图片存在并且两个图片相同
                                    if(IsRight(id1,id2,0))
                                    {
                                        //两个图片能够联通
                                        mFID1=id1;
                                        mFID2=id2;
                                        return true;
                                    }
                                }
                            }
                        }
                }
            }

        return false;
    }


    /**********************************************
     * id1 到 id2 是否联通 false 联通失败  true 联通成功
     * @param id1 开始图片
     * @param id2 结束图片
     * @param type 状态 0--第一次执行  1--递归执行
     */
    public boolean IsRight(int id1,int id2,int type)
    {
        //转换成坐标
        int mx1=id1%(mA+2);
        int my1=id1/(mA+2);
        int mx2=id2%(mA+2);
        int my2=id2/(mA+2);
        int id; //下一个查找的ID

        if(type==0)
        {
            //查找前重新重置状态值
            InitData();
        }
        Log.d("jason","GetTs IsRight mx1="+mx1+"  my1="+my1+" mx2="+mx2+" my2="+my2+"  mIsLook="+mData[id1].mIsLook);
        if(mData[id1].mIsLook==true)
        {
            //该图片已经遍历过，不再进行遍历
            return false;
        }

        //设置id1 是已查找状态 后续不查找
        mData[id1].mIsLook=true;

        if(id1==id2)
        {
            //两个图标重合 路径查找成功
            return true;
        }

        Log.d("jason","GetTs IsRight "+"  mImgID="+mData[id1].mImgID);
        if(mData[id1].mImgID!=0&&type!=0)
        {
            //该路径存在图片不能通过 第一次执行是不判断图片
            return false;
        }
        if(mx1<0&&mx1>=mA+2&&my1<0&&my1>=mB+2)
        {
            Log.d("jason","GetTs IsRight over");
            //越界
            return false;
        }

        if(mx1-1>=0)
        {
            //往左查找 递归查找
            Log.d("jason","GetTs IsRight left");
            id=my1*(mA+2)+(mx1-1);
            if(IsRight(id,id2,1)==true)
            {
                mData[id1].mNextID=id;
                return true;
            }
        }

        if(mx1+1<=mA+1)
        {
            //往右查找 递归查找
            Log.d("jason","GetTs IsRight right");
            id=my1*(mA+2)+(mx1+1);
            if(IsRight(id,id2,1)==true)
            {
                mData[id1].mNextID=id;
                return true;
            }
        }

        if(my1-1>=0)
        {
            //往上查找 递归查找
            Log.d("jason","GetTs IsRight up");
            id=(my1-1)*(mA+2)+mx1;
            if(IsRight(id,id2,1)==true)
            {
                mData[id1].mNextID=id;
                return true;
            }
        }

        if(my1+1<=mB+1)
        {
            //往下查找 递归查找
            Log.d("jason","GetTs IsRight down");
            id=(my1+1)*(mA+2)+mx1;
            if(IsRight(id,id2,1)==true)
            {
                mData[id1].mNextID=id;
                return true;
            }
        }
        return false;
    }

    /*************************************************
     * 重新设置图片状态
     * @param id  图片的ID
     * @param data 图片的数值
     */
    public void RestDate(int id,int data)
    {
        mData[id].mImgID=data;
    }

    /***************************************************
     * 判断游戏是否胜利
     * @return
     */
    public boolean IsWin()
    {
        //所有图片消除 游戏胜利
        for(int i=0;i<mData.length;i++)
        {
            if(mData[i].mImgID!=0)
                return false;
        }
        return true;
    }
}
