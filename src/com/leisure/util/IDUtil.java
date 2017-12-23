package com.leisure.util;

import java.util.UUID;

/**
 *ID相关工具类
 */
public class IDUtil {
    public static String randomId(){ //静态类:直接类调方法
        return UUID.randomUUID() //固定写法:随机id
                .toString()      //字符串
                .replace("-","") //- 更换 成空
                .toLowerCase();  //小写
    }
    public  static  boolean isEmpty(String s){
        return s==null||s.trim().isEmpty(); //s.trim().isEmpty()去掉首尾空格再判断
                                            //前后没区别,但是后面有个首尾去空格
    }
}
