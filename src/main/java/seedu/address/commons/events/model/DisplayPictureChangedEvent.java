package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Event to trigger reading and storing of image
 */
public class DisplayPictureChangedEvent extends BaseEvent {

    public final String path;
    public final int newPath;

    public DisplayPictureChangedEvent(String path, int newPath) {
        this.path = path;
        this.newPath = newPath;
    }

    @Override
    public String toString() {
        return newPath + "stored";
    }
}
