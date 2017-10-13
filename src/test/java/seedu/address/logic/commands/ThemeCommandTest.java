package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ThemeCommandTest {

    @Test
    public void executeUndoableCommand() throws Exception {
        boolean thrown = false;
        ThemeCommand themeCommand = new ThemeCommand();
        try {
            themeCommand.executeUndoableCommand();
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
