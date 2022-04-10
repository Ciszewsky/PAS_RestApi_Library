package pl.pas.Library.model;

import java.time.LocalDate;
import java.util.UUID;


public class Rent {

    private UUID uuid;
    private LocalDate startDate;
    private LocalDate endDate = null;
    private User client;
    private Book book;

    public Rent(LocalDate startDate, User client, Book book) {
        this.uuid = UUID.randomUUID();
        this.startDate = startDate;
        this.client = client;
        this.book = book;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public User getClient() {
        return client;
    }

    public Book getBook() {
        return book;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
