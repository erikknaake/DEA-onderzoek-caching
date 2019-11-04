package nl.knaake.erik.unittests;

import nl.knaake.erik.crosscuttingconcerns.utils.Hasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HasherTest {

    @Test
    void happyDayForHash() {
        assertEquals("d404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db", Hasher.hash("1234"));
    }

    /**
     * Verwachte lege input van: https://www.di-mgt.com.au/sha_testvectors.html
     * */
    @Test
    void emptyStringHash() {
        assertEquals("cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e", Hasher.hash(""));
    }

}
