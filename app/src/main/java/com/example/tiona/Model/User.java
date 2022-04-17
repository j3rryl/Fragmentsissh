package com.example.tiona.Model;

public class User {
    public String fullname,age,email,imageURL,id;
    public User(){

    }

    public User(String id,String fullname, String age, String email, String imageURL) {
        this.id=id;
        this.fullname = fullname;
        this.age = age;
        this.email = email;
        this.imageURL=imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
