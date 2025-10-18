package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.event.LocationContainsKeywordsPredicate;

/**
 * Finds and lists all events in address book whose location contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindEventLocationCommand extends FindCommand {

    private final LocationContainsKeywordsPredicate predicate;

    /**
     * Creates a FindMemberCommand to find the specified {@code NameContainsKeywordsPredicate}
     */
    public FindEventLocationCommand(LocationContainsKeywordsPredicate predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEventLocationCommand)) {
            return false;
        }

        FindEventLocationCommand otherFindEventLocationCommand = (FindEventLocationCommand) other;
        return predicate.equals(otherFindEventLocationCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
