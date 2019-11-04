package nl.knaake.erik.domain.login;

import nl.knaake.erik.crosscuttingconcerns.dtos.UserDTO;

/**
 * Allows to communicate with the persistence system about users
 */
public interface IUserDAO {
    UserDTO getUserDTOByName(String username);
    void addNewSession(UserDTO user);
}
