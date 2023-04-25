package com.example.healthcare.models;

public class Comment implements Comparable<Comment>{

    String sickness;
    String StdName;
    String Comment;


public Comment() {
        }

public Comment(String sickness, String StdName, String Comment) {
        this.sickness = sickness;
        this.StdName = StdName;
        this.Comment = Comment;
        }

public String getSickness() {
        return sickness;
        }

public void setSickness(String sickness) {
        this.sickness = sickness;
        }

public String getComment() {
        return Comment;
        }

public void setComment(String Comment) {
        this.Comment = Comment;
        }

public String getStdName() {
        return StdName;
        }

public void setStdName(String StdName) {
        this.StdName = StdName;
        }

        @Override
        public int compareTo(Comment o) {
                return this.getSickness().compareTo(o.getSickness());
        }
}

