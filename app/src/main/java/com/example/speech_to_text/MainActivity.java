package com.example.speech_to_text;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textOutput;
    Button mButton;
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textOutput = findViewById(R.id.textOutput);
        mButton = findViewById(R.id.emailButton);
        mEdit = findViewById(R.id.emailInput);

        mButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + mEdit.getText().toString()));
               intent.putExtra(Intent.EXTRA_SUBJECT, "Notes from Notation");
               intent.putExtra(Intent.EXTRA_TEXT, textOutput.getText().toString());
               startActivity(intent);
           }
        });
    }

    public void speechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent, 100);

//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent, 100);
//        } else {
//            Toast.makeText(this,"Text-to-Speech Does Not Work On Your Device", Toast.LENGTH_SHORT).show();
//        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {
            textOutput.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
            System.out.println(textOutput.getText());
        }
    }
}