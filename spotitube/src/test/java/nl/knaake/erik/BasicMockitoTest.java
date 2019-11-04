package nl.knaake.erik;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
public class BasicMockitoTest {

    @BeforeEach
    private void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
}
