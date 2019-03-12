package com.minor.attendance.Models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Faculty {

    public String name;
    public String email;

    public Faculty() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Faculty(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
