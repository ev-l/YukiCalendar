package com.example.yukicalendar;

/**
 * Created by esther on 16/02/17.
 */

public class ListItem {

    private String head;
    private String desc;

    public ListItem(String head, String desc) {
        this.head = head;
        this.desc = desc;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }
}
