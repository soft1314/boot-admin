package com.qiyuan.bautil.util;

import java.util.HashMap;
import java.util.Map;

public class RequestHolder {
    private final static ThreadLocal<Map<String, Object>> holder = new ThreadLocal<>();

    public static void remove() {
        holder.remove();
    }

    public static void set(String key, Object value) {
        getContext().put(key, value);
    }

    public static Object get(String key) {
        return getContext().get(key);
    }

    static private Map<String, Object> getContext() {
        Map map = holder.get();
        if (map == null) {
            map = new HashMap();
            holder.set(map);
        }
        return map;
    }
}
