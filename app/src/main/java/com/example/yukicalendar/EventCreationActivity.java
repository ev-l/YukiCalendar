package com.example.yukicalendar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yukicalendar.model.CalendarEvent;
import com.example.yukicalendar.tasks.CreateCalendarEvent;
import com.example.yukicalendar.yukiparser.Config;
import com.example.yukicalendar.yukiparser.parser.SeerParserInitializer;

import java.util.ArrayList;
import java.util.Locale;

public class EventCreationActivity extends AppCompatActivity implements View.OnClickListener, CreateCalendarEvent.OnEventCreateListener {

    public static final String CALENDAR_ID = "CALENDAR_ID";
    private static final int REQ_CODE_SPEECH_INPUT = 1;

    private Button addEventButton;
    private View micButtonView;
    private EditText userInputView;
    private SeerParserInitializer eventParser;
    private long calendarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        calendarId = getIntent().getIntExtra(CALENDAR_ID, 0);

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

        micButtonView = findViewById(R.id.mic_view);
        micButtonView.setOnClickListener(this);
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    userInputView.setText(result.get(0));
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_event_btn) {
            // Parser input text on click
            String userInput = userInputView.getText().toString();
            if (TextUtils.isEmpty(userInput)) {
                Toast.makeText(this, "Please input some text", Toast.LENGTH_SHORT).show();
            } else {
                // Send text to parser
                CalendarEvent value = eventParser.parseInput(userInput);

                if (value != null) {
                    value.setCalendarId(this.calendarId);
                    CreateCalendarEvent createCalendarEvent = new CreateCalendarEvent(getContentResolver(), value);
                    createCalendarEvent.setOnEventCreateListener(EventCreationActivity.this);
                    createCalendarEvent.createEvent();
                    Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Nothing parsed", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v.getId() == R.id.mic_view) {
            promptSpeechInput();
        }

    }

    @Override
    public void onEventCreated() {
        // Close the activity after event creation
        finish();
    }
}
