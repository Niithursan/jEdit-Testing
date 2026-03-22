package org.gjt.sp.jedit.search;

import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class PatternSearchMatcherTest {

    /**
     * Testing Abstraction: Logic-based Testing
     * Target: nextMatch(CharSequence text, boolean start, boolean end, boolean firstTime, boolean reverse)
     * Rationale: We test the logic paths involving zero-length regex matches 
     * (e.g. `^` or `$`) and how the matcher resolves the while(true) loop.
     */
    @Test
    public void testNextMatch_LogicBased_ZeroWidthAssertions() throws Exception {
        PatternSearchMatcher matcher = new PatternSearchMatcher("^start", false);
        
        // Logical branch: text boundaries trigger start=true vs start=false
        SearchMatcher.Match matchStart = matcher.nextMatch("start line", true, true, true, false);
        assertNotNull(matchStart, "Expected '^' regex to match at start=true branch.");

        SearchMatcher.Match matchMiddle = matcher.nextMatch("start line", false, true, true, false);
        // Depending on internal sol matcher, it might fail or pass, but the logic branch is exercised
        assertNull(matchMiddle, "Expected '^' regex to fail if start=false logic avoids spurious match.");
    }

    /**
     * Testing Abstraction: Graph-based Testing (CFG)
     * Target: removeNonCapturingGroups(Pattern re, int flags)
     * Rationale: Testing the recursion graph. A pattern with 0 non-capturing groups
     * should return immediately. A pattern with 1 should recurse once.
     */
    @Test
    public void testRemoveNonCapturingGroups_GraphBased_Recursion() {
        Pattern p1 = Pattern.compile("normal(group)");
        Pattern result1 = PatternSearchMatcher.removeNonCapturingGroups(p1, 0);
        assertEquals("normal(group)", result1.pattern(), "Path 1: No recursion triggered.");

        Pattern p2 = Pattern.compile("normal(?:noncapture)(group)");
        Pattern result2 = PatternSearchMatcher.removeNonCapturingGroups(p2, 0);
        assertEquals("normal(group)", result2.pattern(), "Path 2: Graph recursion stripped non-capturing group.");
    }
}
