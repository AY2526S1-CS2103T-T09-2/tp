package seedu.address.model.alias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores and manages mappings between command words and their aliases.
 * Each alias is unique and maps to one command word.
 */
public class AliasBook {

    private final Map<String, String> aliasMap = new HashMap<>();

    /**
     * Adds a new alias mapping to the alias book.
     *
     * @param alias Alias to add.
     */
    public void addAlias(Alias alias) {
        aliasMap.put(alias.getAliasWord(), alias.getCommandWord());
    }

    /**
     * Removes an alias from the alias book.
     *
     * @param key Alias word to remove.
     */
    public void removeAlias(String key) {
        aliasMap.remove(key);
    }

    /**
     * Returns true if the given alias word exists in the alias book.
     *
     * @param aliasWord Alias word to check.
     * @return True if alias exists, false otherwise.
     */
    public boolean isAliasPresent(String aliasWord) {
        return aliasMap.containsKey(aliasWord);
    }

    /**
     * Returns true if the given command word has an alias.
     *
     * @param commandWord Command word to check.
     * @return True if command has an alias, false otherwise.
     */
    public boolean isCommandPresent(String commandWord) {
        return aliasMap.containsValue(commandWord);
    }

    /**
     * Returns the command word mapped to the given alias word.
     *
     * @param aliasWord Alias word to resolve.
     * @return Command word if alias exists, or {@code null} otherwise.
     */
    public String getCommandWordForAlias(String aliasWord) {
        return aliasMap.get(aliasWord);
    }

    /**
     * Returns the alias word corresponding to the given command word.
     * If no alias is found, the command word itself is returned.
     *
     * @param commandWord Command word to look up.
     * @return Alias word or the command word if no alias exists.
     */
    public String getAliasForCommandWord(String commandWord) {
        return aliasMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(commandWord))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    /**
     * Removes all aliases from the alias book.
     */
    public void clear() { // Method for future use
        aliasMap.clear();
    }

    /**
     * Converts the Alias Book into a list of aliases
     * @return List of type Alias
     */
    public List<Alias> getAliasList() {
        return aliasMap.entrySet()
                .stream()
                .map(entry -> new Alias(entry.getValue(), entry.getKey()))
                .toList();
    }

    /**
     * Returns an unmodifiable copy of the alias map.
     *
     * @return Unmodifiable view of alias mappings.
     */
    public Map<String, String> getAliasMap() {
        return Map.copyOf(aliasMap);
    }

    @Override
    public String toString() {
        return aliasMap.toString();
    }
}
