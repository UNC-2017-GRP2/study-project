package com.netcracker.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class User {
    private BigInteger userId;
    private String fio;
    private String login;
    private String passwordHash;
    private String confirmPassword;
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String email;
    private List<Address> addresses;
    private String role;
    private String image;

    public User (){}

    public User(BigInteger userId, String fio, String login, String passwordHash, String confirmPassword, String phoneNumber, LocalDate birthday, String email, List<Address> addresses, String role, String image) {
        this.userId = userId;
        this.fio = fio;
        this.login = login;
        this.passwordHash = passwordHash;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.email = email;
        this.addresses = addresses;
        this.role = role;
        this.image = image;
    }


    public BigInteger getUserId() {
        return userId;
    }

    public String getFio() {
        return fio;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public String getRole() {
        return role;
    }

    public String getImage() {
        return image;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fio='" + fio + '\'' +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    private String birthdayToString(){
        SimpleDateFormat sd = new SimpleDateFormat("dd/mm/yyyy");
        return sd.format(birthday);
    }
}
