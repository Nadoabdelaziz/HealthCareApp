package com.example.healthcare.models;

public class Teacher implements Comparable<Teacher> {

    private String Name;
    private String SchoolName;
    private String birthDate;
    private String phoneNumber;
    private String email;
    private String cin;
    private String maritalStatus;


    public Teacher() {
    }

    public Teacher(String Name, String SchoolName, String birthDate, String phoneNumber, String email, String cin, String maritalStatus) {
        this.Name = Name;
        this.SchoolName = SchoolName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cin = cin;
        this.maritalStatus = maritalStatus;
    }

    public String getFirstName() {
        return Name;
    }

    public void setFirstName(String Name) {
        this.Name = Name;
    }

    public String getLastName() {
        return SchoolName;
    }

    public void setLastName(String SchoolName) {
        this.SchoolName = SchoolName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getFullName()
    {
        return Name+" "+SchoolName;
    }

    @Override
    public int compareTo(Teacher o) {
        return this.getFullName().compareTo(o.getFullName());
    }
}