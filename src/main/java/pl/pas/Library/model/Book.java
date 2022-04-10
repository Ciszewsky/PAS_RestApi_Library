package pl.pas.Library.model;


import java.time.LocalDate;
import java.util.UUID;

public class Book {

    private UUID uuid;
    private String title;
    private String authorName;
    private LocalDate releaseDate;
    private boolean archived = false;


    public Book(String title, String authorName, LocalDate releaseDate) {
        this.title = title;
        this.authorName = authorName;
        this.releaseDate = releaseDate;

    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setArchived() {
        this.archived = true;
    }



    @Override
    public String toString() {
        return "Book{" +
                "id='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
