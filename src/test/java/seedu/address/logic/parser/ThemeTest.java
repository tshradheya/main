//@@author chuaweiwen
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
//@@author
