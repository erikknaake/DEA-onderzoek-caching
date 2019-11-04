package nl.knaake.erik.crosscuttingconcerns.dtos;

/**
 * Representation of a user how its stored in the database
 * Can be transferred between layers and can be parsed to/from a JSON
 */
public class UserDTO {
    private String user;
    private String token;
    private String password;
    private String salt;

    public UserDTO() {
    }

    public UserDTO(String user, String token, String password, String salt) {
        this.user = user;
        this.token = token;
        this.password = password;
        this.salt = salt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
