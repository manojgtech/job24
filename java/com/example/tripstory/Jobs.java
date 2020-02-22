package com.example.tripstory;


public class Jobs {
    public String id;
    public String title;
    public  String exp;
    public String location;
    public String category;
    public  String company;
    public String date;

    public Jobs(String id, String title, String company, String location,String exp,String date,String category) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.exp=exp;
        this.date=date;
        this.category=category;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExp() {
        return exp;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
