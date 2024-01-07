package com.example.searchapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        result = findViewById(R.id.textview_first);

        // Retrieve search results from intent and display them
        String searchResults = getIntent().getStringExtra("searchResults");
        if (searchResults != null) {
            result.setText(android.text.Html.fromHtml(searchResults));
            result.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        }
    }
}
