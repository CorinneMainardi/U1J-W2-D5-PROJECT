package it.epicode.entity;

public class Catalog {

    private String isbn;
    private String title;
    private int publicationYear;
    private int pagesNr;

    public Catalog (String isbn, String title, int publicationYear, int pagesNr){
        this.isbn = isbn;
        this.title = title;
        this.publicationYear = publicationYear;
        this.pagesNr = pagesNr;
    }
    public String getIsbn(){
        return isbn;
    }
    public void setIsbn(String isbn){
        this.isbn= isbn;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title= title;
    }
    public int getPublicationYear(){
        return  publicationYear;
    }
    public void setPublicationYear(int publicationYear){
        this.publicationYear = publicationYear;
    }
    public int getPagesNr(){
        return  pagesNr;
    }
    public void setPagesNr(int pagesNr){
        this.pagesNr = pagesNr;
    }
}
