package classify.user;

import classify.student.Student;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParsingTest {

    //@@author tayponghee
    @Test
    public void testByeCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ArrayList<Student> masterStudentList = new ArrayList<>();
        ArrayList<Student> recentlyDeletedList = new ArrayList<>();
        ArrayList<Student> archiveList = new ArrayList<>();
        String [] commands = new String[2];
        commands[0] = "bye";
        InputParsing.parseUserCommand(commands, masterStudentList, recentlyDeletedList,
                archiveList, new Scanner(System.in));

        System.setOut(System.out);

        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "Hope you've had a productive day. See you again! Goodbye!" +
                System.lineSeparator() + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + System.lineSeparator() + "Analysing Inputs..."
                + System.lineSeparator() + "Directory loaded successfully!" + System.lineSeparator()
                + "Data update success!" + System.lineSeparator()
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

        assertEquals(expectedOutput.trim(), printedOutput);
    }

    //@@author blackmirag3
    @Test
    public void testUnknownCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Student> recentlyDeletedList = new ArrayList<>();
        ArrayList<Student> archiveList = new ArrayList<>();
        String [] commands = new String[2];
        commands[0] = "yeet";
        InputParsing.parseUserCommand(commands, students, recentlyDeletedList, archiveList, new Scanner(System.in));
        System.setOut(System.out);
        String printedOutput = outputStream.toString().trim();
        String expectedOutput = "No such command, type \"help\" to view all commands" +
                System.lineSeparator() + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        assertEquals(expectedOutput.trim(), printedOutput);
    }

    //@@author alalal47
    @Test
    public void testHelpCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ArrayList<Student> masterStudentList = new ArrayList<>();
        ArrayList<Student> recentlyDeletedList = new ArrayList<>();
        ArrayList<Student> archiveList = new ArrayList<>();
        String [] commands = new String[2];
        commands[0] = "help";
        InputParsing.parseUserCommand(commands, masterStudentList, recentlyDeletedList,
                archiveList, new Scanner(System.in));

        System.setOut(System.out);

        String printedOutput = outputStream.toString().trim();
        String expectedOutput =
                "add                         Adds a student to the student list, expects a name" +
                                             ", grade and lessons attended," +
                                             " can be used directly with a name e.g. add [name]"
                                             + System.lineSeparator() +
                "edit                        Edits a students details, expects a name" +
                                             ", can be used directly with a name e.g. edit [name]"
                                             + System.lineSeparator() +
                "view                        Views a students details, expects a name" +
                                             ", can be used directly with a name e.g. add [name]"
                                             + System.lineSeparator() +
                "delete                      Deletes a student from the student list, expects a name" +
                                             ", can be used directly with a name e.g. add [name]"
                                             + System.lineSeparator() +
                "restore                     Restore a student deleted within the current session, expects a name, " +
                                             "can be used directly by restore [name]."
                                             + System.lineSeparator() +
                "undo                        Restores the last student deleted in the current session."
                                             + System.lineSeparator() +
                "list                        Displays the list of all students"
                                             + System.lineSeparator() +
                "                            Currently available types: Whole student list, with " +
                                             "total classes attended or by certain subject only"
                                             + System.lineSeparator() +
                "bye                         Exits Classify"
                                             + System.lineSeparator() +
                "sort                        Sorts the student list by the input parameter, expects an" +
                                             " attribute to sort by, can be used directly by sort [type]."
                                             + System.lineSeparator() +
                "                            Currently available types: name, classes, payment"
                                             + System.lineSeparator() +
                "archive                     Removes the specified student from the list and archives them," +
                                             " can be used directly by archive [name]."
                                             + System.lineSeparator() +
                "unarchive                   Removes the specified student from the archive and adds them to the list" +
                                             ", can be used directly by unarchive [name]."
                                             + System.lineSeparator() +
                "help                        Prints this help message" +
            System.lineSeparator() + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

        assertEquals(expectedOutput.trim(), printedOutput);
    }

    //@@author Cryolian
    @Test
    public void testReadInDateCommand() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String userInputDate = "2020-12-12";

        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(userInputDate.getBytes()));
            Scanner scanner = new Scanner(System.in);

            LocalDate parsedDate = InputParsing.readInDate(scanner);
            assertEquals(LocalDate.parse(userInputDate), parsedDate);
        } finally {
            System.setIn(stdin);
        }

        userInputDate = " ";

        try {
            System.setIn(new ByteArrayInputStream(userInputDate.getBytes()));
            Scanner scanner = new Scanner(System.in);

            LocalDate parsedDate = InputParsing.readInDate(scanner);
            assertEquals(LocalDate.now(), parsedDate);
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testParseDateFromString() {
        LocalDate date = InputParsing.parseDateFromString("fiodjsfoi");

        assertEquals(date, LocalDate.now().plusDays(2));

    }
}
