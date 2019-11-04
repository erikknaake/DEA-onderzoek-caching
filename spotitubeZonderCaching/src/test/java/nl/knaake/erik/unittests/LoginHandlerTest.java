package nl.knaake.erik.unittests;

import nl.knaake.erik.BasicMockitoTest;
import nl.knaake.erik.domain.login.IUserDAO;
import nl.knaake.erik.crosscuttingconcerns.dtos.LoginDTO;
import nl.knaake.erik.domain.login.LoginHandler;
import nl.knaake.erik.crosscuttingconcerns.dtos.UserDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.SessionDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class LoginHandlerTest extends BasicMockitoTest {

    @InjectMocks
    private LoginHandler loginHandler;

    @Mock
    private IUserDAO userDAOMock = mock(IUserDAO.class);

    @Test
    void correctLogin() {
        //Arrange
        String user = "Erik";
        LoginDTO loginAttempt = new LoginDTO("P@ssword1", user);
        UserDTO loginResponse = new UserDTO(user, "1234-1234-1234", "c67c1a8f23993e2acc47d3f9ba20e8e6705f6c61cd34df43d9889cfb4ae269fe09c493cd40b368af9a46ff77de2f3f4368ba4ddcb955ec5317aa9c4c60273beb", "0123456789");

        when(userDAOMock.getUserDTOByName(user)).thenReturn(loginResponse);

        //Act
        boolean result = loginHandler.correctPassword(loginAttempt);

        //Assert
        verify(userDAOMock, times(1)).getUserDTOByName(user);
        assertTrue(result);
    }

    @Test
    void emptyPassword() {
        String user = "Erik";
        LoginDTO loginAttempt = new LoginDTO("", user);
        UserDTO loginResponse = new UserDTO(user, "1234-1234-1234", "c67c1a8f23993e2acc47d3f9ba20e8e6705f6c61cd34df43d9889cfb4ae269fe09c493cd40b368af9a46ff77de2f3f4368ba4ddcb955ec5317aa9c4c60273beb", "0123456789");

        when(userDAOMock.getUserDTOByName(user)).thenReturn(loginResponse);

        //Act
        boolean result = loginHandler.correctPassword(loginAttempt);

        //Assert
        verify(userDAOMock, times(1)).getUserDTOByName(user);
        assertFalse(result);
    }

    @Test
    void emptyUsername() {
        String user = "";
        LoginDTO loginAttempt = new LoginDTO("", user);
        UserDTO loginResponse = new UserDTO();

        when(userDAOMock.getUserDTOByName(user)).thenReturn(loginResponse);

        //Act
        boolean result = loginHandler.correctPassword(loginAttempt);

        //Assert
        verify(userDAOMock, times(1)).getUserDTOByName(user);
        assertFalse(result);
    }

    @Test
    void passwordIsNull() {
        String user = "Erik";
        LoginDTO loginAttempt = new LoginDTO(null, user);
        UserDTO loginResponse = new UserDTO(user, "1234-1234-1234", "c67c1a8f23993e2acc47d3f9ba20e8e6705f6c61cd34df43d9889cfb4ae269fe09c493cd40b368af9a46ff77de2f3f4368ba4ddcb955ec5317aa9c4c60273beb", "0123456789");

        when(userDAOMock.getUserDTOByName(user)).thenReturn(loginResponse);

        //Act
        boolean result = loginHandler.correctPassword(loginAttempt);

        //Assert
        verify(userDAOMock, times(1)).getUserDTOByName(user);
        assertFalse(result);
    }

    @Test
    void usernameIsNull() {
        String user = null;
        LoginDTO loginAttempt = new LoginDTO("P@ssword", user);
        UserDTO loginResponse = new UserDTO(user, "1234-1234-1234", "c67c1a8f23993e2acc47d3f9ba20e8e6705f6c61cd34df43d9889cfb4ae269fe09c493cd40b368af9a46ff77de2f3f4368ba4ddcb955ec5317aa9c4c60273beb", "0123456789");

        when(userDAOMock.getUserDTOByName(user)).thenReturn(loginResponse);

        //Act
        boolean result = loginHandler.correctPassword(loginAttempt);

        //Assert
        verify(userDAOMock, times(1)).getUserDTOByName(user);
        assertFalse(result);
    }

    @Test
    void getSession() {
        //Arrange
        String user = "Erik";
        String token = "1234-1234-1234";
        LoginDTO loginRequest = new LoginDTO("P@ssword1", user);
        UserDTO registeredUser = new UserDTO(user, token, "c67c1a8f23993e2acc47d3f9ba20e8e6705f6c61cd34df43d9889cfb4ae269fe09c493cd40b368af9a46ff77de2f3f4368ba4ddcb955ec5317aa9c4c60273beb", "0123456789");
        SessionDTO expected = new SessionDTO(token, user);

        when(userDAOMock.getUserDTOByName(user)).thenReturn(registeredUser);
        //Act
        SessionDTO actual = loginHandler.getSession(loginRequest.getUser());

        //Assert
        verify(userDAOMock, times(1)).getUserDTOByName(user);
        verify(userDAOMock, times(1)).addNewSession(registeredUser);
        assertEquals(expected, actual);
    }
}
