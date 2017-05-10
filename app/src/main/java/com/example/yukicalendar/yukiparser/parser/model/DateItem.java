package com.example.yukicalendar.yukiparser.parser.model;


import com.example.yukicalendar.yukiparser.parser.SuggestionValue;

/**
 * @author p-v
 */

public class DateItem extends SuggestionValue.LocalItemItem {

    public int startIdx;
    public int endIdx;

    public DateItem(int value, int startIdx, int endIdx) {
        super(value, startIdx, endIdx);
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }
}
