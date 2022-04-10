package pl.pas.Library.model;

public class AdminResources extends User{
    public AdminResources(String login, String password, String address, String pesel, AccessLevel accessLevel) {
        super(login, password, address, pesel, accessLevel);
    }
}
