package nl.knaake.erik.domain.login;

import nl.knaake.erik.crosscuttingconcerns.dtos.LoginDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.UserDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.SessionDTO;
import nl.knaake.erik.crosscuttingconcerns.utils.Hasher;
import nl.knaake.erik.restservicelayer.login.ILoginHandler;

import javax.inject.Inject;

/**
 * Houses logic to handle login requests
 */
public class LoginHandler implements ILoginHandler {

    @Inject
    private IUserDAO userDAO;

    /**
     * Gets a new session and registers it to the persistence system
     * @param username user to register a new session for
     * @return the newly registered session
     */
    @Override
    public SessionDTO getSession(String username) {
        UserDTO user = userDAO.getUserDTOByName(username);
        userDAO.addNewSession(user);
        return new SessionDTO(user.getToken(), user.getUser());
    }

    /**
     * Checks wether or not the given login attempt is correct
     * @param loginAttempt Username and password combination that needs to be checked
     * @return True when the given account credentials are in line with what the persistence system knows, otherwise false
     */
    @Override
    public boolean correctPassword(LoginDTO loginAttempt) {
        UserDTO user = userDAO.getUserDTOByName(loginAttempt.getUser());
        String inputHashedPass = Hasher.hash(user.getSalt() + loginAttempt.getPassword());
        return inputHashedPass != null && inputHashedPass.equals(user.getPassword()) && user.getPassword() != null && user.getUser() != null;
    }
}
