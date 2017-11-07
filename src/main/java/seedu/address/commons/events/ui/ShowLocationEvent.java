//@@author tshradheya
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Event raised on 'location' command's successful execution
 */
public class ShowLocationEvent extends BaseEvent {

    public final ReadOnlyPerson person;

    public ShowLocationEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
