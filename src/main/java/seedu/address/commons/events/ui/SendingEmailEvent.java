package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;

/**
 * Event raised on 'email' command's successful execution
 */
public class SendingEmailEvent extends BaseEvent {

    public final String recipients;
    public final Subject subject;
    public final Body body;
    public final Service service;

    public SendingEmailEvent(String recipients, Subject subject, Body body, Service service) {
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.service = service;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
