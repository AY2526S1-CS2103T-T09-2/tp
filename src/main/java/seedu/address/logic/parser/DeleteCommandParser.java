package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.member.DeleteMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        // Split the current args input into at most 2 parts: [type, index]
        String[] argsParts = args.trim().split("\\s+");

        if (argsParts.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        // Type indicates whether to delete member or event
        String type = argsParts[0];
        String indexToDelete = argsParts[1];
        boolean isInvalidType = !type.equalsIgnoreCase("member") && !type.equalsIgnoreCase("event");

        if (isInvalidType) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_TYPE, DeleteCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(indexToDelete);
            //return new DeleteMemberCommand(index);
            return matchType(type, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Checks if it is member or event command
     * @param type Member or Event
     * @param index Position to be removed
     * @return DeleteMemberCommand or DeleteEventCommand
     */
    public DeleteCommand matchType(String type, Index index) {
        if (type.equalsIgnoreCase("member")) {
            return new DeleteMemberCommand(index);
        } else if (type.equalsIgnoreCase("event")) {
            return new DeleteEventCommand(index);
        } else {
            return null;
        }
    }

}
