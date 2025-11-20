package backend.databaseManager;

import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.reflect.TypeToken;


public class ReadWrite {
    private static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    
    // Generic method to read any list of objects
    public <T> List<T> readFromFile(String filename, Class<T> type) {
        try (Reader reader = new FileReader(filename)) {
            Type listType = TypeToken.getParameterized(List.class, type).getType();
            List<T> items = gson.fromJson(reader, listType);
            return items != null ? items : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    // Generic method to write any list of objects
    public <T> void writeToFile(String filename, List<T> items) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(items, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}