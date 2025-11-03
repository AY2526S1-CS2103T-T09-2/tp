package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Venue string for an Event.
 * Immutable and validated (non-empty, allows most characters).
 */
public class Venue {

    public static final String MESSAGE_CONSTRAINTS =
            "Input for venue should not be blank "
                    + "and must be at most 75 characters long including spaces.";

    // First char non-space, then any char sequence
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Creates a Venue after validating constraints.
     */
    public Venue(String value) {
        requireNonNull(value);
        checkArgument(isValidVenue(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    public static boolean isValidVenue(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 75;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Venue)) {
            return false;
        }
        Venue otherVenue = (Venue) other;
        return value.equalsIgnoreCase(otherVenue.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
