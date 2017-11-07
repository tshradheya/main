# chuaweiwen
###### \java\seedu\address\logic\commands\FilterCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameAndTagsContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_nonExistentNameKeyword_noPersonFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand command = prepareCommand("A", null);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_nonExistentTagKeyword_noPersonFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand command = prepareCommand(null, "A");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_oneNameKeyword_success() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareCommand("Alice", null);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE));
    }

    @Test
    public void execute_oneTagKeyword_success() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FilterCommand command = prepareCommand(null, "friend");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_oneNameAndOneTagKeywords_success() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareCommand("Alice", "friend");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE));
    }

    @Test
    public void execute_multipleTagKeywords_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareCommand(null, "relative colleague");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DANIEL));
    }

    @Test
    public void execute_multipleNameKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand command = prepareCommand("Kurz Elle Kunz", null);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_existingFullNameAsKeywords_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareCommand(ALICE.getName().fullName, null);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE));
    }

    /**
     * Parses {@code userInput} into a {@code FilterCommand}.
     */
    private FilterCommand prepareCommand(String nameInputs, String tagInputs) {
        List<String> nameKeywords = (nameInputs == null) ? Collections.emptyList()
                : Arrays.asList(nameInputs.split("\\s+"));
        List<String> tagKeywords = (tagInputs == null) ? Collections.emptyList()
                : Arrays.asList(tagInputs.split("\\s+"));

        FilterCommand command =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FilterCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\NicknameCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NICKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NICKNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nickname;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class NicknameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_setNickname_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNickname("Some nickname").build();

        NicknameCommand nicknameCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNickname().value);

        String expectedMessage = String.format(NicknameCommand.MESSAGE_SET_NICKNAME_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nicknameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeNickname_success() throws Exception {
        // intialize model with a person with a nickname
        Person intializedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withNickname(VALID_NICKNAME_AMY).build();
        model.updatePerson(model.getFilteredPersonList().get(0), intializedPerson);

        // building a person without a nickname
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNickname("").build();

        NicknameCommand nicknameCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNickname().value);

        String expectedMessage = String.format(NicknameCommand.MESSAGE_REMOVE_NICKNAME_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nicknameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_displayMessageUnchangedNickname_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNickname("").build();

        NicknameCommand nicknameCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNickname().value);

        String expectedMessage = String.format(NicknameCommand.MESSAGE_UNCHANGED_NICKNAME, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nicknameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withNickname("Some nickname").build();
        NicknameCommand nicknameCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNickname().value);

        String expectedMessage = String.format(NicknameCommand.MESSAGE_SET_NICKNAME_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nicknameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        NicknameCommand nicknameCommand = prepareCommand(outOfBoundIndex, VALID_NICKNAME_AMY);

        assertCommandFailure(nicknameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NicknameCommand nicknameCommand = prepareCommand(outOfBoundIndex, VALID_NICKNAME_AMY);

        assertCommandFailure(nicknameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final NicknameCommand standardCommand = new NicknameCommand(INDEX_FIRST_PERSON, NICKNAME_AMY);
        final NicknameCommand commandWithSameValues = new NicknameCommand(INDEX_FIRST_PERSON, NICKNAME_AMY);
        final NicknameCommand commandWithDifferentIndex = new NicknameCommand(INDEX_SECOND_PERSON, NICKNAME_AMY);
        final NicknameCommand commandWithDifferentNickname = new NicknameCommand(INDEX_FIRST_PERSON, NICKNAME_BOB);
        final NicknameCommand commandWithDifferentValues = new NicknameCommand(INDEX_SECOND_PERSON, NICKNAME_BOB);

        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // same type but different nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentNickname));

        // same type but different index and nickname -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentValues));
    }

    /**
     * Returns an {@code NicknameCommand} with parameters {@code index} and {@code remark}
     */
    private NicknameCommand prepareCommand(Index index, String nickname) {
        NicknameCommand nicknameCommand = new NicknameCommand(index, new Nickname(nickname));
        nicknameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return nicknameCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ThemeCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.AddressBookGuiTest;
import seedu.address.logic.parser.Theme;
import seedu.address.logic.parser.ThemeList;

public class ThemeCommandTest extends AddressBookGuiTest {

    @Test
    public void execute_setTheme_success() throws Exception {
        Theme standardTheme = new Theme(ThemeList.THEME_DAY, ThemeList.THEME_DAY_PATH);
        ThemeCommand themeCommand = new ThemeCommand(standardTheme);

        String expectedList = "[" + ThemeList.THEME_DAY_PATH + ", view/Extensions.css]";
        String expectedMessage = String.format(ThemeCommand.MESSAGE_SET_THEME_SUCCESS, ThemeList.THEME_DAY);
        CommandResult result = themeCommand.execute();

        assertEquals(result.feedbackToUser, expectedMessage);
        assertEquals(expectedList, stage.getScene().getStylesheets().toString());
    }

    @Test
    public void equals() {
        Theme standardTheme = new Theme(ThemeList.THEME_NIGHT, ThemeList.THEME_NIGHT_PATH);
        Theme differentTheme = new Theme(ThemeList.THEME_DAY, ThemeList.THEME_DAY_PATH);
        ThemeCommand standardCommand = new ThemeCommand(standardTheme);
        ThemeCommand commandWithSameTheme = new ThemeCommand(standardTheme);
        ThemeCommand commandWithDifferentTheme = new ThemeCommand(differentTheme);

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // Null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different object, but same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameTheme));

        // Different object and different values -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentTheme));
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_nickname() throws Exception {
        NicknameCommand command = (NicknameCommand) parser.parseCommand(
                NicknameCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + NICKNAME_DESC_AMY);
        assertEquals(new NicknameCommand(INDEX_FIRST_PERSON, new Nickname(VALID_NICKNAME_AMY)), command);
    }

    @Test
    public void parseCommand_theme() throws Exception {
        ThemeCommand command = (ThemeCommand) parser.parseCommand(
                ThemeCommand.COMMAND_WORD + " " + ThemeList.THEME_NIGHT);
        Theme theme = new Theme(ThemeList.THEME_NIGHT, ThemeList.THEME_NIGHT_PATH);
        assertEquals(new ThemeCommand(theme), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_filter() throws Exception {
        List<String> nameKeywords = Arrays.asList("foo", "bar", "baz");
        List<String> tagKeywords = Arrays.asList("friends");
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " n/foo bar baz t/friends");
        assertEquals(new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords)), command);
    }
```
###### \java\seedu\address\logic\parser\FilterCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.NameAndTagsContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() throws Exception {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        // No name specified after name prefix -> fail
        assertParseFailure(parser, " n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // No tag specified after tag prefix -> fail
        assertParseFailure(parser, " t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // Name specified but no tag specified after tag prefix -> fail
        assertParseFailure(parser, " n/name t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/ n/name", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // Tag specified but no name specified after name prefix -> fail
        assertParseFailure(parser, " n/ t/tag", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/tag n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // Prefix not used -> fail
        assertParseFailure(parser, " Alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() throws Exception {
        List<String> nameKeywords = new ArrayList<>();
        List<String> tagKeywords = new ArrayList<>();

        // with name but without tag -> success
        nameKeywords.add("Alice");
        FilterCommand expectedFilterCommandWithoutName =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " n/Alice", expectedFilterCommandWithoutName);

        nameKeywords.clear();

        // with tag but wihout name -> success
        tagKeywords.add("friends");
        FilterCommand expectedFilterCommandWithoutTag =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " t/friends", expectedFilterCommandWithoutTag);

        // with both name and tag -> success
        nameKeywords.add("Alice");
        FilterCommand expectedFilterCommandWithAllArgs =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " n/Alice t/friends", expectedFilterCommandWithAllArgs);

        // with both name and tag but different order -> success
        assertParseSuccess(parser, " t/friends n/Alice", expectedFilterCommandWithAllArgs);

        nameKeywords.clear();

        // with more than one tag -> success
        tagKeywords.add("colleagues");
        FilterCommand expectedFilterCommandWithTwoTags =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " t/friends t/colleagues", expectedFilterCommandWithTwoTags);
        assertParseSuccess(parser, " t/friends colleagues", expectedFilterCommandWithTwoTags);

        tagKeywords.clear();

        // with more than one name -> success
        nameKeywords.add("Alice");
        nameKeywords.add("Pauline");
        FilterCommand expectedFilterCommandWithTwoNames =
                new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywords, tagKeywords));
        assertParseSuccess(parser, " n/Alice n/Pauline", expectedFilterCommandWithTwoNames);
        assertParseSuccess(parser, " n/Alice Pauline", expectedFilterCommandWithTwoNames);
    }

}
```
###### \java\seedu\address\logic\parser\NicknameCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.NicknameCommand;
import seedu.address.model.person.Nickname;

public class NicknameCommandParserTest {

    private NicknameCommandParser parser = new NicknameCommandParser();

    @Test
    public void parse_validParts_returnsNicknameCommand() throws Exception {
        // Index and nickname included - nickname accepted
        assertParseSuccess(parser, "1 john",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john")));

        // Index without the nickname - nickname as an empty string accepted
        assertParseSuccess(parser, "1",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("")));

        // Spaces before the index - nickname accepted
        assertParseSuccess(parser, "  1 john",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john")));

        // Spaces between the index and nickname - nickname without spaces at the front accepted
        assertParseSuccess(parser, "1   john",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john")));

        // Spaces within the nickname - nickname with the spaces accepted
        assertParseSuccess(parser, "1 john  peter",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john  peter")));

        // Spaces after nickname - nickname without spaces at the back accepted
        assertParseSuccess(parser, "1 john peter  ",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john peter")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NicknameCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPartsFailure() throws Exception {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NicknameCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ThemeCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_THEME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ThemeCommand;

public class ThemeCommandParserTest {

    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_existingArgs_returnsThemeCommand() throws Exception {
        ThemeCommand expectedCommand = new ThemeCommand(new Theme(ThemeList.THEME_NIGHT, ThemeList.THEME_NIGHT_PATH));
        assertParseSuccess(parser, ThemeList.THEME_NIGHT, expectedCommand);
    }

    @Test
    public void parse_nonExistingArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "unknown_theme", String.format(MESSAGE_UNKNOWN_THEME,
                ThemeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ThemeCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ThemeTest.java
``` java
package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ThemeTest {

    @Test
    public void equals() {
        Theme standardTheme = new Theme(ThemeList.THEME_DARK, ThemeList.THEME_DARK_PATH);
        Theme sameTheme = new Theme(ThemeList.THEME_DARK, ThemeList.THEME_DARK_PATH);
        Theme differentTheme = new Theme(ThemeList.THEME_SKY, ThemeList.THEME_SKY_PATH);
        Theme themeWithDifferentName = new Theme(ThemeList.THEME_SKY, ThemeList.THEME_DARK_PATH);
        Theme themeWithDifferentCss = new Theme(ThemeList.THEME_DARK, ThemeList.THEME_SKY_PATH);

        // same object -> returns true
        assertTrue(standardTheme.equals(standardTheme));

        // null -> returns false
        assertFalse(standardTheme == null);

        // different type -> returns false
        assertFalse(standardTheme.equals("String"));

        // different theme -> returns false
        assertFalse(standardTheme.equals(differentTheme));

        // same themes -> returns true
        assertTrue(standardTheme.equals(sameTheme));

        // same css but different names -> returns false
        assertFalse(standardTheme.equals(themeWithDifferentName));

        // same theme name but different css -> returns false
        assertFalse(standardTheme.equals(themeWithDifferentCss));
    }
}
```
###### \java\seedu\address\model\person\NameAndTagsContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameAndTagsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateNameKeywordList = Arrays.asList("alex", "yeoh");
        List<String> firstPredicateTagsKeywordList = Arrays.asList("friends", "colleagues");

        List<String> secondPredicateNameKeywordList = Arrays.asList("bernice", "yu");
        List<String> secondPredicateTagsKeywordList = Arrays.asList("clients");

        NameAndTagsContainsKeywordsPredicate firstPredicate = new NameAndTagsContainsKeywordsPredicate(
                firstPredicateNameKeywordList, firstPredicateTagsKeywordList);
        NameAndTagsContainsKeywordsPredicate secondPredicate = new NameAndTagsContainsKeywordsPredicate(
                secondPredicateNameKeywordList, secondPredicateTagsKeywordList);
        NameAndTagsContainsKeywordsPredicate predicateWithDifferentTag = new NameAndTagsContainsKeywordsPredicate(
                firstPredicateNameKeywordList, secondPredicateTagsKeywordList);
        NameAndTagsContainsKeywordsPredicate predicateWithDifferentName = new NameAndTagsContainsKeywordsPredicate(
                secondPredicateNameKeywordList, firstPredicateTagsKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameAndTagsContainsKeywordsPredicate firstPredicateCopy = new NameAndTagsContainsKeywordsPredicate(
                firstPredicateNameKeywordList, firstPredicateTagsKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // same names but different tags -> returns false
        assertFalse(firstPredicate.equals(predicateWithDifferentTag));

        // same tags but different names -> returns false
        assertFalse(firstPredicate.equals(predicateWithDifferentName));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One name
        NameAndTagsContainsKeywordsPredicate predicate = new NameAndTagsContainsKeywordsPredicate(
                Arrays.asList("Alice"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // One tag
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));

        // Multiple names
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple tags
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("family", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family", "friends").build()));

        // Both names and tags
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Alice"), Arrays.asList("family"));
        assertTrue(predicate.test(new PersonBuilder().withNameAndTags("Alice Bob", "family").build()));

        // Multiple names in different order
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Bob Alice").build()));

        // Multiple tags in different order
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("family", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Mixed-case name
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case tag
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("fRiEnDs"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Repeated names
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Alice", "Alice"), new ArrayList<>());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Repeated tags
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("friends", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching name
        NameAndTagsContainsKeywordsPredicate predicate = new NameAndTagsContainsKeywordsPredicate(
                Arrays.asList("Carol"), new ArrayList<>());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Non-matching tags
        predicate = new NameAndTagsContainsKeywordsPredicate(new ArrayList<>(), Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        // Only one matching name
        predicate = new NameAndTagsContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), new ArrayList<>());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Only one matching tag
        predicate = new NameAndTagsContainsKeywordsPredicate(
                new ArrayList<>(), Arrays.asList("family", "colleagues"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Match name but does not match tag
        predicate = new NameAndTagsContainsKeywordsPredicate(
                Arrays.asList("Alice", "Bob"), Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withNameAndTags("Alice Bob", "friends").build()));
    }

    @Test
    public void test_missingNameAndTagKeywords_throwsError() throws AssertionError {
        NameAndTagsContainsKeywordsPredicate predicate = new NameAndTagsContainsKeywordsPredicate(
                new ArrayList<>(), new ArrayList<>());
        boolean thrown = false;
        try {
            predicate.test(null);
        } catch (AssertionError e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
```
###### \java\seedu\address\model\person\NicknameTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NICKNAME_BOB;

import org.junit.Test;

public class NicknameTest {

    @Test
    public void equals() {
        Nickname standardNickname = new Nickname(VALID_NICKNAME_AMY);
        Nickname sameNickname = new Nickname(VALID_NICKNAME_AMY);
        Nickname differentNickname = new Nickname(VALID_NICKNAME_BOB);

        // same object -> returns true
        assertTrue(standardNickname.equals(standardNickname));

        // null -> returns false
        assertFalse(standardNickname == null);

        // different type -> returns false
        assertFalse(standardNickname.equals("String"));

        // different nickname -> returns false
        assertFalse(standardNickname.equals(differentNickname));

        // same nickname -> returns true
        assertTrue(standardNickname.equals(sameNickname));
    }
}
```
