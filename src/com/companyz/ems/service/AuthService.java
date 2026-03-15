package com.companyz.ems.service;

import com.companyz.ems.dao.UserDAO;
import com.companyz.ems.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {
    private UserDAO userDAO;
    private static User currentUser;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) {
        String hash = hashPassword(password);
        User user = userDAO.authenticate(username, hash);
        if (user != null) {
            currentUser = user;
        }
        return user;
    }

    public void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAdmin() {
        return currentUser != null && "HR_ADMIN".equals(currentUser.getRole());
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
