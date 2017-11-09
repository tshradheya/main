//@@author tshradheya
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getPopularPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPopularPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertPopularCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PopularContactCardHandle;
import guitests.guihandles.PopularContactsPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

public class PopularContactListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getPopularPersons());

    private PopularContactsPanelHandle popularContactsPanelHandle;

    @Before
    public void setUp() {
        PopularContactPanel popularContactPanel = new PopularContactPanel(TYPICAL_PERSONS);
        uiPartRule.setUiPart(popularContactPanel);

        popularContactsPanelHandle = new PopularContactsPanelHandle(getChildNode(popularContactPanel.getRoot(),
                popularContactsPanelHandle.PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            popularContactsPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            ReadOnlyPerson expectedPerson = TYPICAL_PERSONS.get(i);
            PopularContactCardHandle actualCard = popularContactsPanelHandle.getPopularContactCardHandle(i);

            assertCardDisplaysPopularPerson(expectedPerson, actualCard);
            assertEquals("#" + Integer.toString(i + 1) + " ", actualCard.getRank());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        popularContactsPanelHandle.select(INDEX_SECOND_PERSON.getZeroBased());
        guiRobot.pauseForHuman();

        PopularContactCardHandle expectedCard = popularContactsPanelHandle
                .getPopularContactCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PopularContactCardHandle selectedCard = popularContactsPanelHandle.getHandleToSelectedCard();
        assertPopularCardEquals(expectedCard, selectedCard);
    }
}
