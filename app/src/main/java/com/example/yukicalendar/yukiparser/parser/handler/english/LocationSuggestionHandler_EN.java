package com.example.yukicalendar.yukiparser.parser.handler.english;

import android.content.Context;

import com.example.yukicalendar.yukiparser.Config;
import com.example.yukicalendar.yukiparser.parser.SuggestionValue;
import com.example.yukicalendar.yukiparser.parser.handler.SuggestionHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author p-v
 */

public class LocationSuggestionHandler_EN extends SuggestionHandler {


    private static final String LOCATION_RGX = "(?<=\\b(?:\\b(?:at)|(?:in)|(?:on)\\b)\\b).+?(?=(?:(?:on)|(?:at)|(?:in)|$))";
    private Pattern pLoc;

    private String locationString;

    public LocationSuggestionHandler_EN() {
        pLoc = Pattern.compile(LOCATION_RGX, Pattern.CASE_INSENSITIVE);
    }

    private boolean isOutsideItem(int start, int end, SuggestionValue.LocalItemItem localItem) {
        if (localItem == null) {
            return true;
        }
        return ((start < localItem.startIdx && end < localItem.endIdx) ||
                (start > localItem.startIdx && end > localItem.endIdx));
    }

    private boolean checkIfOverlapping(int start, int end, SuggestionValue suggestionValue) {
        SuggestionValue.LocalItemItem dateSuggestion = suggestionValue.get(SuggestionValue.DATE);
        SuggestionValue.LocalItemItem numItem = suggestionValue.get(SuggestionValue.RELATIVE_DAY_NUMBER);
        SuggestionValue.LocalItemItem dowItem = suggestionValue.get(SuggestionValue.DAY_OF_WEEK);
        SuggestionValue.LocalItemItem dowNextItem = suggestionValue.get(SuggestionValue.DAY_OF_WEEK_NEXT);
        SuggestionValue.LocalItemItem relDayNumItem = suggestionValue.get(SuggestionValue.RELATIVE_DAY_NUMBER);
        SuggestionValue.LocalItemItem relDayItem = suggestionValue.get(SuggestionValue.RELATIVE_DAY);
        SuggestionValue.LocalItemItem timeItem = suggestionValue.get(SuggestionValue.TIME);
        SuggestionValue.LocalItemItem todItem = suggestionValue.get(SuggestionValue.TIME_OF_DAY);

        if (isOutsideItem(start, end, dateSuggestion)
                && isOutsideItem(start, end, numItem)
                && isOutsideItem(start, end, dowItem)
                && isOutsideItem(start, end, dowNextItem)
                && isOutsideItem(start, end, relDayItem)
                && isOutsideItem(start, end, relDayNumItem)
                && isOutsideItem(start, end, timeItem)
                && isOutsideItem(start, end, todItem)) {
            return false;
        }
        return true;
    }

    @Override
    public void handle(Context context, String input, SuggestionValue suggestionValue) {
        Matcher m = pLoc.matcher(input);
        while (m.find()) {
            if (!checkIfOverlapping(m.start(), m.end(), suggestionValue)) {
                // Marking the first match location
                this.locationString = m.group();
                break;
            }

        }
        super.handle(context, input, suggestionValue);
    }

    public String getLocationString() {
        return locationString == null ? "" : locationString;
    }
}
