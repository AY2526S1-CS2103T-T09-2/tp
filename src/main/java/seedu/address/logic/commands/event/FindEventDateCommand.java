package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.event.DateContainsKeywordsPredicate;

/**
 * Finds and lists all events in the address book whose location contains
 * any of the specified keywords. The search is case-insensitive and matches
 * partial keywords as well.
 *
 * <p>Example usage:
 * <pre>
 *     findeventlocation library
 * </pre>
 * will list all events with "library" appearing anywhere in their location field.</p>
 */
public class FindEventDateCommand extends FindCommand {

    private final DateContainsKeywordsPredicate predicate;

    /**
     * Constructs a {@code FindEventDateCommand} with the given predicate
     * to filter the event list based on location keywords.
     *
     * @param predicate the condition used to test each event's location.
     */
    public FindEventDateCommand(DateContainsKeywordsPredicate predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return CommandResult.showEvents(String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW,
                model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEventDateCommand)) {
            return false;
        }

        FindEventDateCommand otherFindEventDateCommand = (FindEventDateCommand) other;
        return predicate.equals(otherFindEventDateCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
