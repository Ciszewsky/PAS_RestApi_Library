package pl.pas.Library.DtoMapper;

import pl.pas.Library.dto.BookDto;
import pl.pas.Library.model.Book;

import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Stateless
public class BookMapper {
    public Book ConvertBookDtoToBook(BookDto bookDto){
        return new Book(bookDto.getTitle(),bookDto.getAuthorName(), LocalDate.parse(bookDto.getReleaseDate()));
    }

}
