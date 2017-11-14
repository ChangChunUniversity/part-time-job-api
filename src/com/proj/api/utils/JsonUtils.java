package com.proj.api.utils;

import com.google.gson.Gson;

/**
 * Created by jangitlau on 2017/11/5.
 */
public class JsonUtils {
    public static Gson gson=new Gson();

    public static synchronized String toJson(Object src){
        return gson.toJson(src);
    }

    public static synchronized <T> T fromJson(String json,Class<T> classOff){
        return gson.fromJson(json, classOff);
    }
}
