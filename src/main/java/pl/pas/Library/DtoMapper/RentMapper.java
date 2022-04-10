package pl.pas.Library.DtoMapper;

//import pl.pas.Library.dto.RentGetDto;
import pl.pas.Library.dto.RentPostDto;
//import pl.pas.Library.dto.UserGetDto;
import pl.pas.Library.managers.BookManager;
import pl.pas.Library.managers.UserManager;
import pl.pas.Library.model.Book;
import pl.pas.Library.model.Rent;
import pl.pas.Library.model.User;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RentMapper {

    @Inject
    BookManager bookManager;
    @Inject
    UserManager userManager;

    @Inject
    UserMapper mapper;

    public Rent convertRentDtoToRent(RentPostDto rentPostDto) {
        User user = userManager.getUser(rentPostDto.getClientUuid());
        Book book = bookManager.getBook(rentPostDto.getBookUuid());
        return new Rent(LocalDate.parse(rentPostDto.getStartDate()),user,book);
    }

}
