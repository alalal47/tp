package classify;

import classify.data.DataHandler;
import classify.student.StudentList;
import classify.user.InputParsing;
import classify.user.UserInput;
import classify.ui.UI;
import java.io.IOException;
import java.util.Scanner;

public class Classify {
    public static Scanner in = new Scanner(System.in);

    /**
     * Main entry-point for the Classify application.
     * 
     * @param args          Input arguments when running the program.
     * @throws IOException  Thrown when error reading file.
     */
    public static void main(String[] args) throws IOException {
        //@@author ParthGandhiNUS
        DataHandler.readStudentInfo(StudentList.masterStudentList);
        //@@author blackmirag3
        DataHandler.readArchive(StudentList.archiveList);
        //@@author ParthGandhiNUS
        UI.printWelcomeMessage();
        
        // Takes in input from the user, and processes input to determine if it contains a command and a name   
        String[] userCommand;

        userCommand = UserInput.processInput(in.nextLine());

        // Set up polling for the first word input by the user.
        // If user's first word is "bye", will exit the while loop.
        while (userCommand == null || !(userCommand[0].equals("bye"))){
            InputParsing.parseUserCommand(userCommand, StudentList.masterStudentList, StudentList.recentlyDeletedList,
                    StudentList.archiveList, in);
            UI.printSubsequentUserPrompt();
            userCommand = UserInput.processInput(in.nextLine());
        }

        UI.printEndConversation();
    }
}
