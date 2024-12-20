package it.epicode.entity;

import it.epicode.enums.Periodicity;

public class Magazine extends Catalog {
    private Periodicity periodicity;

    public Magazine(String isbn, String title, int publicationYear, int pagesNr, Periodicity periodicity) {
        super(isbn, title, publicationYear, pagesNr);
        this.periodicity = periodicity;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "ISBN code='" + getIsbn() + '\'' +
                "title='" + getTitle() + '\'' +
                "periodicity='" + periodicity+ '\'' +
                "publication year='" + getPublicationYear() + '\'' +
                "pages number='" + getPagesNr() + '\'' +
                '}';
    }
}
