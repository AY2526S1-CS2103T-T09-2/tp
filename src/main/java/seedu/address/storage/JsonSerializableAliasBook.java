package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;

/**
 * An Immutable AliasBook that is serializable to JSON format.
 */
@JsonRootName(value = "aliasbook")
class JsonSerializableAliasBook {

    public static final String MESSAGE_DUPLICATE_ALIAS = "Alias list contains duplicate person(s).";

    private final List<JsonAdaptedAlias> aliases = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAliasBook(@JsonProperty("aliases") List<JsonAdaptedAlias> aliases) {
        this.aliases.addAll(aliases);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAliasBook(AliasBook source) {
        aliases.addAll(source.getAliasList().stream().map(JsonAdaptedAlias::new).collect(Collectors.toList()));
    }

    /**
     * Converts this alias book into the model's {@code AliasBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AliasBook toModelType() throws IllegalValueException {
        AliasBook aliasBook = new AliasBook();
        for (JsonAdaptedAlias jsonAdaptedAlias : aliases) {
            Alias alias = jsonAdaptedAlias.toModelType();
            aliasBook.addAlias(alias);
        }
        return aliasBook;
    }
}
