package uebung18_1;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
/**
 * @see [Oec22]
 */
public class Student
{
    private String firstName;
    private String lastName;
    private String bornInCountry;
    private String livingInCountry;
    private String progLanguage;
    private String[] otherProgLanguages;

    public Student()
    {
        // Preselection
        firstName = "Vorname";
        lastName = "Nachname";
        bornInCountry = "Deutschland";
        livingInCountry = "Deutschland";
        progLanguage = "Java";
        otherProgLanguages = new String[] {"C++", "C#"};
    }

    public String getFirstName() throws InterruptedException {
        Thread.sleep((int) (Math.random() * 100));
        return firstName;
    }

    public void setFirstName(String firstName) throws InterruptedException {
        Thread.sleep((int) (Math.random() * 100));
        this.firstName = firstName;
    }

    public String getLastName() throws InterruptedException {
        Thread.sleep((int) (Math.random() * 100));
        return lastName;
    }

    public void setLastName(String lastName) throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        this.lastName = lastName;
    }

    public String getBornInCountry() throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        return bornInCountry;
    }

    public void setBornInCountry(String bornInCountry) throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        this.bornInCountry = bornInCountry;
    }

    public String getLivingInCountry() throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        return livingInCountry;
    }

    public void setLivingInCountry(String livingInCountry) throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        this.livingInCountry = livingInCountry;
    }

    public String getProgLanguage() throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        return progLanguage;
    }

    public void setProgLanguage(String progLanguage) throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        this.progLanguage = progLanguage;
    }

    public String[] getOtherProgLanguages() throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        return otherProgLanguages;
    }

    public void setOtherProgLanguages(String[] otherProgLanguages) throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        this.otherProgLanguages = otherProgLanguages;
    }
    
    public String handleRequest() throws InterruptedException {

        Thread.sleep((int) (Math.random() * 100));
        if(firstName.equals("Rainer") && lastName.equals("Oechsle"))
        {
            return "student-response-special";
        }
        else
        {
            return "student-response";
        }
    }
}
