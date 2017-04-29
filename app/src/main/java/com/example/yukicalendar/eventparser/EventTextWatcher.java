package com.example.yukicalendar.eventparser;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;

public class EventTextWatcher implements TextWatcher {

    private static final int EVENT_TEXT_CHANGE = 0;
    private static final int HANDLER_POST_DELAY = 400;

    private Handler eventTextHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == EVENT_TEXT_CHANGE) {
                String enteredText = (String) msg.obj;
                // Handle text...
            }
        }
    };

    private String previouslyEnteredText;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String enteredText = s.toString().trim();

        if(enteredText.equals(previouslyEnteredText)) {
            return;
        }

        this.previouslyEnteredText = enteredText;

        eventTextHandler.removeMessages(EVENT_TEXT_CHANGE);
        final Message msg = Message.obtain(eventTextHandler, EVENT_TEXT_CHANGE, s);
        eventTextHandler.sendMessageDelayed(msg, HANDLER_POST_DELAY);
    }
}
