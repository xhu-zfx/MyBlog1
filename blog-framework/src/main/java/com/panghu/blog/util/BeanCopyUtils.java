package com.panghu.blog.util;

import cn.hutool.core.bean.BeanUtil;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    // 拷贝单个bean
    public static <V> V copyBeanSingle(Object source, Class<V> clazz) {
//        //创建目标对象
//        V result = null;
//        try {
//            result = clazz.newInstance();
//            //实现属性copy
//            BeanUtil.copyProperties(source, result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //返回结果
//        return result;
        return BeanUtil.copyProperties(source,clazz);
    }

    // 拷贝list集合
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> BeanUtil.copyProperties(o,clazz))
                .collect(Collectors.toList());
    }
}

