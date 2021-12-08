import edu.iastate.cs228.hw2.Alphabet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlphabetTest {
    @Test
    @DisplayName("Char Array Constructor")
    void test1() {
        Alphabet a = new Alphabet(new char[]{'c', 'a', 'b', '#'});
        assertEquals("{#, 4} {a, 2} {b, 3} {c, 1} ", a.toString());
    }

    @Test
    @DisplayName("IsValid, GetPosition, and BinarySearch")
    void test2() {
        Alphabet a = new Alphabet(new char[]{'c', 'a', 'b', '#'});
        assertAll( () -> {
            assertTrue(a.isValid('a'));
            assertFalse(a.isValid('$')); }
        );

    }
}