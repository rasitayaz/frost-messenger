package com.frostdev.frostmessenger.ActivityHierarchy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.frostdev.frostmessenger.FrostMessenger;

@SuppressLint("Registered")
public class FrostActivity extends AppCompatActivity {
    private FrostMessenger application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (FrostMessenger) getApplication();
        application.setCurrentActivity(this);
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) view = new View(this);
        if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        application.setCurrentActivity(this);
        application.setAppActive(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        application.setAppActive(false);
    }
}
