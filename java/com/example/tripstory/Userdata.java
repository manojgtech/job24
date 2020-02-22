package com.example.tripstory;
public class Userdata {
    String email;
    String name;
    String mob;
    String dob;
    String img;
    String linkd;
    String addrs;

    public Userdata(String email, String name, String mob, String dob, String img, String linkd, String addrs) {
        this.email = email;
        this.name = name;
        this.mob = mob;
        this.dob = dob;
        this.img = img;
        this.linkd = linkd;
        this.addrs = addrs;
    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getMob() {
        return mob;
    }

    public String getDob() {
        return dob;
    }

    public String getImg() {
        return img;
    }

    public String getLinkd() {
        return linkd;
    }

    public String getAddrs() {
        return addrs;
    }
}
