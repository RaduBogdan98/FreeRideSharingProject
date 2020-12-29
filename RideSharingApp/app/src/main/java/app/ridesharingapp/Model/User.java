package app.ridesharingapp.Model;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private int age;
    private String address;
    private String licenseId;
    private List<Car> cars;

    public User(String name, String email, String password, String phoneNumber, int age){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public String getName() {
        return name;
    }
}
