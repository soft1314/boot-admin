package com.neusoft.jn.bautil;

import com.qiyuan.bautil.util.Base62;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class Base62Test {
     @Test
    public void testEncodePositiveNumber() {
        String number = "123456789";
        String actual = Base62.encode(number);
        String expected = Base62.decode(actual).toString();
        assertEquals(expected, number);
    }


     @Test
    public void testDecodeInvalidCharacter() {
        String base62 = "21i3V9!";
        try {
            Base62.decode(base62);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid character: !", e.getMessage());
        }
    }
}