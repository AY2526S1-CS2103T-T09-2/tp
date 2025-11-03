package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstKeywordList = Collections.singletonList("1");
        List<String> secondKeywordList = Arrays.asList("1", "2");

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstKeywordList);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy = new RoleContainsKeywordsPredicate(firstKeywordList);
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
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new PersonBuilder().withYear("1").build()));

        // Multiple keywords (one matches)
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("2", "1"));
        assertTrue(predicate.test(new PersonBuilder().withYear("1").build()));

    }


    @Test
    public void test_yearDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withYear("1").build()));

        // Non-matching keyword
        predicate = new RoleContainsKeywordsPredicate(Collections.singletonList("2"));
        assertFalse(predicate.test(new PersonBuilder().withYear("1").build()));

        // Keywords match name/phone/email but not year
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Alice", "91234567", "alice@u.nus.edu"));
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
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(keywords);
        String expected = "seedu.address.model.person.RoleContainsKeywordsPredicate{keywords=[1, 2]}";
        assertTrue(predicate.toString().contains("keywords=[1, 2]"));
    }
}
