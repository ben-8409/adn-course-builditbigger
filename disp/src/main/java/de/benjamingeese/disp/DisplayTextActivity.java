package de.benjamingeese.disp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayTextActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_TEXT = "extra-text";
    private String mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_text);

        if(savedInstanceState == null) {
            mText = getIntent().getStringExtra(INTENT_EXTRA_TEXT);
        } else {
            mText = savedInstanceState.getString(INTENT_EXTRA_TEXT);
        }

        //display text
        TextView tv = findViewById(R.id.tv_text);
        tv.setText(mText);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(INTENT_EXTRA_TEXT, mText);
        super.onSaveInstanceState(outState);
    }
}
