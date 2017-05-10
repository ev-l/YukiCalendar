package com.example.yukicalendar.yukiparser.parser.model;


import com.example.yukicalendar.yukiparser.parser.SuggestionValue;

/**
 * @author p-v
 */

public class RelativeDayItem extends SuggestionValue.LocalItemItem {

    public boolean isPartial;

    public RelativeDayItem(int value, boolean isPartial, int startIdx, int endIdx) {
        super(value, startIdx, endIdx);
        this.isPartial = isPartial;
    }
}
