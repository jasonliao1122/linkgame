package jw.database;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/***************************************************************
 * 封装文件存储功能，便于使用
 */
public class JwFile {

    /**************************************************
     * 往输入流写入文件内容
     * @param fos 输入流句柄
     * @param data 写入数据
     * @return 返回写入状态
     */
    static public boolean Write(FileOutputStream fos, String data) {
        try {
            //写入文件内容
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fos != null) {
            try {
                //关闭输出流
                fos.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*******************************************
     * 读取输出流数据，并关闭输出流
     * @param fis 输出流句柄
     * @return 返回 读取数据 失败为null;
     */
    static public  String Read(FileInputStream fis) {
        String tRet = null;
        try {
            //创建缓冲区，并获得文件长度
            byte[] buffer = new byte[fis.available()];
            //读取文件到缓冲区
            fis.read(buffer);
            //缓冲区数据转换成返回字符串
            tRet = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fis != null) {
            try {
                //关闭输入流
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tRet;
    }
    /********************************************
     * 内部存储
     */
    /*******************************************
     * 写入数据到内部存储中
     * * @param iContext  句柄
     * @param filename  文件名
     * @param data 数据
     * @return 写入成功，失败状态
     */
    static public  boolean Write(Context iContext, String filename, String data) {
        FileOutputStream fos = null;

        try {
            //根据文件名打开文件输出流
            fos = iContext.openFileOutput(filename, Context.MODE_PRIVATE);
            //往输入流写入文件内容
            return Write(fos, data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /******************************************************
     * 读取内部文件数据
     * @param iContext 句柄
     * @param filename 文件名
     * @return 返回读取数据，失败返回null
     */
    static public  String Read(Context iContext, String filename) {
        String tRet = null;
        FileInputStream fis = null;
        try {
            //根据文件名获得文件输入流
            fis = iContext.openFileInput(filename);
            return Read(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tRet;
    }

    /******************************************************
     * 读写外部存储 功能
     */
    /************************************
     * 判断是否存储SD卡
     * @return true 存在 false 不存在
     */
    static public  boolean IsHaveSD() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /*****************************************
     * 获取SD卡中的文件句柄
     * @param filename 文件名
     * @return 返回文件句柄 失败返回null
     */
    static public  File GetSDFile(String filename) {
        File file = null;
        if (IsHaveSD() == true) {
            //SD卡存在
            //获取SD卡路径
            File SDPath = Environment.getExternalStorageDirectory();
            //获取文件名对应的文件句柄
            file = new File(SDPath, filename);
        }
        return file;
    }

    /**************************************************
     * 往SD卡文件中写入数据
     * @param filename 文件名
     * @param data 数据
     * @return 写入成功失败的状态
     */
    static public  boolean WriteSD(String filename, String data) {
        File file = GetSDFile(filename);
        if (file != null) {
            FileOutputStream fos = null;
            try {
                //通过文件句柄获取输出流
                fos = new FileOutputStream(file);
                return Write(fos, data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**********************************************
     * 从SD卡中读取文件的内容
     * @param filename 文件名
     * @return 返回文件内容 失败返回null
     */
    static public  String ReadSD(String filename) {
        File file = GetSDFile(filename);
        if (file != null) {
            try {
                FileInputStream fis=new FileInputStream(file);
                return Read(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*******************************************************
     *动态申请SD卡权限
     * @param iContext 上下文句柄
     * @param reqid 请求码
     */
    static public  void Permissions(Context iContext,int reqid)
    {
        ActivityCompat.requestPermissions((Activity) iContext,
                new String[]{"android.permission.WRITE_EXTERNAL_STORACE"},reqid);
    }

    /****************************************
     * 新建文件夹
     * @param ipth
     */
    static public void MKDIR(String ipth)
    {
        ipth="/sdcard/"+ipth;

        String[] sArray1=ipth.split("/");
        if(sArray1!=null)
        {
            String tpth="/";
            for(int i=0;i<sArray1.length;i++)
            {
                tpth+=sArray1[i];
                File file = new File(tpth);

                if (!file.exists())
                {
                    file.mkdir();
                }
                tpth+="/";
            }
        }
    }
}
