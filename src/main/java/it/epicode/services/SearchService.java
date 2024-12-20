package it.epicode.services;

import it.epicode.entity.Book;
import it.epicode.entity.Catalog;
import it.epicode.entity.Magazine;
import it.epicode.exceptions.ElementNotFound;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    private List<Book> books;
    private List<Magazine> magazines;
    private List<Catalog> catalogList;

    // Costruttore
    public SearchService(List<Book> books, List<Magazine> magazines, List<Catalog> catalogList) {
        this.books = books;
        this.magazines = magazines;
        this.catalogList = catalogList;
    }
    public Catalog searchByIsbn(String isbnToSearch) throws ElementNotFound {
        return catalogList.stream()
                .filter(catalog -> catalog.getIsbn().equals(isbnToSearch))
                .findFirst()
                .orElseThrow(() -> new ElementNotFound("ISBN " + isbnToSearch + " non trovato!"));
    }




        public List<Catalog> searchByPublicationYear(int yearToSearch) throws ElementNotFound {
            List<Catalog> foundYear = catalogList.stream()
                    .filter(catalog -> catalog.getPublicationYear() == yearToSearch)
                    .collect(Collectors.toList());

            if (foundYear.isEmpty()) {
                throw new ElementNotFound("Non esiste nessun elemento nel catalogo pubblicato nell'anno " + yearToSearch);
            }

            return foundYear;

    }

    public List<Book> searchByAuthor(String authorToSearch) throws ElementNotFound {
        List<Book> foundBooks = books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(authorToSearch)) // Confronto case-insensitive
                .collect(Collectors.toList());

        if (foundBooks.isEmpty()) {
            throw new ElementNotFound("Non sono stati trovati libri dell'autore: " + authorToSearch);
        }

        return foundBooks;
    }





    public List<Catalog> searchByTitle(String titleToSearch) throws ElementNotFound {
        List<Catalog> foundTitles = catalogList.stream()
                .filter(catalog -> catalog.getTitle().toLowerCase().contains(titleToSearch))
                .collect(Collectors.toList());

        if (foundTitles.isEmpty()) {
            throw new ElementNotFound("Non sono stati trovati pubblicazioni corrispondenti al titolo: " + titleToSearch + " nel nostro catalogo.");
        }

        return foundTitles;
    }

}
