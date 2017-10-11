package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FilterCommandTest {

    @Test
    public void execute() throws Exception {
        boolean thrown = false;
        FilterCommand filterCommand = new FilterCommand();
        try {
            filterCommand.execute();
        } catch (Exception e) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}
