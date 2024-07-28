package resources.data;

import java.io.*;
import java.util.Scanner;

// Text file data center (Reading and Writing data)


public class dataManip {
    private File file = new File("src/resources/data/mockDatabase.txt");

    public boolean read(String verification) throws FileNotFoundException {

        try {
            Scanner scan = new Scanner(this.file);

            while (scan.hasNextLine()) {
                String x = scan.nextLine();
                if (x.equals(verification)) {
                    return true;
                }
            }

        } catch (Exception e) {

            System.out.println("No Available Data (for text file sign in)");
            return false;
        } return false;
    }

    public void write(String first, String last, String customerNumber, String pin) throws IOException {
        //File file = new File("src/resources/data/mockDatabase.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(this.file, true));

        pw.println(first + " " + last + " " + customerNumber + " " + pin);
        pw.close();
    }


    // Clears all text file data after app is closed

    public void clear() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(this.file);
        PrintWriter writerLog = new PrintWriter("src/resources/data/loggedID.txt");
        PrintWriter writerAcc = new PrintWriter("src/resources/data/accountStatus.txt");
        PrintWriter writerCust = new PrintWriter("src/resources/data/customerNumber.txt");

        writer.close();
        writerLog.close();
        writerAcc.close();
        writerCust.close();
    }
}
