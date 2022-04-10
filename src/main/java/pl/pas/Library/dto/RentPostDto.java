package pl.pas.Library.dto;

import pl.pas.Library.model.AccessLevel;
import pl.pas.Library.model.Book;
import pl.pas.Library.model.User;
import pl.pas.Library.otherValidation.CheckDateFormat;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.UUID;

public class RentPostDto {

    @NotNull
    @CheckDateFormat(pattern = "yyyy-MM-dd")
    private String startDate;

    @NotNull
    private UUID clientUuid;

    @NotNull
    private UUID bookUuid;

    @JsonbCreator
    public RentPostDto(@JsonbProperty("startDate")String startDate, @JsonbProperty("clientUuid") UUID clientUuid, @JsonbProperty("bookUuid")UUID bookUuid) {
        this.startDate = startDate;
        this.clientUuid = clientUuid;
        this.bookUuid = bookUuid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public UUID getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(UUID clientUuid) {
        this.clientUuid = clientUuid;
    }

    public UUID getBookUuid() {
        return bookUuid;
    }

    public void setBookUuid(UUID bookUuid) {
        this.bookUuid = bookUuid;
    }
}
