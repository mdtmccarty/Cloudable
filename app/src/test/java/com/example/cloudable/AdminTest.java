package com.example.cloudable;

import static org.junit.Assert.assertEquals;

public class AdminTest {
    MainActivity m = new MainActivity();
    public void addition_isCorrect() {
        assertEquals(false,m.isAdmin);
    }
}
