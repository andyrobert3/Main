package seedu.addressbook.commands;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.storage.StorageFile;

import java.io.IOException;


/**
 * Finds a particular person with the specified NRIC, used for screening.
 * Letters in NRIC must be in lower case.
 */
public class FindCommand extends Command {
    //@@author muhdharun -reused
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds person with specified NRIC \n\t"
            + "Parameters: NRIC ...\n\t"
            + "Example: " + COMMAND_WORD + " s1234567a";

    private String FILE_NOT_FOUND_ERROR = "File not found";
    private String nric;
    private String SCREENING_DATABASE = "screeningHistory.txt";
    private AddressBook addressBookForTest; //For testing

    public FindCommand(String nricToFind) {
        this.nric = nricToFind;
    }

    public String getNric(){
        return nric;
    }

    public void setFile(String file) {
        this.SCREENING_DATABASE = file;
    }

    /**
     *  Used for testing purposes, especially for when testing for wrong file paths
     */
    public void setAddressBook(AddressBook addressBook) {
        this.addressBookForTest = addressBook;
        try {
            StorageFile storage = new StorageFile();
            this.addressBook = storage.load();
        } catch(Exception e) {
        }
    }

    public String getDbName() {
        return SCREENING_DATABASE;
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson personFound = getPersonWithNric();
            return new CommandResult(getMessageForPersonShownSummary(personFound));
        } catch(IOException ioe) {
            return new CommandResult(FILE_NOT_FOUND_ERROR);
        }
    }


    /**
     * Retrieve the person in the records whose name contain the specified NRIC.
     *
     * @return Persons found, null if no person found
     */
    public ReadOnlyPerson getPersonWithNric() throws IOException {
        ReadOnlyPerson result = null;
        if (this.addressBookForTest != null) {
            for (ReadOnlyPerson person : this.addressBookForTest.getAllPersons().immutableListView()) {
                if (person.getNric().getIdentificationNumber().equals(nric)) {
                    this.addressBookForTest.addPersonToDbAndUpdate(person);
                    this.addressBookForTest.updateDatabase(SCREENING_DATABASE);
                    result = person;
                    break;
                }
            }
        } else {
            for (ReadOnlyPerson person : relevantPersons) {
                if (person.getNric().getIdentificationNumber().equals(nric)) {
                    addressBook.addPersonToDbAndUpdate(person);
                    addressBook.updateDatabase(SCREENING_DATABASE);
                    result = person;
                    break;
                }
            }
        }
        return result;
    }

}
