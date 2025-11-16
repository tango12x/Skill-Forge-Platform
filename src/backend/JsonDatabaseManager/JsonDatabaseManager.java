
package backend.JsonDatabaseManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class JsonDatabaseManager {

    // UTILITY METHODS TO CONVERT JSON ARRAYS TO ARRAYLISTS
    public static ArrayList<String> toStringList(JSONArray jsonArray) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
            System.out.println(jsonArray.getString(i));
        }
        return list;
    }

    // UTILITY METHOD TO CONVERT NESTED JSON ARRAYS TO LIST OF STRING LISTS
    public static ArrayList<ArrayList<String>> toListOfStringLists(JSONArray jsonArray) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray innerArray = jsonArray.getJSONArray(i);
            result.add(toStringList(innerArray));
        }
        return result;
    }

    // UTILITY METHOD TO CONVERT ARRAYLIST TO JSON ARRAY
    public static JSONArray toJSONArray(ArrayList<String> list) {
        JSONArray jsonArray = new JSONArray();
        for (String item : list) {
            jsonArray.put(item);
        }
        return jsonArray;
    }
    
    // UTILITY METHOD TO CONVERT LIST OF STRING LISTS TO NESTED JSON ARRAY
    public static JSONArray toJSONArrayOfLists(ArrayList<ArrayList<String>> listOfLists) {
        JSONArray jsonArray = new JSONArray();
        for (ArrayList<String> list : listOfLists) {
            jsonArray.put(toJSONArray(list));
        }
        return jsonArray;
    }
   
    
}
