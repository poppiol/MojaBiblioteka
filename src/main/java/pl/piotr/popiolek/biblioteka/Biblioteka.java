package pl.piotr.popiolek.biblioteka;


import com.thoughtworks.xstream.XStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class Biblioteka {

    BookList bookList;

    public Biblioteka() {
        bookList = new BookList();
       /* for (int i = 1; i < 10; i++) {
            bookList.books.add(new Book(i, "KsiazkaNr" + i, "Janusz", true));
        }*/

    }


    public void printBooks() {
        for (Book book : bookList.books) {
            if (book.isAvailable()) {
                System.out.println(book.getId() + ". " + book.getTitle());
            }

        }
    }

    public void borrowABook(int id, Person person) {
        for (Book book : bookList.books) {
            if (book.getId() == id && book.isAvailable()) {
                book.setAvailable(false);
                person.addBookToList(book);
                return;
            }
        }
        System.out.println("Nie wypozyczono ksiazki, zly kod ID");
    }

    public void returnABook(int id, Person person) {
        for (Book book : bookList.books) {
            if (book.getId() == id) {
                book.setAvailable(true);
                person.removeBookFromList(book);
            }
        }
    }

    public void saveListOfBooksToFile(String fileName) throws IOException {
        JSONArray listOfBooks = new JSONArray();
        for (Book book : bookList.books) {
            JSONObject obj = new JSONObject();
            obj.put("Id", book.getId());
            obj.put("Title", book.getTitle());
            obj.put("Author", book.getAuthor());
            obj.put("Available", book.isAvailable());
            listOfBooks.add(obj);
        }
        try (FileWriter file = new FileWriter(fileName + ".txt")) {
            file.write(listOfBooks.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
        }
    }

    public void deserializeFromXML() throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(BookList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BookList books = (BookList) jaxbUnmarshaller.unmarshal( new File("listaKsiazek.xml") );
        for (Book book : books.getBooks()) {
            bookList.getBooks().add(book);
        }
    }


    public void serializeBooksToXML() throws IOException, JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(BookList.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File file = new File("listaKsiazek.xml");
        marshaller.marshal(bookList,file);
    }
}
