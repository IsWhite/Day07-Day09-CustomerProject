package com.leisure.util;

import com.leisure.bean.CustomerBean;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
//编辑的思路:id -数据-改
/**
 *存
 */
public class BeanUtil {
    public static void requestToBean(HttpServletRequest request
            , CustomerBean bean){
        //利用反射获取bean类
        Class clazz = bean.getClass();
        //获取类中的所有属性
        Field[] fields = clazz.getDeclaredFields();
        //对属性进行遍历
        for (Field field : fields) {

            String name = field.getName();//反射找出key
            String value = request.getParameter(name);//从前端获取value值

            field.setAccessible(true);//打破封装
            try {
                field.set(bean,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }
}
