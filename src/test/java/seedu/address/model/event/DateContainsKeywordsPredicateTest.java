package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.HACKATHON;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class DateContainsKeywordsPredicateTest {

    // ---------------------- test() method ----------------------

    @Test
    public void singleKeyword_matchesVenue_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertTrue(predicate.test(HACKATHON));
    }

    @Test
    public void singleKeyword_doesNotMatchVenue_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("Zoom"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void multipleKeywords_oneMatches_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Arrays.asList("Zoom", "UTown"));
        assertTrue(predicate.test(HACKATHON));
    }

    @Test
    public void multipleKeywords_noneMatch_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Arrays.asList("Zoom", "COM2-0205"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void keywordDifferentCase_matchesIgnoreCase_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("uTOWN"));
        assertTrue(predicate.test(HACKATHON));
    }

    @Test
    public void partialWord_noMatch_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("UT"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void emptyKeywords_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(HACKATHON));
    }

    // ---------------------- equals() method ----------------------

    @Test
    public void equals_sameObject_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        DateContainsKeywordsPredicate predicate1 =
                new DateContainsKeywordsPredicate(Collections.singletonList("UTown"));
        DateContainsKeywordsPredicate predicate2 =
                new DateContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        DateContainsKeywordsPredicate predicate1 =
                new DateContainsKeywordsPredicate(Collections.singletonList("UTown"));
        DateContainsKeywordsPredicate predicate2 =
                new DateContainsKeywordsPredicate(Collections.singletonList("Zoom"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_null_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertFalse(predicate.equals("not a predicate"));
    }

    // ---------------------- toString() method ----------------------

    @Test
    public void toString_containsKeywords() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Arrays.asList("UTown", "Zoom"));
        String str = predicate.toString();
        assertTrue(str.contains("UTown"));
        assertTrue(str.contains("Zoom"));
    }
}
