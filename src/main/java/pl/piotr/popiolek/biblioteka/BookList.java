package pl.piotr.popiolek.biblioteka;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ksiazki")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookList {

    @XmlElement(name = "ksiazka")
    List<Book> books = new ArrayList<>();

    public BookList() {
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}