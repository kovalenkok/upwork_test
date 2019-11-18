package com.app.upworktest.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JSONObjectCreator {

    public static <T> T createObject(String json, Class<T> parentType) {
        return new Gson().fromJson(json, parentType);
    }

    public static <T> T createObjectCollection(String json, Type parentType) {
        return new Gson().fromJson(json, parentType);
    }

    public static <T> String createJson(T object) {
        return new Gson().toJson(object);
    }

}
