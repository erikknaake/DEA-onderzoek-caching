package nl.knaake.erik.crosscuttingconcerns.dtos;

/**
 * Representation of a login attempt
 * Can be transferred between layers and can be parsed to/from a JSON
 */
public class LoginDTO {

    private String password, user;

    public LoginDTO(String password, String user) {
        this.password = password;
        this.user = user;
    }

    public LoginDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
