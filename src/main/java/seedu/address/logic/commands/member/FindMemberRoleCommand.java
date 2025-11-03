package seedu.address.logic.commands.member;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose year contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindMemberRoleCommand extends FindCommand {

    private final RoleContainsKeywordsPredicate predicate;

    /**
     * Creates a FindMemberRoleCommand to find the specified {@code RoleContainsKeywordsPredicate}
     */
    public FindMemberRoleCommand(RoleContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof FindMemberRoleCommand)) {
            return false;
        }

        FindMemberRoleCommand otherFindMemberRoleCommand = (FindMemberRoleCommand) other;
        return predicate.equals(otherFindMemberRoleCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
