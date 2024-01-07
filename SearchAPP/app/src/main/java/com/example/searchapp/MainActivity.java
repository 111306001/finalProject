package com.example.searchapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;
import java.io.IOException;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    CheckBox[] checkBoxes = new CheckBox[8];
    SearchView keywordInput;
    Button button;
    MainActivity2 mainActivity2;
    private static final long serialVersionUID = 1L;
    StringBuilder resultText = new StringBuilder();

    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity2 = new MainActivity2();

        keywordInput = findViewById(R.id.keyword);

        checkBoxes[0] = findViewById(R.id.checkBox);
        checkBoxes[1] = findViewById(R.id.checkBox2);
        checkBoxes[2] = findViewById(R.id.checkBox3);
        checkBoxes[3] = findViewById(R.id.checkBox4);
        checkBoxes[4] = findViewById(R.id.checkBox5);
        checkBoxes[5] = findViewById(R.id.checkBox6);
        checkBoxes[6] = findViewById(R.id.checkBox7);
        checkBoxes[7] = findViewById(R.id.checkBox8);

        button = (Button) findViewById(R.id.button);

        for (int i = 0; i < checkBoxes.length; i++) {
            final int index = i;
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        updateKeywordInput(checkBoxes[index].getText().toString());
                    } else {
                        removeKeywordFromInput(checkBoxes[index].getText().toString());
                    }
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = keywordInput.getQuery().toString().trim();
                if (!keyword.isEmpty()) {
                    performSearch(keyword);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void performSearch(String keyword) {
        String apiKey = "AIzaSyBuklRqxCBbXGoBcYeJ2Mo-cNet7zPPCRY"; // Replace with your API key
        String customSearchEngineID = "b6b062cffaef84cff"; // Replace with your custom search engine ID
        String searchUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey +
                "&cx=" + customSearchEngineID + "&q=" + keyword;

        new HttpRequestTask().execute(searchUrl);
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            handleResponse(result);
        }
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray items = jsonResponse.getJSONArray("items");
            StringBuilder searchResults = new StringBuilder();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String title = item.getString("title");
                String link = item.getString("link");
                searchResults.append("<a href='").append(link).append("'>").append(title).append("</a><br>");
            }
            if (searchResults.length() > 0) {
                // You might want to display these search results in MainActivity2
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("searchResults", searchResults.toString());
                startActivity(intent);
            } else {
                // Handle case of no results found
                Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error
            Toast.makeText(MainActivity.this, "Error parsing results", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateKeywordInput(String text) {
        resultText.setLength(0);

        resultText.append(keywordInput.getQuery().toString());
        if (!resultText.toString().isEmpty()) {
            resultText.append(" ");
        }

        resultText.append(text);
        keywordInput.setQuery(resultText.toString().trim(), false);
    }

    private void removeKeywordFromInput(String text) {
        String currentText = keywordInput.getQuery().toString().trim();
        if (currentText.contains(text)) {
            currentText = currentText.replace(text, "").trim();
            keywordInput.setQuery(currentText, false);
        }
    }

}

