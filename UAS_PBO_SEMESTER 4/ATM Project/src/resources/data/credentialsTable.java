package resources.data;

// Manages credentials

class credentialsTable {
    private String fullName;
    private String firstName;
    private String lastName;
    private String customerNumber;
    private String pin;

    credentialsTable(String fullName, String firstName, String lastName, String customerNumber, String pin) {
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerNumber = customerNumber;
        this.pin = pin;
    }

    String getFullName() {return fullName;}
    String getFirstName() {return firstName;}
    String getLastName() {return lastName;}
    String getCustomerNumber() {return customerNumber;}
    String getPin() {return pin;}

}
