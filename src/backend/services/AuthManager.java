package backend.services;

import java.util.ArrayList;

import backend.models.*;
import backend.models.parents.User;
import backend.databaseManager.*;
import backend.util.*;

public class AuthManager {

    private UsersDatabaseManager db;

    public AuthManager() {
        db = new UsersDatabaseManager();
    }

    // LOGIN METHOD
    public User login(String email, String password) {
        // 1. Validate fields
        if (!Validator.isFilled(email) || !Validator.isFilled(password))
            return null;

        if (!Validator.isValidEmail(email))
            return null;

        // 2. Load users
        ArrayList<User> users = db.getAllUsers();

        // 3. Hash password
        String hashed = PasswordHasher.hashPassword(password);

        // 4. Compare
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getEmail().equalsIgnoreCase(email) && u.getPasswordHash().equals(hashed)) 
            {
                return db.getUser(u.getUserId());
            }
        }
        return null; // not found
    }


    // REGISTER METHOD TO CREATE A NEW USER
    public User register(String username, String email, String password, String role) {
        // Load users
        ArrayList<User> users = db.getAllUsers();

        // Check existing email
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getEmail().equalsIgnoreCase(email)) {
                return null;
            }
        }

        // Hash password
        String hashed = PasswordHasher.hashPassword(password);

        User newUser;
        // create correct role
        if (role.equals("student")) {
            newUser = new Student(username, email, hashed);
        } else {
            newUser = new Instructor(username, email, hashed);
        }
        String id = db.addUser(newUser);
        db.SaveUsersToFile();
        return db.getUser(id);
    }

}