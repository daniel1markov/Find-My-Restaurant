package com.hit.dm;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private String name;
    private String category;
    private String address;
    private String city;
    private String phoneNumber;
    private String rating;

    public Restaurant(){}

    public Restaurant(String category,String name, String address,String city, String phoneNumber, String rating) {

        this.category = category;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String name) {
        this.city = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString ()
    {
        return "{'" +
                "Category':'" + this.getCategory() + '\'' +
                ", 'Name':'" + this.getName() + '\'' +
                ", 'Address':'" + this.getAddress() + '\'' +
                ", 'City':'" + this.getCity() + '\'' +
                ", 'PhoneNumber':'" + this.getPhoneNumber() + '\'' +
                ", 'Rating':'" + this.getRating() + "'}";
    }
}
