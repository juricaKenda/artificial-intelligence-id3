package main.java.ui.id3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;
import static main.java.ui.id3.model.Const.CMD;
import static main.java.ui.id3.model.Const.TARGET;

public class Model {
    private HashMap<Integer,List<String>> depthLog;
    private HashMap<String,String> struct;
    private String proxyWalker;
    private String labelKey;
    private String labelFallback;
    private List<String> labels;

    public Model(String labelKey,String fallback,List<String> labels){
        depthLog = new HashMap<>();
        struct = new HashMap<>();
        this.labelKey = labelKey;
        proxyWalker = ".";
        labelFallback = fallback;
        this.labels = labels;
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
        if (proxyWalker == null){
            return labelFallback;
        }
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
        return proxyWalker == null || proxyWalker.startsWith(TARGET);
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

    public HashMap<Integer, List<String>> depthLog(){
        return depthLog;
    }

    public List<String> labelValues() {
        return labels;
    }
}
