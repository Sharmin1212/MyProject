package org.ieselcaminas.pro.myfirstproject;

public class OrderItem {

    private int img;
    private String title;
    private String user;
    private String descr;
    private String owner;

    private String accepted;

    public OrderItem() {
    }

    public OrderItem(int img, String title, String user, String descr, String owner, String accepted) {
        this.img = img;
        this.title = title;
        this.user = user;
        this.descr = descr;
        this.owner = owner;
        this.accepted = accepted;
    }


    public void setImage(int img) {
        this.img = img;
    }


    public int getImage() {
        return img;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }
}
