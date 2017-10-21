package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AddressBookPictureStorageTest {

    private static String PATH = "/pictures/default.png";
    private static String INVALID_PATH = ".2? 2./";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createTest() {
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(PATH);
        assertEquals(addressBookPictureStorage.getAddressBookPicturePath(), PATH);
    }

    /*@Test
    public void createPicturesPath_throwsIoException() throws IOException {
        thrown.expect(IOException.class);
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(INVALID_PATH);
        addressBookPictureStorage.createPictureStorageFolder();
    }
    */

    @Test
    public void saveAddressBookPicturePath_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        AddressBookPictureStorage addressBookPictureStorage = new AddressBookPictureStorage(null);
        addressBookPictureStorage.createPictureStorageFolder();
    }
}
