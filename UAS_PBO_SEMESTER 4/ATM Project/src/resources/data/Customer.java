package resources.data;

import java.io.IOException;
import java.util.Random;

public class Customer {

    // Prepares user information then sends it to the database

    public Customer(String firstName, String lastName, String pin) throws IOException {
        Random rand = new Random();
        String fullName = firstName + " " + lastName;
        int customerNumber = rand.nextInt(999999);
        String customerNumber1 = String.format("%06d", customerNumber);


        dataManip data = new dataManip();
        data.write(firstName, lastName, customerNumber1, pin);

        // Inserts the new customer data into the Postgres database (Checking/Savings account data as well as User credentials)

        credentialsTable dbCreds = new credentialsTable(fullName, firstName, lastName, customerNumber1, pin);
        dataManager database = new dataManager();
        long id = database.insertCreds(dbCreds);
        database.writeCustomerNumber(customerNumber1);

        System.out.println(String.format("%s, %s, %s, %s, %s has been inserted with id %d",
                fullName, firstName, lastName, customerNumber1, pin, id));

        accountsTable dbAcc = new accountsTable();
        long accID = database.insertAccounts(dbAcc);
        float checking = 0;
        float savings = 0;
        System.out.println(String.format("%f, %f has been inserted with id %d",
                checking, savings, accID));
    }
}
