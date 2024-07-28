package resources.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

import jFiles.controllers.signinController;
import resources.data.Customer;

// Postgres data center for my local ATM database


public class dataManager {

    private final String url = "jdbc:postgresql://localhost:5432/atmdata";
    private final String user = "postgres";
    private final String password = "NorthBrunswick1685!";

    // Method to connect to database prior to any manipulations

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            //System.out.println("Connected to the PostgreSQL server successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // After account is created, the user's credentials are inserted in the respective data table

    public long insertCreds(credentialsTable creds) {
        String sql = "INSERT INTO person.credentials(fullname, firstname, lastname, customernumber, pin) " + "VALUES (?, ?, ?, ?, ?)";
        long id = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, creds.getFullName());
            pstmt.setString(2, creds.getFirstName());
            pstmt.setString(3, creds.getLastName());
            pstmt.setString(4, creds.getCustomerNumber());
            pstmt.setString(5, creds.getPin());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                        //System.out.println(rs.getMetaData());
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    // Initializes account balances to 0

    public long insertAccounts(accountsTable acc) {
        String sql = "INSERT INTO person.accounts(checking, savings) " + "VALUES (?, ?)";
        long id = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setFloat(1, acc.getChecking());
            pstmt.setFloat(2, acc.getSavings());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    // Clears data tables

    public void truncate() {
        try (Connection conn = connect(); Statement statement = conn.createStatement()) {
            int sql = statement.executeUpdate("TRUNCATE " + "person.credentials");
            int sq = statement.executeUpdate("TRUNCATE " + "person.accounts");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Resets ID's in Postgres database

    public void idReset() {
        try (Connection conn = connect(); Statement statement = conn.createStatement()) {
            int sql = statement.executeUpdate("ALTER SEQUENCE person.credentials_id_seq RESTART WITH 1");
            int sq = statement.executeUpdate("ALTER SEQUENCE person.accounts_id_seq RESTART WITH 1");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method used for making sure a valid user is signing in

    public boolean signInVerification(String verification) {

        long id;

        try (Connection conn = connect()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from person.credentials");
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnsNumber = rsmd.getColumnCount();
            boolean match = false;

            while (rs.next()) {
                String nameHolder = "";

                for(int i = 3 ; i <= columnsNumber; i++){

                    if (i == columnsNumber) {
                        nameHolder = nameHolder + rs.getString(i);
                    } else {
                        nameHolder = nameHolder + rs.getString(i) + " ";
                    }
                    System.out.print(rs.getString(i) + " ");
                }
                if (nameHolder.equals(verification)) {
                    match = true;
                    id = Long.parseLong(rs.getString(1));
                    writeLog(id);
                }
                System.out.println();
            }
            return match;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Updates balances in database when a deposit is made

    public void deposit(float amount) {

        try (Connection conn = connect()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from person.accounts");
            String c = "update person.accounts set checking = ? where id = ?";
            String s = "update person.accounts set savings = ? where id = ?";

            String account = this.readAccountStatus();  // Checks which account user is in (i.e. "In Checkings Account")

            int accountColumn = 0;
            if (account.equals("checking")) {
                accountColumn = 2;
            } else if (account.equals("savings")) {
                accountColumn = 3;
            }
            long i = readLogStatus();
            while (rs.next()) {
                System.out.println(rs.getLong(accountColumn));
                if (i == rs.getLong(1)) {

                    if (accountColumn == 2) {
                        float curAmt = rs.getFloat(2);
                        float newAmt = curAmt + amount;
                        PreparedStatement pstmt = conn.prepareStatement(c);
                        pstmt.setFloat(1, newAmt);
                        pstmt.setLong(2, i);
                        pstmt.executeUpdate();
                    } else {
                        float curAmt = rs.getFloat(3);
                        float newAmt = curAmt + amount;
                        PreparedStatement pstmt = conn.prepareStatement(s);
                        pstmt.setFloat(1, newAmt);
                        pstmt.setLong(2, i);
                        pstmt.executeUpdate();
                    }
                }
            }

        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Updates balances in database

    public void withdraw(float amount) {

        try (Connection conn = connect()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from person.accounts");
            String c = "update person.accounts set checking = ? where id = ?";
            String s = "update person.accounts set savings = ? where id = ?";

            String account = this.readAccountStatus();

            int accountColumn = 0;
            if (account.equals("checking")) {
                accountColumn = 2;
            } else if (account.equals("savings")) {
                accountColumn = 3;
            }
            long i = readLogStatus();
            while (rs.next()) {
                System.out.println(rs.getLong(accountColumn));
                if (i == rs.getLong(1)) {

                    if (accountColumn == 2) {
                        float curAmt = rs.getFloat(2);
                        float newAmt = curAmt - amount;
                        PreparedStatement pstmt = conn.prepareStatement(c);
                        pstmt.setFloat(1, newAmt);
                        pstmt.setLong(2, i);
                        pstmt.executeUpdate();
                    } else {
                        float curAmt = rs.getFloat(3);
                        float newAmt = curAmt - amount;
                        PreparedStatement pstmt = conn.prepareStatement(s);
                        pstmt.setFloat(1, newAmt);
                        pstmt.setLong(2, i);
                        pstmt.executeUpdate();
                    }
                }
            }

        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Checks ID of the user that is currently logged in

    public long readLogStatus() throws FileNotFoundException {

        try {
            File f = new File("src/resources/data/loggedID.txt");
            Scanner scan = new Scanner(f);

            String x = scan.nextLine();
            return Long.parseLong(x);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public void writeLog(long id) {
        try {
            File file = new File("src/resources/data/loggedID.txt");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(Long.toString(id));
            pw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeAccount(String account) {
        try {
            File file = new File("src/resources/data/accountStatus.txt");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(account);
            pw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeCustomerNumber(String number) {
        try {
            File file = new File("src/resources/data/customerNumber.txt");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(number);
            pw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Checks account that the user is in

    public String readAccountStatus() throws FileNotFoundException {

        try {
            File f = new File("src/resources/data/accountStatus.txt");
            Scanner scan = new Scanner(f);

            return scan.nextLine();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Returns cutomer number window for new user

    public String readCustomerNumber() throws FileNotFoundException {

        try {
            File f = new File("src/resources/data/customerNumber.txt");
            Scanner scan = new Scanner(f);

            return scan.nextLine();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Returns account balance from database

    public float accountBalance() {
        try (Connection conn = connect()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from person.accounts");

            String account = this.readAccountStatus();

            int accountColumn = 0;
            if (account.equals("checking")) {
                accountColumn = 2;
            } else if (account.equals("savings")) {
                accountColumn = 3;
            }
            long i = readLogStatus();
            while (rs.next()) {
                if (i == rs.getLong(1)) {

                    if (accountColumn == 2) {
                        System.out.println("This is the balance before the withdraw: " + rs.getFloat(accountColumn));
                        return rs.getFloat(accountColumn);
                    } else {
                        System.out.println("This is the balance before the withdraw: " + rs.getFloat(accountColumn));
                        return rs.getFloat(accountColumn);
                    }
                }
            }

        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        } return 0;

    }
    public String getCustomerNumber() throws FileNotFoundException {
        return readCustomerNumber();
    }

}
