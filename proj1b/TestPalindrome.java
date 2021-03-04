import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertFalse(palindrome.isPalindrome(null));
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("i"));
        assertTrue(palindrome.isPalindrome("level"));
        assertTrue(palindrome.isPalindrome("abba"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindromeCC() {
        OffByOne offByOne = new OffByOne();
        OffByN offBy3 = new OffByN(3);
        assertFalse(palindrome.isPalindrome(null, offByOne));
        assertFalse(palindrome.isPalindrome(null, offBy3));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("", offBy3));
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertTrue(palindrome.isPalindrome("a", offBy3));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("aadd", offBy3));
        assertFalse(palindrome.isPalindrome("xyzzyz", offByOne));
        assertFalse(palindrome.isPalindrome("abccde", offBy3));
    }
}
