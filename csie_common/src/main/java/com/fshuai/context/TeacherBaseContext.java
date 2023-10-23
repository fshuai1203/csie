package com.fshuai.context;


import java.util.Map;

public class TeacherBaseContext {

    public static ThreadLocal<Map<String, Integer>> threadLocal = new ThreadLocal<>();

    public static void setCurrentTeacher(Map map) {
        threadLocal.set(map);
    }

    public static Map getCurrentTeacher() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
