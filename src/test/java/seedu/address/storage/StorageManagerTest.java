package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;

/**
 * Integration tests between {@link StorageManager} and its JSON-backed storage classes.
 */
public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonAliasBookStorage aliasBookStorage = new JsonAliasBookStorage(getTempFilePath("alias"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        // Ensure static alias map starts clean for each test
        new AliasBook().clear();
        storageManager = new StorageManager(addressBookStorage, aliasBookStorage, userPrefsStorage);
    }

    @AfterEach
    public void tearDown() {
        new AliasBook().clear();
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest}.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of AddressBook saving/reading is done in {@link JsonAddressBookStorageTest}.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void aliasBookReadSave() throws Exception {
        AliasBook original = new AliasBook();
        original.clear();
        original.addAlias(new Alias(ListCommand.COMMAND_WORD, "ls"));
        storageManager.saveAliasBook(original);
        AliasBook retrieved = storageManager.aliasBook().get();
        assertEquals(original.getAliasList(), retrieved.getAliasList());
    }

    @Test
    public void getFilePaths_notNull() {
        assertNotNull(storageManager.getAddressBookFilePath());
        assertNotNull(storageManager.getAliasBookFilePath());
    }
}
