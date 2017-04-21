package com.example.yukicalendar.yukiparser.parser.handler.english;

import android.content.Context;


import com.example.yukicalendar.yukiparser.parser.SuggestionValue;
import com.example.yukicalendar.yukiparser.parser.handler.SuggestionHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.yukicalendar.yukiparser.parser.helper.Constants.TOD_AFTERNOON;
import static com.example.yukicalendar.yukiparser.parser.helper.Constants.TOD_EVENING;
import static com.example.yukicalendar.yukiparser.parser.helper.Constants.TOD_MORNING;
import static com.example.yukicalendar.yukiparser.parser.helper.Constants.TOD_NIGHT;

/**
 * @author p-v
 */

public class TODSuggestionHandler_EN extends SuggestionHandler {

    private static final String REGEX = "\\b(?:(morn(?:i(?:n(?:g)?)?)?)|(after(?=(?:\\S+|$))(?:n(?:o(?:o(?:n)?)?)?)?)|(even(?:i(?:n(?:g)?)?)?)|(ni(?:g(?:h(?:t)?)?)?))\\b";
    private Pattern pTod;

    public TODSuggestionHandler_EN() {
        pTod = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public void handle(Context context, String input, SuggestionValue suggestionValue) {
        Matcher matcher = pTod.matcher(input);
        if (matcher.find()) {
            int value;
            if (matcher.group(1) != null) {
                value = TOD_MORNING;
            } else if (matcher.group(2) != null) {
                value = TOD_AFTERNOON;
            } else if(matcher.group(3) != null) {
                value = TOD_EVENING;
            } else {
                value = TOD_NIGHT;
            }
            suggestionValue.appendSuggestion(SuggestionValue.TIME_OF_DAY, value);
        }
        super.handle(context, input, suggestionValue);
    }

}
