package it.epicode.entity;

public class Book extends Catalog {
    private String author;
    private String genre;


public Book(String isbn, String title, int publicationYear, int pagesNr, String author, String genre){
    super(isbn, title, publicationYear, pagesNr);
    this.author= author;
    this.genre =genre;
}
    public String getAuthor(){
        return  author;
    }

    public void setAuthor(String author){
        this.author = author;
    }
    public String  getGenre(){
        return  genre;
    }
    public void setGenre(String genre){
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN code='" + getIsbn() + '\'' +
                "title='" + getTitle() + '\'' +
                "author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                "publication year='" + getPublicationYear() + '\'' +
                "pages number='" + getPagesNr() + '\'' +
                '}';
    }
}
