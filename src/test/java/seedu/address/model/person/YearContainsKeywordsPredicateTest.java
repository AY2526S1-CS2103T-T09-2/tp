package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class YearContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstKeywordList = Collections.singletonList("1");
        List<String> secondKeywordList = Arrays.asList("1", "2");

        YearContainsKeywordsPredicate firstPredicate = new YearContainsKeywordsPredicate(firstKeywordList);
        YearContainsKeywordsPredicate secondPredicate = new YearContainsKeywordsPredicate(secondKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        YearContainsKeywordsPredicate firstPredicateCopy = new YearContainsKeywordsPredicate(firstKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_yearContainsKeywords_returnsTrue() {
        // One keyword (exact match)
        YearContainsKeywordsPredicate predicate = new YearContainsKeywordsPredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new PersonBuilder().withYear("1").build()));

        // Multiple keywords (one matches)
        predicate = new YearContainsKeywordsPredicate(Arrays.asList("2", "1"));
        assertTrue(predicate.test(new PersonBuilder().withYear("1").build()));

    }


    @Test
    public void test_yearDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        YearContainsKeywordsPredicate predicate = new YearContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withYear("1").build()));

        // Non-matching keyword
        predicate = new YearContainsKeywordsPredicate(Collections.singletonList("2"));
        assertFalse(predicate.test(new PersonBuilder().withYear("1").build()));

        // Keywords match name/phone/email but not year
        predicate = new YearContainsKeywordsPredicate(Arrays.asList("Alice", "91234567", "alice@u.nus.edu"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@u.nus.edu")
                .withYear("3")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("1", "2");
        YearContainsKeywordsPredicate predicate = new YearContainsKeywordsPredicate(keywords);
        String expected = "seedu.address.model.person.YearContainsKeywordsPredicate{keywords=[1, 2]}";
        assertTrue(predicate.toString().contains("keywords=[1, 2]"));
    }
}
