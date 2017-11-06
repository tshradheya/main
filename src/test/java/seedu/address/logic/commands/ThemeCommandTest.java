//@@author chuaweiwen
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
        Theme standardTheme = new Theme(ThemeList.THEME_SKY, ThemeList.THEME_SKY_PATH);
        ThemeCommand themeCommand = new ThemeCommand(standardTheme);

        String expectedList = "[view/" + ThemeList.THEME_SKY_PATH + ", view/Extensions.css]";
        String expectedMessage = String.format(ThemeCommand.MESSAGE_SET_THEME_SUCCESS, ThemeList.THEME_SKY);
        CommandResult result = themeCommand.execute();

        assertEquals(result.feedbackToUser, expectedMessage);
        assertEquals(expectedList, stage.getScene().getStylesheets().toString());
    }

    @Test
    public void equals() {
        Theme standardTheme = new Theme(ThemeList.THEME_DARK, ThemeList.THEME_DARK_PATH);
        Theme differentTheme = new Theme(ThemeList.THEME_SKY, ThemeList.THEME_SKY_PATH);
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
//@@author
