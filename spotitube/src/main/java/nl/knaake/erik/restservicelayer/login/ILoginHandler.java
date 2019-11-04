package nl.knaake.erik.restservicelayer.login;

import nl.knaake.erik.crosscuttingconcerns.dtos.LoginDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.SessionDTO;

/**
 * Offers functionality to get if a session is valid
 */
public interface ILoginHandler {
    SessionDTO getSession(String username);
    boolean correctPassword(LoginDTO loginAttempt);
}
