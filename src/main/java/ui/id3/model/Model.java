package main.java.ui.id3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.*;
import static main.java.ui.id3.model.Const.*;

public class Model {
    private HashMap<Integer,List<String>> depthLog;
    private HashMap<String,String> struct;
    private String proxyWalker = ".";
    private String labelKey;
    private int labelSize;

    public Model(String labelKey,int labelSize){
        depthLog = new HashMap<>();
        struct = new HashMap<>();
        this.labelKey = labelKey;
        this.labelSize = labelSize;
    }

    public void bindResult(String proxy,String cmd){
        struct.put(proxy, wrapResult(cmd));
    }

    public void bindRequest(String proxy, String cmd){
        struct.put(proxy, wrapCommand(cmd));
    }

    public boolean requireFeed(){
        return !reachedResult(proxyWalker);
    }

    public void feed(String value){
        proxyWalker = struct.get(value);
    }

    public String result(){
        return proxyWalker.replace(TARGET,"");
    }

    public String nextRequest() {
        return proxyWalker.replace(CMD,"");

    }

    private String wrapCommand(String cmd) {
        return format("%s%s", CMD,cmd);
    }

    private String wrapResult(String cmd) {
        return format("%s%s",TARGET,cmd);
    }

    private boolean reachedResult(String proxyWalker) {
        return proxyWalker.startsWith(TARGET);
    }

    public void load() {
        proxyWalker = ".";
        proxyWalker = struct.get(proxyWalker);
    }

    public void log(int depth, String splitter){
        if (!depthLog.containsKey(depth)){
            depthLog.put(depth, new ArrayList<>());
        }
        depthLog.get(depth).add(splitter);
    }

    public String labelKey() {
        return labelKey;
    }

    public int labelSize(){
        return labelSize;
    }

    public HashMap<Integer, List<String>> depthLog(){
        return depthLog;
    }
}
