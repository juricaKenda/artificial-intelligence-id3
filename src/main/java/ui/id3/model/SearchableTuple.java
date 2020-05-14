package main.java.ui.id3.model;

import java.util.HashMap;

public class SearchableTuple {
    private HashMap<String,String> storage;

    public SearchableTuple(){
        storage = new HashMap<>();
    }

    public String get(String key){
        return storage.get(key);
    }

    public void bind(String key, String value){
        storage.put(key.trim(),value.trim());
    }
}
