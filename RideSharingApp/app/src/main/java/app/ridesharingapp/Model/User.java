package app.ridesharingapp.Model;

import java.util.List;

public class User {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private String cid;
    private int age;
    private List<Car> car;
    private String token;
    private Location location;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User() {
    }

    public User(String name, String surname, String email, String phoneNumber, String password, String address, String cid, int age, List<Car> cars) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.cid = cid;
        this.age = age;
        this.car = cars;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Car> getCars() {
        return car;
    }

    public void setCars(List<Car> cars) {
        this.car = cars;
    }

    public void addCar(Car car) {
        this.car.add(car);
    }

    public void receiveRemovedRideNotification() {

    }

    public String getCompleteName() {
        return this.name + " " + this.surname;
    }
}
