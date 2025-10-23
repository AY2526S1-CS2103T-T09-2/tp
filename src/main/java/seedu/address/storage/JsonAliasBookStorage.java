package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.alias.AliasBook;

/**
 * A class to access AliasBook data stored as a json file on the hard disk.
 */
public class JsonAliasBookStorage implements AliasBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAliasBookStorage.class);

    private Path filePath;

    public JsonAliasBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAliasBookFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<AliasBook> aliasBook() throws DataLoadingException {
        return aliasBook(filePath);
    }

    /**
     * Similar to {@link #aliasBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<AliasBook> aliasBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAliasBook> jsonAliasBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAliasBook.class);
        if (!jsonAliasBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAliasBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAliasBook(AliasBook aliasBook) throws IOException {
        saveAliasBook(aliasBook, filePath);
    }

    /**
     * Similar to {@link #saveAliasBook(AliasBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAliasBook(AliasBook aliasBook, Path filePath) throws IOException {
        requireNonNull(aliasBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAliasBook(aliasBook), filePath);
    }

}
