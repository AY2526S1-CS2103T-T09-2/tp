package seedu.address.logic.commands;

/**
 * Adds a member or event to the address book.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member or event to the address book.\n"
            + "Parameters for member: add member n/[member name] p/[phone] e/[email] a/[address]\n"
            + "Example: add member n/John Doe p/98765432 e/johndoe@gmail.com a/Bukit Batok St 11\n"
            + "Parameters for event: add event n/[event name] d/[date(YYYY-MM-DD)] l/[location]\n"
            + "Example: add event n/CS Workshop d/2025-12-30 l/NUS COM1-03\n";
}
