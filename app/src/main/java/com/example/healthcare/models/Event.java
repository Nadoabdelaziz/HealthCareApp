package com.example.healthcare.models;

public class Event implements Comparable<Event>{
    String Title;
    String To;
    String From;
    String Description;


    public Event() {

    }

    public Event(String Title, String To, String From, String Description) {
        this.Title = Title;
        this.To = To;
        this.From = From;
        this.Description = Description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String To) {
        this.To = To;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String From) {
        this.From = From;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public int compareTo(Event o) {
        return this.Title.compareTo(o.Title);
    }
}

