package pl.pas.Library.DtoMapper;

//import pl.pas.Library.dto.UserGetDto;
import pl.pas.Library.dto.UserPostDto;
import pl.pas.Library.model.*;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class UserMapper {
    public User ConvertUserPostDtoToUser(UserPostDto userPostDto){
        switch(userPostDto.getAccessLevel()){
            case "CLIENT":
                return new Client(userPostDto.getLogin(), userPostDto.getPassword(), userPostDto.getAddress(), userPostDto.getPesel(),AccessLevel.CLIENT);
            case "ADMINRESOURCES":
                return new AdminResources(userPostDto.getLogin(), userPostDto.getPassword(), userPostDto.getAddress(), userPostDto.getPesel(),AccessLevel.ADMINRESOURCES);
            case "ADMINUSER":
                return new AdminUser(userPostDto.getLogin(), userPostDto.getPassword(), userPostDto.getAddress(), userPostDto.getPesel(),AccessLevel.ADMINUSER);
            default:
                return null;
        }
    }
}
