package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

/**
 * Represents delete command with hidden internal logic and the ability to be executed.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the member/event identified by the index number used in the displayed member/event list.\n"
            + "Parameters: TYPE (either 'member' or 'event'), INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " member 1\n"
            + "Example: " + COMMAND_WORD + " event 1";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public Index getTargetIndex() {
        return this.targetIndex;
    }
}
