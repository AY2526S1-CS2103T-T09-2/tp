package seedu.address.logic.commands;

/**
 * Finds and lists all persons or events based on the type passed in
 * address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons or events that match the given criteria.\n"
            + "If 'find member' is entered, it searches for members whose names or roles contain any\n"
            + "of the specified keywords (case-insensitive).\n"
            + "If 'find event' is entered, it searches for events whose names or locations contain any\n"
            + "of the specified keywords (case-insensitive), "
            + "and displays them as a list with index numbers.\n"
            + "Parameters for finding members by name: find member n/[KEYWORDS...]\n"
            + "Example to find members by name: " + COMMAND_WORD + " member n/alice bob charlie\n"
            + "Parameters for finding members by role: find member r/[KEYWORDS...]\n"
            + "Example to find members by role: " + COMMAND_WORD + " member r/project director\n"
            + "Parameters for finding events by name: find event n/[KEYWORDS...]\n"
            + "Example to find events by name: " + COMMAND_WORD + " event n/ideate\n"
            + "Parameters for finding event by location: find event l/[KEYWORDS...]\n"
            + "Example to find events by location: " + COMMAND_WORD + " event l/Queenstown\n";

}
