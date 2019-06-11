package pl.piotr.popiolek.biblioteka;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "uzytkownik")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    private String name;
    private String surname;
    private List<Book> bookList = new ArrayList<>();

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void addBookToList(Book book) {
        bookList.add(book);
    }

    public void removeBookFromList(Book book) {
        if (bookList.contains(book)) {
            bookList.remove(book);
        }
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void printBooks() {
        for (Book book : bookList) {
            System.out.println(book.getId() + ". " + book.getTitle());
        }
    }
}
