package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.alias.AliasBook;

/**
 * Storage for alias book
 */
public interface AliasBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAliasBookFilePath();

    /**
     * Returns Alias Book data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<AliasBook> aliasBook() throws DataLoadingException;

    /**
     * @see #getAliasBookFilePath()
     */
    Optional<AliasBook> aliasBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param aliasBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAliasBook(AliasBook aliasBook) throws IOException;

    /**
     * @see #saveAliasBook(AliasBook)
     */
    void saveAliasBook(AliasBook aliasBook, Path filePath) throws IOException;

}
