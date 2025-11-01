package seedu.address.logic.commands.member;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.person.YearContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose year contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindMemberYearCommand extends FindCommand {

    private final YearContainsKeywordsPredicate predicate;

    /**
     * Creates a FindMemberYearCommand to find the specified {@code YearContainsKeywordsPredicate}
     */
    public FindMemberYearCommand(YearContainsKeywordsPredicate predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return CommandResult.showMembers(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getFilteredPersonList().size()));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindMemberYearCommand)) {
            return false;
        }

        FindMemberYearCommand otherFindMemberYearCommand = (FindMemberYearCommand) other;
        return predicate.equals(otherFindMemberYearCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
