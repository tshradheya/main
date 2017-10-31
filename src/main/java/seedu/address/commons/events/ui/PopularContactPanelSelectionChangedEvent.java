package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PopularContactCard;

/**
 * Represents a selection change in the Popular Contact Panel
 */
public class PopularContactPanelSelectionChangedEvent extends BaseEvent {

    private final PopularContactCard newSelection;

    public PopularContactPanelSelectionChangedEvent(PopularContactCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PopularContactCard getNewSelection() {
        return newSelection;
    }
}
