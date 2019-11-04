package nl.knaake.erik.unittests;

import nl.knaake.erik.BasicMockitoTest;
import nl.knaake.erik.domain.session.ISessionDAO;
import nl.knaake.erik.domain.session.SessionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class SessionHandlerTest extends BasicMockitoTest {

    private final static int TIME_OUT_TIME = 15;

    @InjectMocks
    private SessionHandler sessionHandler;

    @Mock
    private ISessionDAO sessionDAOMock = mock(ISessionDAO.class);

    @Test
    void validateValidSession() {
        //Arrange
        String token = "1234-1234-1234";
        when(sessionDAOMock.isSessionStillValid(token)).thenReturn(true);

        //Act
        boolean isSessionValid = sessionHandler.validateSession(token);

        //Assert
        verify(sessionDAOMock, times(1)).extendSession(token, 15);
        assertTrue(isSessionValid);
    }

    @Test
    void validateInvalidSession() {
        //Arrange
        String token = "1234-1234-1234";
        when(sessionDAOMock.isSessionStillValid(token)).thenReturn(false);

        //Act
        boolean isSessionValid = sessionHandler.validateSession(token);

        //Assert
        verify(sessionDAOMock, times(0)).extendSession(token, sessionHandler.getTimeoutTime());
        assertFalse(isSessionValid);
    }

    @Test
    void generateTokenLength() {
        String result = sessionHandler.generateNewToken();
        assertEquals(result.length(), 14);
    }
}
