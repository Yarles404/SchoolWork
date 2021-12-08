import edu.iastate.cs228.hw2.WordList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordListTest {
    @Test
    @DisplayName("Deep Clone Test")
    void testClone() {
        WordList storm = new WordList(new String[]{"Hello", "World", "!"});
        WordList trooper = storm.clone();
        trooper.swap(0,1);
        trooper.set(2, "?");
        storm.set(0, "hi");

        assertNotSame(storm.toString(), trooper.toString());
    }
}