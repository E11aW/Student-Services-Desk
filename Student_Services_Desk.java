// This program creates a list of students and allows the user to 
// lookup information about a student given the ID number.

import java.util.*;

public class Student_Services_Desk {

    private static int nextSID = 22; // must be greater than any of the Students' IDs that we pre-load

    public static void main(String[] args) {
        // initializing variables for later use
        Scanner keyboard = new Scanner(System.in);
        int currentID;
        String newFirstName, newLastName, newClass;

        // initializing starting students
        Map<Integer, Student> studentInfo = new TreeMap<Integer, Student>();
        studentInfo.put(21, new Student(21, "Zog", "TheDestroyer",
                new ArrayList<String>(List.of("BIT 143", "MATH 411", "ENG 120"))));
        studentInfo.put(20,
                new Student(20, "Mary", "Sue", new ArrayList<String>(List.of("BIT 142", "MATH 142", "ENG 100"))));
        studentInfo.put(1,
                new Student(1, "Joe", "Bloggs", new ArrayList<String>(List.of("BIT 115", "MATH 141", "ENG 101"))));

        char choice = 'L'; // anything but 'q' is fine
        while (choice != 'q') {
            System.out.println("\nWhat would you like to do next? ");
            System.out.println("F - Find a specific student");
            System.out.println("L - List all the students (for debugging purposes)");
            System.out.println("A - Add a new student");
            System.out.println("D - Delete an existing student");
            System.out.println("M - Modify an existing student");
            System.out.println("Q - Quit (exit) the program");
            System.out.print("What is your choice?\n(type in the letter & then the enter/return key) ");
            choice = keyboard.nextLine().trim().toLowerCase().charAt(0);
            System.out.println();

            switch (choice) {
                case 'f':
                    System.out.println("Find a student (based on their ID number):\n");
                    System.out.print("What is the ID number of the student to find? ");
                    currentID = keyboard.nextInt();
                    keyboard.nextLine();
                    // checks if SID is valid, then prints information
                    if (studentInfo.containsKey(currentID)) {
                        System.out.printf("%s, %s (SID=%d)\nClasses:\n", studentInfo.get(currentID).getLastName(), studentInfo.get(currentID).getFirstName(), currentID);
                        for (String eachClass: studentInfo.get(currentID).getClasses()) {
                            System.out.println("\t" + eachClass);
                        }
                    } else {
                        System.out.println("Didn't find a student with ID # " + currentID);
                    }
                    break;

                case 'l':
                    // uses iterator to print each student's information
                    Iterator<Integer> itr = studentInfo.keySet().iterator();
                    System.out.println("The following students are registered:");
                    while (itr.hasNext()) {
                        currentID = itr.next();
                        System.out.println(studentInfo.get(currentID).toString());
                    }
                    break;
                case 'a':
                    // asks user for new student information
                    newClass = "temp";
                    System.out.println("Adding a new student\n");
                    System.out.println("Please provide the following information:");
                    System.out.print("Student's first name? ");
                    newFirstName = keyboard.nextLine();
                    System.out.print("Student's last name? ");
                    newLastName = keyboard.nextLine();
                    // creates a new array of classes and adds each new class, making sure it is not an empty line
                    ArrayList<String> newClasses = new ArrayList<String>();
                    while (!newClass.isEmpty()) {
                        System.out.println("Type the name of class, or leave empty to stop.  Press enter/return regardless");
                        newClass = keyboard.nextLine();
                        if (newClass.length() > 0) {
                            newClasses.add(newClass);
                        }
                    }
                    // creates new student, SID is increased first to match SIDs of example on Canvas
                    nextSID++;
                    studentInfo.put(nextSID, new Student(nextSID, newFirstName, newLastName, new ArrayList<String>(newClasses)));
                    // resets arraylist for future use
                    newClasses.clear();
                    break;
                case 'd':
                // checks SID is valid and removes student if it is
                    System.out.println("Deleting an existing student\n");
                    System.out.print("What is the ID number of the student to delete? ");
                    currentID = keyboard.nextInt();
                    keyboard.nextLine();
                    if (studentInfo.containsKey(currentID)) {
                        studentInfo.remove(currentID);
                        System.out.println("Student account found and removed from the system!");
                    } else {
                        System.out.println("Didn't find a student with ID # " + currentID);
                    }
                    break;
                case 'm':
                    // checks SID is valid, then starts updating student information
                    System.out.println("Modifying an existing student\n");
                    System.out.print("What is the ID number of the student to modify? ");
                    currentID = keyboard.nextInt();
                    keyboard.nextLine();
                    if (studentInfo.containsKey(currentID)) {
                        System.out.println(
                            "Student account found!\nFor each of the following type in the new info or leave empty to keep the existing info.  Press enter/return in both cases.");
                        System.out.print("Student's first name: " + studentInfo.get(currentID).getFirstName() + " New first name? ");
                        newFirstName = keyboard.nextLine();
                        // these if statements check if newNames are empty and need to be left alone
                        if (newFirstName.length() > 0) {
                            studentInfo.get(currentID).setFirstName(newFirstName);
                        }
                        System.out.print("Student's last name: " + studentInfo.get(currentID).getLastName() + " New last name? ");
                        newLastName = keyboard.nextLine();
                        if (newLastName.length() > 0) {
                            studentInfo.get(currentID).setLastName(newLastName);
                        }
                        // lists current classes and goes through each one to check if user wants to delete them
                        System.out.println("Updating class list");
                        System.out.println("Here are the current classes: " + studentInfo.get(currentID).getClasses());
                        System.out.println("This program will go through all the current classes.");
                        System.out.println(
                                "For each class it will print the name of the class and then ask you if you'd like to delete or keep it.");
                        // creates new arraylist that will only get the new/kept classes added to it
                        ArrayList<String> updatedClassList = new ArrayList<String>();
                        for (String currentClass: studentInfo.get(currentID).getClasses()) {
                            System.out.println(currentClass + "\nType d<enter/return> to remove, or just <enter/return> to keep ");
                            if (keyboard.nextLine().equalsIgnoreCase("d")) {
                                System.out.println("Removing " + currentClass + "\n");
                            } else {
                                System.out.println("Keeping " + currentClass + "\n");
                                updatedClassList.add(currentClass);
                            }
                        }
                        // adds new classes to arraylist above
                        newClass = "temp";
                        while (newClass.length() > 0) {
                            System.out.println("Type the name of the class you'd like to add, or leave empty to stop.  Press enter/return regardless");
                            newClass = keyboard.nextLine();
                            if (newClass.length() > 0) {
                                updatedClassList.add(newClass);
                            }   
                        }
                        // updates classes and clears arraylist for later use, then prints student's updated info
                        studentInfo.get(currentID).setClasses(new ArrayList<String>(updatedClassList));
                        updatedClassList.clear();
                        System.out.println("Here's the student's new, updated info: " + studentInfo.get(currentID).toString());
                    } else {
                        System.out.println("Didn't find a student with ID # " + currentID);
                    }
                    break;
                case 'q':
                    System.out.println("Thanks for using the program - goodbye!\n");
                    break;
                default:
                    System.out.println("Sorry, but I didn't recognize the option " + choice);
                    break;
            }

        }
    }
}