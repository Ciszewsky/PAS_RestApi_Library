package pl.pas.Library.dto;


import pl.pas.Library.model.AccessLevel;
import pl.pas.Library.otherValidation.CheckDateFormat;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class BookDto {

    @NotNull
    @Size(min = 2)
    @Column(name = "title")
    private String title;

    @NotNull
    @Size(min = 2)
    @Column(name = "authorName")
    private String authorName;

    @NotNull
    @CheckDateFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;

    @JsonbCreator
    public BookDto(@JsonbProperty("title")String title,@JsonbProperty("authorName")String authorName,@JsonbProperty("releaseDate")  String releaseDate) {
        this.title = title;
        this.authorName = authorName;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
