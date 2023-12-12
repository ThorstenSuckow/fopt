package fopt4.uebung2_12.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactModel
{
    private long nextId = 0;
    private Map<Long, Contact> contacts;

    public ContactModel()
    {
        contacts = new HashMap<>();
        createSampleData();
    }

    public List<Contact> searchContacts(String[] keywords)
    {
        List<Contact> matches = new ArrayList<Contact>();
        if(keywords == null || keywords.length == 0)
        {
            matches.addAll(contacts.values());
        }
        else
        {
            for(Contact contact: contacts.values())
            {
                for(String keyword: keywords)
                {
                    keyword = keyword.toLowerCase();
                    if (contact.getFirstName().toLowerCase().contains(keyword) ||
                        contact.getLastName().toLowerCase().contains(keyword))
                    {
                        matches.add(contact);
                        break;
                    }
                }
            }
        }
        return matches;
    }

    public void updateContact(Contact updatedContact)
    {
        contacts.put(updatedContact.getId(), updatedContact);
    }

    private void createContact(String firstName, String lastName,
                               String mailAddress)
    {
        Contact contact = new Contact(nextId, firstName, lastName,
                                      mailAddress);
        contacts.put(contact.getId(), contact);
        nextId++;
    }

    private void createSampleData()
    {
        createContact("Johann Sebastian", "Bach", "jsbach@thomas-kirche-leipzig.de");
        createContact("Gilbert", "Becaud", "gilbert@becaud.fr");
        createContact("Ludwig", "van Beethoven", "luigi@bonn.de");
        createContact("Hildegard", "von Bingen", "hilde@bingen.de");
        createContact("Bob", "Dylan", "bob@dylan.com");
        createContact("David", "Crosby", "crosby@csny.com");
        createContact("Friedrich", "Engels", "engels@wuppertal.de");
        createContact("Ella", "Fitzgerald", "ella@ella.com");
        createContact("David", "Gilmour", "david@pink-floyd.com");
        createContact("Herbert", "Gr�nemeyer", "herbert@eisbaer.de");
        createContact("Georg Friedrich", "H�ndel", "haendel@haendel.uk");
        createContact("George", "Harrison", "george@beatles.uk");
        createContact("Joseph", "Haydn", "papahaydn@hochschule-trier.de");
        createContact("Klaus", "Hoffmann", "hoffmann@berlin.de");
        createContact("Mick", "Jagger", "mick@stones.com");
        createContact("Norah", "Jones", "norah@jones.com");
        createContact("Immanuel", "Kant", "i.kant@hochschule-trier.de");
        createContact("Hildegard", "Knef", "hildchen@knef.de");
        createContact("Mark", "Knopfler", "knopfler@greatguitars.com");
        createContact("John", "Lennon", "john@beatles.uk");
        createContact("Awa", "Ly", "awa@ly.it");
        createContact("Karl", "Marx", "marx@trier.de");
        createContact("Paul", "McCartney", "paul@beatles.uk");
        createContact("Ulla", "Meinecke", "ulla@meinecke.de");
        createContact("Katie", "Melua", "katie@melua.uk");
        createContact("Joni", "Mitchell", "joni@mitchell.com");
        createContact("Van", "Morrison", "van@morrison.com");
        createContact("Anna", "M�ller", "anna@haeufiger-name.de");
        createContact("David", "M�ller", "david@haeufiger-name.de");
        createContact("Thomas", "M�ller", "thomas@haeufiger-name.de");
        createContact("Wolfgang Amadeus", "Mozart", "wolferl@mozart.at");
        createContact("Graham", "Nash", "nash@csny.com");
        createContact("Keith", "Richards", "keith@stones.com");
        createContact("Lisa", "Schmitt", "lisa@haeufiger-name.de");
        createContact("Clara", "Schumann", "clara@robert-schumann.de");
        createContact("Carly", "Simon", "carly@simon-carly.com");
        createContact("Paul", "Simon", "paul@simon.com");
        createContact("Patti", "Smith", "pattismith@pattismith.com");
        createContact("Ringo", "Starr", "ringo@beatles.uk");
        createContact("Stephen", "Stills", "stills@csny.com");
        createContact("Roger", "Waters", "roger@pink-floyd.com");
        createContact("Charlie", "Watts", "charlie@stones.com");
        createContact("Brian", "Wilson", "brian@beachboys.com");
        createContact("Carl", "Wilson", "carl@beachboys.com");
        createContact("Dennis", "Wilson", "dennis@beachboys.com");
        createContact("Ron", "Wood", "ron@stones.com");
        createContact("Richard", "Wright", "rick@pink-floyd.com");
        createContact("Neil", "Young", "young@csny.com");
    }
}
