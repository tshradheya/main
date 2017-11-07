//@@author chuaweiwen
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
//@@author
