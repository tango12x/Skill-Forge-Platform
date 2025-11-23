package backend.models;
import backend.models.parents.*;
/**
 *
 * @author ahmme
 */
public class Admin extends User {
    
    // Constructor when ID is given
    public Admin(String userId, String username, String email, String passwordHash) {
        super(userId, "admin", username, email, passwordHash);
    }

    // Constructor when ID is NOT given
    public Admin(String username, String email, String passwordHash) {
        super("admin", username, email, passwordHash);
    }

}    

