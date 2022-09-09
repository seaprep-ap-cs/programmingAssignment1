package pa1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestNurseryRhyme {
    private static final String EXPECTED_OUTPUT = "expected_output.txt";
    private static final String ACTUAL_OUTPUT = "actual_output.txt";
    private static final long START_IGNORE = 35;
    private static final long END_IGNORE = 37;

    public static void main(String args[]) throws IOException {
        File fileStudentAnswer = new File(ACTUAL_OUTPUT);
        System.out.println(fileStudentAnswer.getAbsoluteFile().getParent());

        //System.out.println("From now on " + fileStudentAnswer.getAbsolutePath()+ " will be your console");
        System.setOut(new PrintStream(fileStudentAnswer));

        // Run the program being tested and write output to a file
        NurseryRhyme.main(null);

        // Change printstream to stdout so that we can print to the console during testing
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        // Compare the program's file to the expected answer
        File fileAnswerKey = new File(EXPECTED_OUTPUT);
        filesCompareByLine(fileStudentAnswer.toPath(), fileAnswerKey.toPath(), START_IGNORE, END_IGNORE);
        System.out.println("Tests complete.");
    }

    public static long filesCompareByLine(Path path1, Path path2, long start, long end) throws IOException {
        try (BufferedReader bf1 = Files.newBufferedReader(path1); BufferedReader bf2 = Files.newBufferedReader(path2)) {

            long lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = bf1.readLine()) != null) {

                line2 = bf2.readLine();

                System.out.println();
                System.out.println("Checking line number " + lineNumber);
                System.out.println("Your line: " + line1);
                System.out.println("Correct line: " + line2);

                if (lineNumber >= start && lineNumber <= end) {
                    System.out.println("Custom line created by student. Not checking");
                } else if (line2 == null || !line1.equals(line2)) {
                    System.out.println("Lines do not match! " + "In your myanswer.txt, check line number: " + lineNumber);
                    return lineNumber;
                } else {
                    System.out.println("Lines match! Checking next line");
                }
                lineNumber++;
            }

            if (bf2.readLine() == null) {
                System.out.println();
                System.out.println("PASSED!");
                return -1;
            } else {
                System.out.println("Your answer is not correct. See line number: " + lineNumber);
                return lineNumber;
            }
        }
    }
}