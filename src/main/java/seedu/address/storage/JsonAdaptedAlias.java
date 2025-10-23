package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.Alias;

/**
 * Jackson-friendly version of {@link Alias}.
 */
class JsonAdaptedAlias {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Alias's %s field is missing!";

    private final String commandWord;
    private final String aliasWord;

    /**
     * Constructs a {@code JsonAdaptedAlias} with the given alias details.
     */
    @JsonCreator
    public JsonAdaptedAlias(@JsonProperty("commandWord") String commandWord,
                            @JsonProperty("aliasWord") String aliasWord) {
        this.commandWord = commandWord;
        this.aliasWord = aliasWord;
    }

    /**
     * Converts a given {@code Alias} into this class for Jackson use.
     */
    public JsonAdaptedAlias(Alias source) {
        commandWord = source.getCommandWord();
        aliasWord = source.getAliasWord();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Alias} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Alias toModelType() throws IllegalValueException {
        if (commandWord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "command word"));
        }
        if (aliasWord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "alias word"));
        }
        if (commandWord.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "command word"));
        }
        if (aliasWord.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "alias word"));
        }

        return new Alias(commandWord, aliasWord);
    }
}
