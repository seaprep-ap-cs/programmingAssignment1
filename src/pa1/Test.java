package pa1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;



public class Test {
  private static final String TEST_OUTPUT = "expected_output.txt";
  private static final String MY_OUTPUT = "actual_output.txt";
  
  public static void main(String args[]) throws IOException {
    System.out.println("Output of your program...");
    HelloWorld.main(null);
    
    System.out.println("\nStarting tests...");

    //Instantiating the File class
    File file = new File(MY_OUTPUT);
    String parentPath = file.getAbsoluteFile().getParent();

    //Instantiating the PrintStream class
//    System.out.println("From now on "+ file.getAbsolutePath()+" will be your console");
    System.setOut(new PrintStream(file));

    //Printing values to file
    HelloWorld.main(null);

    // Change printstream to stdout
    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

    // Grade the  answer
    File fileAnswer = new File(TEST_OUTPUT);
    filesCompareByLine(file.toPath(), fileAnswer.toPath());
    System.out.println("Tests complete.");
  }

  public static long filesCompareByLine(Path path1, Path path2) throws IOException {
    try (BufferedReader bf1 = Files.newBufferedReader(path1);
        BufferedReader bf2 = Files.newBufferedReader(path2)) {

      long lineNumber = 1;
      String line1 = "", line2 = "";
      while ((line1 = bf1.readLine()) != null) {
        line2 = bf2.readLine();
        System.out.println("Checking line number " + lineNumber);
        System.out.println("Your line: " + line1);
        System.out.println("Correct line: " + line2);
        if (line2 == null || !line1.equals(line2)) {
          System.out.println("Lines do not match! " + "In your myanswer.txt, check line number: " + lineNumber);
          return lineNumber;
        }
        lineNumber++;
      }
      if (bf2.readLine() == null) {
        System.out.println("PASSED!");
        return -1;
      } else {
        System.out.println("Your answer is not correct. See line number: " + lineNumber);
        return lineNumber;
      }
    }
  }
}
