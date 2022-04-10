package pl.pas.Library.managers;

import pl.pas.Library.exceptions.BookAlreadyRentedException;
import pl.pas.Library.exceptions.BookNotFoundException;
import pl.pas.Library.model.Book;
import pl.pas.Library.repositories.BookRepository;
import pl.pas.Library.repositories.RentRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Stateless
public class BookManager {

    @Inject
    private BookRepository bookRepository;
    @Inject
    private RentRepository rentRepository;

    public Book addBook(Book book){
        bookRepository.add(book);
        return book;
    }

    public Book getBook(UUID id)  {
        Book book = bookRepository.get(id);
        if (book == null) {
            throw new BookNotFoundException("There is no book with given id!");
        }
        return book;
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public void updateBook(UUID uuid, Book UpdatedBook){
        Book book = bookRepository.get(uuid);
        if (book == null) {
            throw new BookNotFoundException("There is no book with given id!");
        }
        book.setTitle(UpdatedBook.getTitle());
        book.setAuthorName(UpdatedBook.getAuthorName());
        book.setReleaseDate(UpdatedBook.getReleaseDate());
    }

    public void removeBook(UUID uuid){
        Book book = bookRepository.get(uuid);
        if (book == null) {
            throw new BookNotFoundException("There is no book with given id!");
        }
        if (rentRepository.getAll(x -> x.getBook().getUuid().equals(uuid) && x.getEndDate() == null).size() != 0) {
            throw new BookAlreadyRentedException("You cannot remove rented book!");
        }
        bookRepository.remove(book);

    }

}
