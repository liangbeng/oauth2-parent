package com.ben.springbootcache.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class PropertiesCfg {
    //配置文件所在目录路径，相对项目根目录，如果是放在根目录下，直接写文件名称就行
    private final static String file = "application.properties";
    private final static String fileDev = "application-dev.properties";
    private final static Properties properties = new Properties();
    private final static Properties propertiesDev = new Properties();

    static{
        try {
            properties.load(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(file)),"utf-8"));
            propertiesDev.load(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(fileDev)),"utf-8"));
        } catch (IOException e) {
           log.error(" load PropertiesCfg error!",e);
        }
    }

    //根据key获取值
    public static  String get(String key){
        return properties.getProperty(key).trim();
    }

    //根据key获取值
    public static  String getDev(String key){
        return propertiesDev.getProperty(key).trim();
    }

    //根据key获取值，值为空则返回defaultValue
    public static  String get(String key,String defaultValue){
        return properties.getProperty(key, defaultValue);
    }
}
