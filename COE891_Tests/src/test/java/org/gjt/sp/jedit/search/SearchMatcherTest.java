package org.gjt.sp.jedit.search;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SearchMatcherTest {

    // Concrete mock to test the abstract methods
    private static class MockSearchMatcher extends SearchMatcher {
        @Override
        public Match nextMatch(CharSequence text, boolean start, boolean end, boolean firstTime, boolean reverse) {
            return null;
        }
    }

    /**
     * Testing Abstraction: Logic-based Testing
     * Target: isWholeWord(CharSequence text, int start, int end)
     * Rationale: Testing the multiple predicates checking `start != 0` and `end < text.length()`.
     */
    @Test
    public void testIsWholeWord_LogicBased_BoundaryPredicates() {
        MockSearchMatcher matcher = new MockSearchMatcher();
        matcher.setNoWordSep("_"); // Default word separators

        // Branch 1: start != 0 evaluates false, end < length evaluates false -> true
        assertTrue(matcher.isWholeWord("word", 0, 4));

        // Branch 2: start != 0 evaluates true, triggers `isEndWord`
        assertTrue(matcher.isWholeWord("a word a", 2, 6));

        // Branch 3: start evaluates false, end evaluates true triggering `isEndWord`
        assertTrue(matcher.isWholeWord("word a", 0, 4));
    }

    /**
     * Testing Abstraction: Input Space Partitioning (ISP) / BVA
     * Target: isEndWord(char current, char next)
     * Rationale: Partitioning inputs into [Word Char, Word Char], 
     * [Word Char, Non-Word Char] boundaries based on `noWordSep`.
     */
    @Test
    public void testIsEndWord_ISP_CharacterPartitions() {
        // We will reflectively access isEndWord since it's private/protected
        MockSearchMatcher matcher = new MockSearchMatcher();
        matcher.setNoWordSep("_");

        // We can test this by calling `isWholeWord` which wraps `isEndWord`.
        // Partition 1: Adjacent word characters (fails whole word)
        assertFalse(matcher.isWholeWord("wordy", 0, 4), "Partition 'Word+Word' must not be an end word break.");

        // Partition 2: Word character appended by non-word (e.g., space)
        assertTrue(matcher.isWholeWord("word ", 0, 4), "Partition 'Word+NonWord' must be an end word break.");
    }
}
