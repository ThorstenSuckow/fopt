package fopt4.uebung1_10;


/**
 * uebung1_10
 */
public class Contact {
    private long id;
    private String firstName;
    private String lastName;
    private String mailAddress;

    public Contact(Long id, String firstName, String lastName, String mailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMailAddress() {
        return mailAddress;
    }


    public String toString() {
        return firstName + " " + lastName + "<" + mailAddress +">";
    }
}
