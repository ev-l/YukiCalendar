package com.example.yukicalendar.yukiparser.parser;

import android.content.Context;


import com.example.yukicalendar.yukiparser.Config;
import com.example.yukicalendar.yukiparser.SuggestionRow;
import com.example.yukicalendar.yukiparser.parser.handler.SuggestionHandler;
import com.example.yukicalendar.yukiparser.parser.handler.english.DOWSuggestionHandler_EN;
import com.example.yukicalendar.yukiparser.parser.handler.english.DateSuggestionHandler_EN;
import com.example.yukicalendar.yukiparser.parser.handler.english.InitialSuggestionHandler_EN;
import com.example.yukicalendar.yukiparser.parser.handler.english.NumberRelativeTimeSuggestionHandler_EN;
import com.example.yukicalendar.yukiparser.parser.handler.english.RelativeTimeSuggestionHandler_EN;
import com.example.yukicalendar.yukiparser.parser.handler.english.TODSuggestionHandler_EN;
import com.example.yukicalendar.yukiparser.parser.handler.english.TimeSuggestionHandler_EN;

import java.util.ArrayList;
import java.util.List;

/**
 * @author p-v
 */

public class SeerParserInitializer {

    private SuggestionHandler initialSuggestionHandler;
    private Context context;

    private void initializeHandlers(@Config.Language int language) {
        // handlers
        SuggestionHandler numberRelativeTimeSuggestionHandler;
        SuggestionHandler relativeTimeSuggestionHandler;
        SuggestionHandler dateSuggestionHandler;
        SuggestionHandler dowSuggestionHandler;
        SuggestionHandler timeSuggestionHandler;
        SuggestionHandler todSuggestionHandler;

        switch (language) {

            case Config.Language.ENGLISH:
                initialSuggestionHandler = new InitialSuggestionHandler_EN();
                numberRelativeTimeSuggestionHandler = new NumberRelativeTimeSuggestionHandler_EN();
                relativeTimeSuggestionHandler = new RelativeTimeSuggestionHandler_EN();
                dateSuggestionHandler = new DateSuggestionHandler_EN(language);
                dowSuggestionHandler = new DOWSuggestionHandler_EN();
                timeSuggestionHandler = new TimeSuggestionHandler_EN();
                todSuggestionHandler = new TODSuggestionHandler_EN();
                break;

            // TODO implement other languages here

            default:
                initialSuggestionHandler = new InitialSuggestionHandler_EN();
                numberRelativeTimeSuggestionHandler = new NumberRelativeTimeSuggestionHandler_EN();
                relativeTimeSuggestionHandler = new RelativeTimeSuggestionHandler_EN();
                dateSuggestionHandler = new DateSuggestionHandler_EN(language);
                dowSuggestionHandler = new DOWSuggestionHandler_EN();
                timeSuggestionHandler = new TimeSuggestionHandler_EN();
                todSuggestionHandler = new TODSuggestionHandler_EN();

        }

        // build handler chain
        initialSuggestionHandler.setNextHandler(numberRelativeTimeSuggestionHandler);
        numberRelativeTimeSuggestionHandler.setNextHandler(relativeTimeSuggestionHandler);
        relativeTimeSuggestionHandler.setNextHandler(dateSuggestionHandler);
        dateSuggestionHandler.setNextHandler(dowSuggestionHandler);
        dowSuggestionHandler.setNextHandler(timeSuggestionHandler);
        timeSuggestionHandler.setNextHandler(todSuggestionHandler);
    }

    public SeerParserInitializer(Context context, Config config) {
        this.context = context;

        initializeHandlers(config.getLanguage());

    }

    public List<SuggestionRow> buildSuggestions(String input) {

        // Stores information about the user input
        SuggestionValue suggestionValue = new SuggestionValue();

        // Interpret user input and store values in suggestion value
        initialSuggestionHandler.handle(context, input, suggestionValue);

        List<SuggestionRow> suggestionList = new ArrayList<>(3);

        // Save values in instance so `SparseArrayCompat#get` method is not
        // called again and again in the builders
        suggestionValue.init();

        return suggestionList;
    }

}
