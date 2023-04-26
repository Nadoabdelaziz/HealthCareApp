package com.example.healthcare.models;

public class Student implements Comparable<Student>{

    String name, nickname, PhoneNumber, schoolname, gender, nationality, bloodtype, disease, precuations,known_as;


    public Student() {
    }

    public Student(String name,String nickname,String PhoneNumber,String schoolname,String gender,String nationality,String bloodtype,String disease,String precuations,String known_as) {
        this.name=name;
        this.nickname=nickname;
        this.PhoneNumber = PhoneNumber;
        this.schoolname = schoolname;
        this.gender = gender;
        this.nationality = nationality;
        this.bloodtype = bloodtype;
        this.disease = disease;
        this.precuations = precuations;
        this.known_as = known_as;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getPrecuations() {
        return precuations;
    }

    public void setPrecuations(String precuations) {
        this.precuations = precuations;
    }

    public String getKnown_as() {
        return known_as;
    }

    public void setKnown_as(String known_as) {
        this.known_as = known_as;
    }


    @Override
    public int compareTo(Student o) {
        return this.getName().compareTo(o.getName());
    }
}

