package org.gjt.sp.jedit.search;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreSearchMatcher_match_1_4_Test {

    private BoyerMooreSearchMatcher matcher;

    @BeforeEach
    public void setUp() throws Exception {
        matcher = new BoyerMooreSearchMatcher("test", false);
    }

    @Test
    public void testMatch_EmptyPattern() throws Exception {
        matcher = new BoyerMooreSearchMatcher("", true);
        assertEquals(0, matcher.match("this is an empty pattern", false));
    }

    @Test
    public void testMatch_NullInput() throws Exception {
        assertThrows(NullPointerException.class, () -> matcher.match(null, false));
    }

    @Test
    public void testGetSkipIndex() throws Exception {
        Method method = BoyerMooreSearchMatcher.class.getDeclaredMethod("getSkipIndex", char.class);
        method.setAccessible(true);
        assertEquals(116, (int) method.invoke(null, 't'));
    }
}
