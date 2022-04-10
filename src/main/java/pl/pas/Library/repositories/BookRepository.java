package pl.pas.Library.repositories;

import pl.pas.Library.exceptions.BookNotFoundException;
import pl.pas.Library.exceptions.BookNotUniqueIdException;
import pl.pas.Library.model.Book;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.Collections.synchronizedList;

@ApplicationScoped
public class BookRepository implements Repository<Book> {

    private final List<Book> books = synchronizedList(new ArrayList<>());

    @Override
    public Book get(UUID uuid) {
        for (Book c : books) {
            if (c.getUuid().equals(uuid) && !c.isArchived()) {
                return c;
            }
        }
        return null;
    }

    @Override
    public boolean add(Book book){
        book.setUuid(UUID.randomUUID());
        if (book.getUuid() == null || this.get(book.getUuid()) != null) {
            throw new BookNotUniqueIdException("Book with given uuid already exist!");
        }
        return books.add(book);
    }

    @Override
    public boolean remove(Book book) {
        book.setArchived();
        return true;
    }

    @Override
    public Book get(Predicate<Book> predicate) {
        for (Book c : books) {
            if (predicate.test(c) && !c.isArchived()) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Book> getAll(Predicate<Book> predicate) {
        List<Book> ret = new ArrayList<>();

        for (Book c : books) {
            if (predicate.test(c) && !c.isArchived()) {
                ret.add(c);
            }
        }
        return ret;
    }

    public List<Book> getAll() {
        List<Book> ret = new ArrayList<>();

        for (Book c : books) {
            if (!c.isArchived()) {
                ret.add(c);
            }
        }
        return ret;
    }

}
