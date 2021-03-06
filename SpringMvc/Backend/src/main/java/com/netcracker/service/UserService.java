package com.netcracker.service;

import com.netcracker.model.Address;
import com.netcracker.model.User;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    List<User> getAllCouriers();
    User getByUsername(String username);
    User getUserById (BigInteger userId);
    User findByEmail(String email) throws SQLException;
    void saveUser(User user);
    void updateUser(User oldUser, User newUser);
    void changeRole(BigInteger userId, String role);
    boolean isLoginExist(String login);
    boolean isEmailExist(String email);
    boolean isYourLoginForUpdateUser(String login, String password);
    boolean isYourEmailForUpdateUser(String email, String password);
    boolean isEqualsPassword(String password, BigInteger userId);
    void updatePassword(BigInteger userId, String password);
    void updateAddresses(BigInteger userId, List<Address> addresses);
    void removeUserById(BigInteger userId) throws SQLException;
    public BigInteger getObjectId();
    void saveUserImage(BigInteger userId, String imageName) throws SQLException;
}
