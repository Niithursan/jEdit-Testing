package org.gjt.sp.jedit.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LLM-Generated Test Class for PatternSearchMatcher
 * Generated using ChatUniTest prompt frameworks.
 */
public class PatternSearchMatcherLLMTest {

    @Test
    public void testPatternSearchMatcherInitialization_Success() throws Exception {
        PatternSearchMatcher matcher = new PatternSearchMatcher("testPattern", true);
        assertNotNull(matcher, "Matcher should be initialized correctly.");
    }

    @Test
    public void testNextMatch_ReturnsValidMatch_WhenPatternExists() throws Exception {
        PatternSearchMatcher matcher = new PatternSearchMatcher("test", false);
        SearchMatcher.Match match = matcher.nextMatch("this is a test string", false, false, false, false);

        assertNotNull(match, "Match Object should not be null when pattern is found in the sequence.");
        assertEquals(10, match.start, "Start index should be 10 for 'test'.");
        assertEquals(14, match.end, "End index should be 14 for 'test'.");
    }

    @Test
    public void testNextMatch_ReturnsNull_WhenPatternDoesNotExist() throws Exception {
        PatternSearchMatcher matcher = new PatternSearchMatcher("missing", false);
        SearchMatcher.Match match = matcher.nextMatch("this is a test string", false, false, false, false);

        assertNull(match, "Match should be null since 'missing' does not exist in the string.");
    }

    @Test
    public void testNextMatch_ReverseSearch_HandlesCorrectly() throws Exception {
        PatternSearchMatcher matcher = new PatternSearchMatcher("test", false);
        SearchMatcher.Match match = matcher.nextMatch("test ... test", false, true, false, false);

        // PatternSearchMatcher uses Java regex which still finds a valid match in reverse mode.
        assertNotNull(match, "Reverse search should still find a match.");
        assertTrue(match.end > match.start, "End index should be greater than start index for a valid pattern match.");
    }
}
