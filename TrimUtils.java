package org.jeecg.modules.crm.groupfee.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangyaming
 * 获得实体类的list，将里面string类型全部trim，再返回
 */

public class TrimUtils {
    public  static List<?>trim(List<?> list,Class entityClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Field[] fs=entityClass.getDeclaredFields();
        ArrayList<EntityAttribute> entityAttributes = new ArrayList<EntityAttribute>();
        for (Field f:fs)
        {
            f.setAccessible(true);
            Class<?> type = f.getType();
            if (type==String.class&&f.getName()!="id") {
                String name = f.getName();//获取属性名
                String upperName = upperCase(name);
                Method getMethod = entityClass.getMethod("get" + upperName);
                Method setMethod = entityClass.getMethod("set" + upperName,String.class);
                EntityAttribute entityAttribute = new EntityAttribute();
                entityAttribute.setAttributeName(name);
                entityAttribute.setSetMethod(setMethod);
                entityAttribute.setGetMethod(getMethod);
                entityAttributes.add(entityAttribute);
            }
            for (Object obj:list)
            {
                for (EntityAttribute entityAttribute:entityAttributes)
                {
                    Method getMethod = entityAttribute.getGetMethod();
                    String attribute = (String)getMethod.invoke(obj);
                    if (attribute!=null)
                    {
                        String attributeSpace = attribute.replaceAll("[　*| *| *|//*]*", "");
                        Method setMethod = entityAttribute.getSetMethod();
                        setMethod.invoke(obj,attributeSpace);
                    }
                }
            }
        }
        return  list;
    }
    public static  Object trim(Object obj,Class entityClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Field[] fs=entityClass.getDeclaredFields();
        ArrayList<EntityAttribute> entityAttributes = new ArrayList<EntityAttribute>();
        for (Field f:fs)
        {
            f.setAccessible(true);
            Class<?> type = f.getType();
            if (type==String.class&&f.getName()!="id") {
                String name = f.getName();//获取属性名
                String upperName = upperCase(name);
                Method getMethod = entityClass.getMethod("get" + upperName);
                Method setMethod = entityClass.getMethod("set" + upperName,String.class);
                EntityAttribute entityAttribute = new EntityAttribute();
                entityAttribute.setAttributeName(name);
                entityAttribute.setSetMethod(setMethod);
                entityAttribute.setGetMethod(getMethod);
                entityAttributes.add(entityAttribute);
            }
                for (EntityAttribute entityAttribute:entityAttributes)
                {

                    Method getMethod = entityAttribute.getGetMethod();
                    String attribute = (String)getMethod.invoke(obj);
                    if (attribute!=null)
                    {
                       String attributeSpace = attribute.replaceAll("[　*| *| *|//*]*", "");
                        Method setMethod = entityAttribute.getSetMethod();
                        setMethod.invoke(obj,attributeSpace);
                    }

            }
        }
        return  obj;
    };
    public static String upperCase(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
