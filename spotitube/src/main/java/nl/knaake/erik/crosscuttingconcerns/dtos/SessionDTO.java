package nl.knaake.erik.crosscuttingconcerns.dtos;

import java.util.Objects;

/**
 * Representation of a session for a user
 * Can be transferred between layers and can be parsed to/from a JSON
 */
public class SessionDTO {
    private String token;
    private String user;

    public SessionDTO() {
    }

    public SessionDTO(String token, String user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDTO that = (SessionDTO) o;
        return Objects.equals(token, that.token) &&
                Objects.equals(user, that.user);
    }
}
