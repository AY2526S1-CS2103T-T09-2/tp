package seedu.address.logic.commands;

/**
 * Represents a base command for adding entities (such as members or events) to the address book.
 * <p>
 * This abstract class provides the shared command word and usage information for all
 * add-type commands, including {@code AddMemberCommand} and {@code AddEventCommand}.
 * </p>
 *
 * <p>Usage examples:</p>
 * <ul>
 *   <li><b>Members:</b> {@code add member n/John Doe p/98765432 e/johndoe@u.nus.edu y/1 r/President}</li>
 *   <li><b>Events:</b> {@code add event n/CS Workshop d/2025-12-30T13:00 v/NUS COM1-03}</li>
 * </ul>
 *
 * <p>
 * Each subclass of {@code AddCommand} defines how the corresponding entity is created
 * and added to the model.
 * </p>
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE_MEMBER =
            "Parameters for member: add member n/[member name] p/[phone] e/[nus email] y/[year(1-4)] r/[roles]\n"
            + "Example: add member n/John Doe p/98765432 e/johndoe@u.nus.edu y/1 r/President\n";

    public static final String MESSAGE_USAGE_EVENT =
            "Parameters for event: add event n/[event name] d/[date(YYYY-MM-DD)Ttime(HH:MM)] v/[location]\n"
            + "Example: add event n/CS Workshop d/2025-12-30T13:00 v/NUS COM1-03\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member or event to the address book.\n"
            + MESSAGE_USAGE_MEMBER
            + MESSAGE_USAGE_EVENT;
}
