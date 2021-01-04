package app.ridesharingapp.Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String _id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private String username;
    private String address;
    private Boolean emailVerified;
    private Boolean active;
    private String role;
    private String token;
    private String cid;
    private Number userScore;
    private String image;
    private Location location; // change to a location object type -> define it in location model
    private String method;
    // Change this to specific object type
//    private String google;
    private int age;
    private List<Car> cars;

    public User(String _id, String name, String surname, String email, String phoneNumber, String password, String username, String address, Boolean emailVerified, Boolean active, String role, String token, String cid, Number userScore, String image, Location location, String method, int age, List<Car> cars) {
        this._id = _id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.username = username;
        this.address = address;
        this.emailVerified = emailVerified;
        this.active = active;
        this.role = role;
        this.token = token;
        this.cid = cid;
        this.userScore = userScore;
        this.image = image;
        this.location = location;
        this.method = method;
        this.age = age;
        this.cars = cars;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Number getUserScore() {
        return userScore;
    }

    public void setUserScore(Number userScore) {
        this.userScore = userScore;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
