package com.example.dell.fireapp;

/**
 * Created by NITANT SOOD on 22-04-2018.
 */

public class UserData {
    String email,stream,year,contact,name;

    public UserData() {
    }

    public UserData(String email, String stream, String year, String contact, String name) {
        this.email = email;
        this.stream = stream;
        this.year = year;
        this.contact = contact;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
