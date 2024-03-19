package classify;

import classify.student.Student;
import classify.student.StudentAttributes;
import classify.student.StudentList;
import classify.student.SubjectGrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputParsing {
    private static final String NOTEMPTY = "THIS STRING IS NOT EMPTY";
    private static final String BYE = "bye";
    private static final String LIST = "list";
    private static final String ADD = "add";
    private static final String VIEW = "view";
    private static final String DELETE = "delete";
    private static final String WRONG_INPUT_MESSAGE = "No such command, type \"help\" to view all commands";
    //@@author tayponghee
    private static final String ENTER_STUDENT_DETAILS = "Enter student details: ";
    private static final String ENTER_STUDENT_NAME = "Enter student name: ";
    private static final String STUDENT_DETAILS = "Student details:";
    private static final String NAME = "Name: ";
    private static final String CLASSES_ATTENDED = "Classes Attended: ";
    private static final String STUDENT_NOT_FOUND = "Student not found!";
    private static final String STUDENT_ADDED_SUCCESSFULLY = "Student added successfully!";
    private static final String STUDENT_DELETED_SUCCESSFULLY = "Student removed successfully!";
    private static final String HELP = "help";
    private static final Logger logger = Logger.getLogger(InputParsing.class.getName());
    private static final String SUBJECT = "Subject: ";
    private static final String CURRENT_MARKS_OUT_OF_100 = "Current marks out of 100: ";


    public static void parseUserCommand(String[] userCommand, ArrayList<Student> masterStudentList, Scanner in){
        switch (userCommand[0]) {
        case ADD:
            addStudent(masterStudentList, in, userCommand[1]);
            break;

        case VIEW:
            viewStudent(masterStudentList, in, userCommand[1]);
            Ui.printDivider();
            break;

        //@@author alalal47
        case DELETE:
            deleteStudent(masterStudentList, in, userCommand[1]);
            break;

        case HELP:
            printHelpMessage();
            Ui.printDivider();
            break;

        //@@author ParthGandhiNUS
        case BYE:
            Ui.printEndConversation();
            break;

        case LIST:
            if (masterStudentList != null) {
                StudentList.printCurrentArrayList(masterStudentList);
            } else {
                System.out.println("Student list is null.");
            }
            Ui.printDivider();
            break;

        default:
            System.out.println(WRONG_INPUT_MESSAGE);
            Ui.printDivider();
            break;
        }
    }

    //@@author alalal47
    /**
     * Removes a student from the list.
     *
     * @param masterStudentList The list of all students.
     * @param in                The scanner object to read user input.
     * @param studentName       The name of the student if the user had entered it before being prompted
     */
    private static void deleteStudent(ArrayList<Student> masterStudentList, Scanner in, String studentName) {
        String name;
        if (studentName == null) {
            System.out.print(ENTER_STUDENT_NAME);
            name = in.nextLine().trim();
        } else {
            name = studentName;
        }

        Student foundStudent = findStudentByName(masterStudentList, name);
        if (foundStudent != null) {
            System.out.println(NAME + foundStudent.getName());
            System.out.println(STUDENT_DELETED_SUCCESSFULLY);
        } else {
            System.out.println(STUDENT_NOT_FOUND);
        }
        Ui.printDivider();
        masterStudentList.remove(foundStudent);
    }

    /**
     * Displays the help message to teach users how to use Classify.
     */
    private static void printHelpMessage() {
        System.out.println("add                         Adds a student to the student list, " +
                                                        "expects a name, grade and lessons attended" +
                                                        ", can be used directly with a name e.g. add [name]");
        System.out.println("view                        Views a students details, expects a name" +
                                                        ", can be used directly with a name e.g. add [name]");
        System.out.println("delete                      Deletes a student from the student list, expects a name" +
                                                        ", can be used directly with a name e.g. add [name]");
        System.out.println("list                        Displays the list of all students");
        System.out.println("bye                         Exits Classify");
        System.out.println("help                        Prints this help message");
    }

    //@@author tayponghee
    /**
     * Displays the details of a specific student.
     *
     * @param masterStudentList The list of all students.
     * @param in                The scanner object to read user input.
     * @param studentName       The name of the student if the user had entered it before being prompted
     */
    private static void viewStudent(ArrayList<Student> masterStudentList, Scanner in, String studentName) {
        String name;
        if (studentName == null) {
            System.out.print(ENTER_STUDENT_NAME);
            name = in.nextLine();
        } else {
            name = studentName;
        }

        assert name != null : "Student name cannot be null";

        Student foundStudent = findStudentByName(masterStudentList, name);
        if (foundStudent != null) {
            logger.log(Level.INFO, "Viewing student details: " + name);
            System.out.println(STUDENT_DETAILS);
            System.out.println(NAME + foundStudent.getName());
            StudentAttributes attributes = foundStudent.getAttributes();
            showAttributes(attributes);
        } else {
            logger.log(Level.WARNING, "Student not found: " + name);
            System.out.println(STUDENT_NOT_FOUND);
        }
    }

    /**
     * Displays the attributes of a student.
     *
     * @param attributes The attributes of the student to display.
     */
    private static void showAttributes(StudentAttributes attributes) {
        if (attributes != null) {
            List<SubjectGrade> subjectGrades = attributes.getSubjectGrades();
            if (!subjectGrades.isEmpty()) {
                for (SubjectGrade subjectGrade : subjectGrades) {
                    System.out.println(SUBJECT + subjectGrade.getSubject());
                    System.out.println(CURRENT_MARKS_OUT_OF_100 + subjectGrade.getGrade());
                    System.out.println("Classes Attended: " + subjectGrade.getClassesAttended());
                    System.out.println();
                }
            } else {
                System.out.println("No subjects and grades found for this student.");
            }
        } else {
            System.out.println("No attributes found for this student.");
        }
    }

    /**
     * Adds a new student to the list of students.
     * Does not allow for students of the same name to be added.
     * Checks if attributes added are in the correct format.
     * Allows for multiple subjects to be added, along with their grades and classes
     * attended.
     *
     * @param masterStudentList The list of all students.
     * @param in                The scanner object to read user input.
     * @param studentName       The name of the student if the user had entered it before being prompted
     */
    private static void addStudent(ArrayList<Student> masterStudentList, Scanner in, String studentName) {
        String name;
        if (studentName == null) {
            System.out.println(ENTER_STUDENT_DETAILS);
            name = checkForEmptyName(masterStudentList, in, studentName);
        } else {
            name = checkForEmptyName(masterStudentList,in, studentName);
        }
        assert name != null : "Student name cannot be empty";
        StudentAttributes attributes = new StudentAttributes(name);
        attributeInput(in, attributes);

        if (attributes.getSubjectGrades().isEmpty()) {
            System.out.println("At least one subject must be provided. Please add subjects and grades.");
            Ui.printDivider();
            attributeInput(in, attributes);
        }

        assert attributes.getSubjectGrades() != null : "Subject cannot be empty";

        Student student = new Student(name, attributes);
        masterStudentList.add(student);

        logger.log(Level.INFO, "Student added successfully.");
        System.out.println(STUDENT_ADDED_SUCCESSFULLY);
        Ui.printDivider();
    }

    /**
     * Prompts the user to enter a non-empty name for a student and checks if it already exists in the list.
     * If the name is empty or already exists, prompts the user again until valid input is provided.
     *
     * @param masterStudentList The list of all students.
     * @param in                The scanner object to read user input.
     * @param studentName       The name of the student if the user had entered it before being prompted
     * @return The valid non-empty name entered by the user.
     */
    private static String checkForEmptyName(ArrayList<Student> masterStudentList, Scanner in, String studentName) {
        String name;
        while (true) {
            if (studentName == null) {
                System.out.print(NAME);
                name = in.nextLine().trim();
            } else {
                name = studentName.trim();
                studentName = NOTEMPTY;
            }
            if (name.isEmpty()) {
                System.out.println("Student name cannot be empty. Please enter a valid name.");
                Ui.printDivider();
            } else if (findStudentByName(masterStudentList, name) != null) {
                logger.log(Level.WARNING, "Student with the same name already exists.");
                System.out.println("Student with the same name already exists. Please enter a different name.");
                Ui.printDivider();
            } else {
                break;
            }
        }
        return name;
    }

    /**
     * Prompts the user to enter attributes for a student, including subject, grade, and classes attended.
     * Continues to prompt the user until at least one subject and grade are provided.
     *
     * @param in         The scanner object to read user input.
     * @param attributes The StudentAttributes object to store the attributes of the student.
     */
    private static void attributeInput(Scanner in, StudentAttributes attributes) {
        while (true) {
            System.out.print("Subject: ");
            String subject = in.nextLine().trim();
            if (subject.isEmpty()) {
                logger.log(Level.WARNING, "Empty subject provided.");
                System.out.println("Subject cannot be empty. Please enter a valid subject.");
                Ui.printDivider();
                continue;
            }
            double grade = checkForGradeFormat(in);
            int classesAttended = checkForClassesAttendedFormat(in);

            SubjectGrade subjectGrade = new SubjectGrade(subject, grade, classesAttended);
            attributes.addSubjectGrade(subjectGrade);

            System.out.println("Do you want to add another subject and grade? (yes/no)");
            String response = in.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                break;
            }
        }
    }

    /**
     * Checks the format of the input for the number of classes attended.
     * Prompts the user to enter a valid integer until one is provided.
     *
     * @param in The scanner object to read user input.
     * @return The valid number of classes attended.
     */
    private static int checkForClassesAttendedFormat(Scanner in) {
        int classesAttended;
        while (true) {
            System.out.print(CLASSES_ATTENDED);
            String classesAttendedInput = in.nextLine();
            try {
                classesAttended = Integer.parseInt(classesAttendedInput);
                if (classesAttended < 0) {
                    System.out.println("Classes attended must be 0 or more.");
                    Ui.printDivider();
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Invalid input for classes attended: " + classesAttendedInput, e);
                System.out.println("Invalid input for classes attended. Please enter a valid whole number.");
                Ui.printDivider();
            }
        }
        return classesAttended;
    }

    /**
     * Checks the format of the input for the grade.
     * Prompts the user to enter a valid double within the range [0, 100] until one is provided.
     *
     * @param in The scanner object to read user input.
     * @return The valid grade.
     */
    private static double checkForGradeFormat(Scanner in) {
        double grade;
        while (true) {
            System.out.print(CURRENT_MARKS_OUT_OF_100);
            String gradeInput = in.nextLine();
            try {
                grade = Double.parseDouble(gradeInput);
                if (grade < 0 || grade > 100) {
                    System.out.println("Grade must be between 0 and 100. Please enter a valid number.");
                    Ui.printDivider();
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Invalid input for grade: " + gradeInput, e);
                System.out.println("Invalid input for grade. Please enter a valid number.");
                Ui.printDivider();
            }
        }
        return grade;
    }

    /**
     * Finds a student in the list by their name.
     *
     * @param masterStudentList The list of all students.
     * @param name              The name of the student to search for.
     * @return The student object if found, null otherwise.
     */
    private static Student findStudentByName(ArrayList<Student> masterStudentList, String name) {
        for (Student student : masterStudentList) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }
}