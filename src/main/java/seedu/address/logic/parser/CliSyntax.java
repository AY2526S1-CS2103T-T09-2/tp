package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_YEAR = new Prefix("y/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_LOCATION = new Prefix("v/");
    public static final Prefix PREFIX_MEMBER = new Prefix("m/");
    public static final Prefix PREFIX_EVENT = new Prefix("e/");

    /**
     * Returns true if the given text contains any of the provided prefixes used in member operations.
     *
     * @param text The text to check.
     * @return True if text contains any of the prefixes, false otherwise.
     */
    public static boolean containsAnyPrefixForMember(String text) {
        String blank = " ";
        return text.contains(blank + PREFIX_YEAR.getPrefix())
                || text.contains(blank + PREFIX_EMAIL.getPrefix())
                || text.contains(blank + PREFIX_PHONE.getPrefix())
                || text.contains(blank + PREFIX_ROLE.getPrefix())
                || text.contains(blank + PREFIX_NAME.getPrefix());
    }

    /**
     * Returns true if the given text contains any of the provided prefixes used in event operations.
     *
     * @param text The text to check.
     * @return True if text contains any of the prefixes, false otherwise.
     */
    public static boolean containsAnyPrefixForEvent(String text) {
        String blank = " ";
        return text.contains(blank + PREFIX_DATE.getPrefix())
                || text.contains(blank + PREFIX_LOCATION.getPrefix())
                || text.contains(blank + PREFIX_NAME.getPrefix());
    }

}
