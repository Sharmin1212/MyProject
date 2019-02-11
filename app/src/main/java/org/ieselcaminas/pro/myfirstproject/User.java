package org.ieselcaminas.pro.myfirstproject;

public class User {

    public String name, email, age, address;

    public int deliveryRating, consumerRating, img;

    public User() {

    }

    public User(String name, String email, String age, String address, int deliveryRating, int consumerRating, int img) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
        this.deliveryRating = deliveryRating;
        this.consumerRating = consumerRating;
        this.img = img;
    }
}

