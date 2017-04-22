package com.example.yukicalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yukicalendar.yukiparser.Config;
import com.example.yukicalendar.yukiparser.parser.SeerParserInitializer;
import com.example.yukicalendar.yukiparser.parser.SuggestionValue;
import com.example.yukicalendar.yukiparser.parser.model.ParsedEvent;

public class EventCreationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addEventButton;
    private EditText userInputView;
    private SeerParserInitializer eventParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        Config config = new Config.ConfigBuilder()
                .setTimeFormat12HoursWithMins("h:mm a")
                .setTimeFormat12HoursWithoutMins("h:mm a")
                .setTimeFormat24Hours("HH:mm")
                .setDateFormatWithYear("dd MMM, yyyy")
                .setDateFormatWithoutYear("dd MMM")
                .setLanguage(Config.Language.ENGLISH)
                .build();
        eventParser = new SeerParserInitializer(this, config);
        userInputView = (EditText) findViewById(R.id.user_input_view);

        addEventButton = (Button) findViewById(R.id.add_event_btn);
        addEventButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Parser input text on click
        String userInput = userInputView.getText().toString();
        if (TextUtils.isEmpty(userInput)) {
            Toast.makeText(this, "Please input some text", Toast.LENGTH_SHORT).show();
        } else {
            // Send text to parser
            ParsedEvent value = eventParser.parseInput(userInput);
            if (value != null) {
                Toast.makeText(this, value.getDtStart().getTime().toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Nothing parsed", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
