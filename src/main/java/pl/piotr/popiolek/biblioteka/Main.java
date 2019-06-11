package pl.piotr.popiolek.biblioteka;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    // public static List<Person> persons = new ArrayList<>();

    public static void main(String[] args) throws IOException, JAXBException {

        UsersList usersList = new UsersList();
        deserializeFromXML(usersList);
       /* usersList.persons.add(new Person("Popiolek", "Piotr"));
        usersList.persons.add(new Person("Jabłoński", "Mateusz"));
        usersList.persons.add(new Person("Dumowski", "Mateusz"));
        usersList.persons.add(new Person("Gajewski", "Michał"));
        usersList.persons.add(new Person("a", "Michał"));
*/
        Biblioteka biblioteka = new Biblioteka();
        biblioteka.deserializeFromXML();
        boolean x = true;
        Scanner scan = new Scanner(System.in);


        while (x) {
            String namePerson;
            printMenu();
            int choose = Integer.parseInt(scan.nextLine());
            switch (choose) {
                case 1:
                    System.out.println("Podaj numer książki");
                    choose = Integer.parseInt(scan.nextLine());
                    System.out.println("Podaj nazwisko");
                    namePerson = scan.nextLine();
                    boolean foundPerson = false;
                    for (Person person : usersList.persons) {
                        if (person.getName().equals(namePerson)) {
                            foundPerson = true;
                            biblioteka.borrowABook(choose, person);
                            break;
                        }
                    }
                    if (!foundPerson) System.out.println("Taki uzytkownik nie istnieje, zarejestruj sie wciskajac 4.");
                    break;
                case 2:
                    System.out.println("Podaj nazwisko");
                    namePerson = scan.nextLine();
                    for (Person person : usersList.persons) {
                        if (person.getName().equals(namePerson)) person.printBooks();
                    }
                    System.out.println("Podaj ID ksiazki:");
                    int bookId = Integer.parseInt(scan.nextLine());
                    biblioteka.returnABook(bookId, getPerson(usersList, namePerson));
                    break;
                case 3:
                    biblioteka.printBooks();
                    break;
                case 4:
                    System.out.println("Podaj nazwisko");
                    String name = scan.nextLine();
                    System.out.println("Podaj imie");
                    String surname = scan.nextLine();
                    usersList.persons.add(new Person(name, surname));

                    for (Person person : usersList.persons) {
                        System.out.println(person.getName() + " " + person.getSurname());
                    }
                    break;
                case 5:
                    System.out.println("Do nastepnego razu");
                    biblioteka.serializeBooksToXML();
                    serializeUsersToXML(usersList, "uzytkownicy.xml");
                    exit(0);
                default:
                    System.out.println("Nieprawidlowa komenda");
            }
        }
    }

    private static Person getPerson(UsersList usersList, String namePerson) {
        for (Person person : usersList.persons) {
            if (person.getName().equals(namePerson)) return person;
        }
        return null;
    }

    private static void printMenu() {
        System.out.println("1.Wypozycz ksiazke");
        System.out.println("2.Zwroc ksiazke");
        System.out.println("3.Zobacz dostepne ksiazki");
        System.out.println("4.Dodaj uzytkownika");
        System.out.println("5.Wyjscie");
    }


    public static void serializeUsersToXML(UsersList usersList, String nameFile) throws IOException, JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(UsersList.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File file = new File(nameFile);
        marshaller.marshal(usersList, file);
    }

    public static void deserializeFromXML(UsersList usersList) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(UsersList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        UsersList users = (UsersList) jaxbUnmarshaller.unmarshal(new File("uzytkownicy.xml"));
        for (Person person : users.getPersons()) {
            usersList.getPersons().add(person);
        }
    }

}


