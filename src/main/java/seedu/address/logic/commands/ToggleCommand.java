package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.BrowserPanelToggleEvent;

public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";
    public static final String MESSAGE_SUCCESS = "Toggle successful.";

    public CommandResult execute() {
        EventsCenter.getInstance().post(new BrowserPanelToggleEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }


}
