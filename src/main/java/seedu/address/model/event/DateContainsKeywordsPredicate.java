package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Event}'s {@code Location} matches any of the keywords given.
 */
public class DateContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public DateContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> event.getDate().value.startsWith(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateContainsKeywordsPredicate)) {
            return false;
        }

        DateContainsKeywordsPredicate otherDateContainsKeywordsPredicate =
                (DateContainsKeywordsPredicate) other;
        return keywords.equals(otherDateContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
