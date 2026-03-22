package org.gjt.sp.jedit.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreSearchMatcherTest {

    /**
     * Testing Abstraction: Input Space Partitioning (ISP)
     * Target: nextMatch(CharSequence text, boolean start, boolean end, boolean firstTime, boolean reverse)
     * Rationale: Partitioning input text into [Empty Sequence], [Sequence without match], 
     * and [Sequence strictly matching the pattern length].
     */
    @Test
    public void testNextMatch_ISP_TextPartitions() throws Exception {
        BoyerMooreSearchMatcher matcher = new BoyerMooreSearchMatcher("test", false);

        // Partition 1: Empty text
        SearchMatcher.Match m1 = matcher.nextMatch("", true, true, true, false);
        assertNull(m1, "Partition 1: Empty text must yield no match");

        // Partition 2: Text without match
        SearchMatcher.Match m2 = matcher.nextMatch("hello world", true, true, true, false);
        assertNull(m2, "Partition 2: Distinct text sequence must yield no match");

        // Partition 3: Text exactly matching
        SearchMatcher.Match m3 = matcher.nextMatch("test", true, true, true, false);
        assertNotNull(m3, "Partition 3: Exact match must not be null");
        assertEquals(0, m3.start, "Partition 3: Start should be 0");
    }

    /**
     * Testing Abstraction: Mutation Testing
     * Target: match(CharSequence text, boolean reverse)
     * Rationale: Killing the mutant where `if(ignoreCase)` is omitted or flipped.
     * We supply upper/lowercase partitioned text to ensure case heuristics work identically.
     */
    @Test
    public void testMatch_Mutation_IgnoreCaseMutant() throws Exception {
        BoyerMooreSearchMatcher matcherInsensitive = new BoyerMooreSearchMatcher("Mutant", true);
        
        // If ignoreCase logic was mutated/removed, this match would fail
        int pos = matcherInsensitive.match("mutant test", false);
        assertEquals(0, pos, "Mutant survived: ignoreCase condition failed to resolve case disparity.");
    }

    /**
     * Testing Abstraction: Logic-based Testing
     * Target: generateSuffixArray(boolean reverse)
     * Rationale: We test the logic path generation based on `reverse` boolean predicate.
     * The internal loop branches should generate completely inversed shift arrays.
     */
    @Test
    public void testGenerateSuffixArray_LogicBased_ReversePredicate() throws Exception {
        BoyerMooreSearchMatcher fwdMatcher = new BoyerMooreSearchMatcher("findme", false);
        
        // This implicitly calls generateSuffixArray(false)
        int fwdPos = fwdMatcher.match("you will findme here", false);
        assertEquals(9, fwdPos);

        // This implicitly calls generateSuffixArray(true) evaluating the secondary logical branch
        int revPos = fwdMatcher.match("you will findme here", true);
        // "reverse" match logic evaluates text backwards and doesn't find the string in this boundary
        assertEquals(-1, revPos, "Logical branch for reverse suffix generation executed and returned -1.");
    }
}
