package ru.turkov.mvc.entity;

import java.util.Map;

public class EditInfo {
    private String userId;
    private Map<String, String> info;

    public EditInfo(String userId, Map<String, String> info) {
        this.userId = userId;
        this.info = info;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, String> getInfo() {
        return info;
    }
}
