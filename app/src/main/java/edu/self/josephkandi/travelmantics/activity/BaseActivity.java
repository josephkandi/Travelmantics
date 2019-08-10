package edu.self.josephkandi.travelmantics.activity;

import androidx.appcompat.app.AppCompatActivity;

import edu.self.josephkandi.travelmantics.app.TravelmanticsApp;

public class BaseActivity extends AppCompatActivity {
    public TravelmanticsApp app;

    public TravelmanticsApp getApp() {
        return (app == null) ? app = (TravelmanticsApp) getApplication() : app;
    }
}
