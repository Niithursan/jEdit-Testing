package org.gjt.sp.jedit.search;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LLM-Generated Test Class for BoyerMooreSearchMatcher
 * Generated using ChatUniTest prompt frameworks.
 */
public class BoyerMooreSearchMatcherLLMTest {

    public void testMatch_FoundInString() throws Exception {
        BoyerMooreSearchMatcher matcher = new BoyerMooreSearchMatcher("test", false);
        SearchMatcher.Match match = matcher.nextMatch("this is a test string", false, false, false, false);

        assertNotNull(match, "Match Object should not be null when pattern is found in the sequence.");
        assertEquals(10, match.start, "Start index should be 10 for 'test'.");
        assertEquals(14, match.end, "End index should be 14 for 'test'.");
    }

    public void testMatch_NotFoundInString() throws Exception {
        BoyerMooreSearchMatcher matcher = new BoyerMooreSearchMatcher("missing", false);
        SearchMatcher.Match match = matcher.nextMatch("this is a test string", false, false, false, false);

        assertNull(match, "Match should be null since 'missing' does not exist in the string.");
    }

    public void testMatch_ReverseMatch() throws Exception {
        BoyerMooreSearchMatcher matcher = new BoyerMooreSearchMatcher("test", false);
        SearchMatcher.Match match = matcher.nextMatch("test ... test", false, true, false, false);

        assertNotNull(match, "Match should be found in reverse search.");
        assertEquals(9, match.start, "Start index should be 9 for the last 'test'.");
        assertEquals(13, match.end, "End index should be 13 for the last 'test'.");
    }
}
