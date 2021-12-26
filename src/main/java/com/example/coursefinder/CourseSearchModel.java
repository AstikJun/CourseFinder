package com.example.coursefinder;

public class CourseSearchModel {
    int id;
    String title;
    String address;
    Integer price;
    String email;
    String description;


    @Override
    public String toString() {
        return "CourseSearchModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPrice() {
        return price;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public CourseSearchModel(Integer id, String title, String address, Integer price, String email, String description) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.price = price;
        this.email = email;
        this.description = description;
    }
}
