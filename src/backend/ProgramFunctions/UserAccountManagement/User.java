package backend.ProgramFunctions.UserAccountManagement;

public abstract class User {
    private String userId;
    private String role;
    private String username;
    private String email;
    private String passwordHash;

    //CLASS CONSTRUCTOR
    public User(String userId, String role, String username, String email, String passwordHash) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }
    public User( String role, String username, String email, String passwordHash) {
        this.role = role;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    //standard getters
    public String getUserId() { return userId; }
    public String getRole() { return role; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }

}
