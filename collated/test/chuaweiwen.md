# chuaweiwen
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
                ThemeCommand.COMMAND_WORD + " " + ThemeNames.THEME_DARK);
        Theme theme = new Theme(ThemeNames.THEME_DARK, ThemeNames.THEME_DARK_CSS);
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
        ThemeCommand expectedCommand = new ThemeCommand(new Theme(ThemeNames.THEME_DARK, ThemeNames.THEME_DARK_CSS));
        assertParseSuccess(parser, ThemeNames.THEME_DARK, expectedCommand);
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
        Theme standardTheme = new Theme(ThemeNames.THEME_DARK, ThemeNames.THEME_DARK_CSS);
        Theme sameTheme = new Theme(ThemeNames.THEME_DARK, ThemeNames.THEME_DARK_CSS);
        Theme differentTheme = new Theme(ThemeNames.THEME_SKY, ThemeNames.THEME_SKY_CSS);
        Theme themeWithDifferentName = new Theme(ThemeNames.THEME_SKY, ThemeNames.THEME_DARK_CSS);
        Theme themeWithDifferentCss = new Theme(ThemeNames.THEME_DARK, ThemeNames.THEME_SKY_CSS);

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
