import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('r', 'q'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertTrue(offByOne.equalChars('h', 'g'));
        assertTrue(offByOne.equalChars('h', 'i'));
        assertFalse(offByOne.equalChars('h', 'o'));
        assertFalse(offByOne.equalChars('m', 'g'));
        assertFalse(offByOne.equalChars('x', 'l'));
        assertTrue(offByOne.equalChars('A', 'B'));
        assertFalse(offByOne.equalChars('A', 'C'));
        assertFalse(offByOne.equalChars('A', 'a'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('A', 'b'));
    }
}
