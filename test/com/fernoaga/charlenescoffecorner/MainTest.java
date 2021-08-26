package com.fernoaga.charlenescoffecorner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void mainTest() {
        Assertions.assertDoesNotThrow(() -> {
            Main.main(new String[0]);
        });
    }
}
