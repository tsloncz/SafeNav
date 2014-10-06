package com.autoowners.capstone.safenav;

import android.app.Application;

/**
 * Created by timsloncz on 10/6/14.
 *
 * Oversees all activities. Used to store data that all activities need access too
 */
public class ApplicationManager extends Application {
    public String userName = null;
    public String userPass = null;

    public UserSettings settings = new UserSettings();
}
