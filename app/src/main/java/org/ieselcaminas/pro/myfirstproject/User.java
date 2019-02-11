package org.ieselcaminas.pro.myfirstproject;

public class User {

    public String name, email, country, city, address;

    public int age, deliveryRating, consumerRating, img;

    public User() {

    }

    public User(String name, String email, String country, String city, String address, int age, int deliveryRating, int consumerRating, int img) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.city = city;
        this.address = address;
        this.age = age;
        this.deliveryRating = deliveryRating;
        this.consumerRating = consumerRating;
        this.img = img;
    }
}

