package seedu.addressbook.ui;

import java.util.ArrayList;
import java.util.List;

import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * Used for formatting text for display. e.g. for adding text decorations.
 */
public class UiFormatter {

    /** A decorative prefix added to the beginning of lines printed by AddressBook */
    private static final String LINE_PREFIX = " ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();


    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";


    /** Offset required to convert between 1-indexing and 0-indexing.  */
    private static final int DISPLAYED_INDEX_OFFSET = 1;


    /** Formats the given strings for displaying to the user. */
    public String format(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String m : messages) {
            sb.append(LINE_PREFIX).append(m.replace("\n", LS + LINE_PREFIX)).append(LS);
        }
        return sb.toString();
    }

    /** Formats the given list of persons for displaying to the user. */
    public String format(List<? extends ReadOnlyPerson> persons) {
        final List<String> formattedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : persons) {
            formattedPersons.add(person.getAsTextShowAll());
        }
        return format(asIndexedList(formattedPersons));
    }
    //@@author muhdharun
    /** Formats given list of strings to the user. */
    public String formatForStrings(List<String> strings) {
        final List<String> formattedTimestamps = new ArrayList<>(strings);
        return format(asIndexedList(formattedTimestamps));
    }
    //@@author
    /** Formats a list of strings as an indexed list. */
    private static String asIndexedList(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as an indexed list item.
     *
     * @param visibleIndex index for this listing
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

}
