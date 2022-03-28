package com.example.database;

import android.util.Log;

import java.util.Random;

/*******************************************************
 * 随机生成姓名 成绩
 */
public class RandomData {

    static private String SXing="赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张"+
            "孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳"+
            "酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄"+
            "和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁";
    static private String SName=
            "镇言奕岩遥潭喻振安歌雅萌轩劲淇铭谦灿相帅育圣霄圆芳若泓善弥庸引烨秋楷祺封世源沧欧"+
                    "茗兰涵妘童思倪皙煊苛杰晖奥海朴致楚姣馨绮悦雯语思嘉然悦菲道彰若旭晴达庭流"+
                    "浅拓悟知施向畅华健容湛卫余馨楚娜杉肃歌德曲柯漾晖蓝恭松玉澜熙舒汐媛筱涵皎"+
                    "依斐茗洋挚银树烨砚渊文豫瀚霖淞炫烨文启兆景澜骏颢耿笃方月励雄岸刚瑜彦菡欣"+
                    "嘉薇芙熙翔欧潜桐阜千阳生戈莫胜翎章古余顺洋萧湛妙春理晨卡庭景乐钦绍蒙健镜"+
                    "科玮语博俊笠楷琨子麟嘉卓明曜鸣杰";

    static public String GetName()
    {
        String name="";
        int x=getNum(SXing.length());
        int m=getNum(SName.length()/2);
        name=SXing.substring(x,x+1)+SName.substring(m*2,m*2+2);

        Log.d("jason","#### RandomData GetName x="+x+"  m="+m+"  name="+name);

        return name;
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
}
