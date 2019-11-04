package nl.knaake.erik.crosscuttingconcerns.dtos;

import java.io.Serializable;

/**
 * Representation of a session with its expirationDate
 * Can be transferred between layers and can be parsed to/from a JSON
 */
public class SessionExpirationDTO implements Serializable {
    private String token;
    private String expirationDate;

    public SessionExpirationDTO(String token, String expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public SessionExpirationDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "SessionExpirationDTO{" +
                "token='" + token + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
