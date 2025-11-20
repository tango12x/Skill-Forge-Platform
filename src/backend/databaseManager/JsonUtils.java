package backend.databaseManager;

import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.reflect.TypeToken;


public class JsonUtils {
    private static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create();
    
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
    
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
    
    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
    
    // Helper for reading lists
    public static <T> List<T> listFromJson(String json, Class<T> classOfT) {
        Type listType = TypeToken.getParameterized(List.class, classOfT).getType();
        return gson.fromJson(json, listType);
    }
}