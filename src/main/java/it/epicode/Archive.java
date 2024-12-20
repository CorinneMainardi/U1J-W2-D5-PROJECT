package it.epicode;

import it.epicode.entity.Book;
import it.epicode.entity.Catalog;
import it.epicode.entity.Magazine;
import it.epicode.enums.Periodicity;
import it.epicode.exceptions.ElementNotFound;
import it.epicode.exceptions.ExistingIsbn;
import it.epicode.services.CatalogService;
import it.epicode.services.SearchService;
import it.epicode.services.StatisticsService;
import java.util.*;
public class Archive {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        List<Book> books = new ArrayList<>();
        List<Magazine> magazines = new ArrayList<>();
        List<Catalog> catalogList = new ArrayList<>();
        CatalogService catalogService = new CatalogService(books, magazines, catalogList);
        SearchService searchService = new SearchService(books,magazines,catalogList);
        StatisticsService statisticsService = new StatisticsService(catalogList);

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Aggiungi un libro");
            System.out.println("2. Aggiungi una rivista");
            System.out.println("3. Ricerca per ISBN");
            System.out.println("4. Ricerca per anno di pubblicazione");
            System.out.println("5. Ricerca per autore");
            System.out.println("6. Ricerca per titolo o parte di esso");
            System.out.println("7. Aggiorna elemento nel catalogo");
            System.out.println("8. Rimuovi un elemento tramite ISBN");
            System.out.println("9. Stampa statistiche");
            System.out.println("10. Esci");
            System.out.print("Scegli un'opzione: ");
           try{
                   int choice = getValidChoice(scanner);

                switch (choice) {
                    case 1 -> addBook(scanner, catalogService);
                    case 2 -> addMagazine(scanner, catalogService);
                    case 3 -> searchByIsbn(scanner,searchService);
                    case 4 -> searchByPublicationYear(scanner, searchService);
                    case 5 -> searchByAuthor(scanner, searchService);
                    case 6 -> searchByTitle(scanner, searchService);
                    case 7 -> updateCatalog(scanner, catalogService);
                    case 8 -> removeItemByIsbn(scanner, catalogService);
                    case 9 -> statisticsService.printStatistics();
                    case 10 -> {
                        scanner.close();

                        System.exit(0);
                    }
                    default -> System.out.println("Opzione non valida. Riprova.");
                }
           } catch (Exception e){
               System.out.println("Errore nel menu: " + e.getMessage());
           }

        }

    }
    private static int getValidChoice(Scanner scanner) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 10) {
                    return choice;
                } else {
                    System.out.println("Opzione non valida. Scegli tra 1 e 10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: Inserisci un numero valido.");
            }
        }
    }
    public static Catalog insertAttributes(Scanner scanner) {
        System.out.println("Inserisci ISBN: ");
        String isbn = scanner.next();
        System.out.println("Inserisci titolo: ");
        String title = scanner.next();
        System.out.println("Inserisci l'anno di pubblicazione: ");
        int publicationYear = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Inserisci il numero di pagine: ");
        int pagesNr = scanner.nextInt();
        scanner.nextLine();
        return new Catalog(isbn, title, publicationYear, pagesNr);
    }

public static void addBook(Scanner scanner,CatalogService catalogService){
   Catalog insertAttributes = insertAttributes(scanner);
    System.out.println("inserisci l'autore: ");
    String author = scanner.nextLine();
    System.out.println("inserisci il genere: ");
    String genre = scanner.nextLine();
    try{
    Book book = new Book (insertAttributes.getIsbn(), insertAttributes.getTitle(), insertAttributes.getPublicationYear(), insertAttributes.getPagesNr(), author, genre);
       catalogService.addBook(book);
        System.out.println("Libro aggiunto con successo!");
    }catch (ExistingIsbn e){
        System.out.println(e.getMessage());
    }
}

    public static void addMagazine(Scanner scanner, CatalogService catalogService) {
        Catalog insertAttributes = insertAttributes(scanner);
        System.out.println("Inserisci la periodicità. La periodicità può essere WEEKLY (settimanale), MONTHLY (mensile), BIANNUAL (semestrale): ");
        String periodicity = scanner.nextLine();
        try {
            Periodicity enumPeriodicity = Periodicity.valueOf(periodicity.toUpperCase());
            Magazine magazine = new Magazine(
                    insertAttributes.getIsbn(),
                    insertAttributes.getTitle(),
                    insertAttributes.getPublicationYear(),
                    insertAttributes.getPagesNr(),
                    enumPeriodicity
            );
            catalogService.addMagazine(magazine);
            System.out.println("Rivista aggiunta con successo!");
        } catch (IllegalArgumentException e) {
            System.out.println("Periodicità non valida. Le opzioni sono: WEEKLY, MONTHLY, BIANNUAL.");
        } catch (ExistingIsbn e) {
            System.out.println(e.getMessage());
        }
    }

    public static void searchByIsbn(Scanner scanner, SearchService searchService) {
        System.out.println("Inserisci codice ISBN da cercare: ");
        String isbnToSearch = scanner.nextLine().trim();

        try {
            Catalog foundCatalog = searchService.searchByIsbn(isbnToSearch);
            if (foundCatalog != null) {
                System.out.println("ISBN " + isbnToSearch + " trovato: " + foundCatalog);
            }
        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante la ricerca: " + e.getMessage());
        }
    }




    public static void searchByPublicationYear(Scanner scanner, SearchService searchService) {
        System.out.println("Inserisci l'anno di pubblicazione da cercare: ");
        int yearToSearch = scanner.nextInt();
        scanner.nextLine();

        try {
            List<Catalog> foundYear = searchService.searchByPublicationYear(yearToSearch);

            System.out.println("Le pubblicazioni trovate nel catalogo avvenute nell'anno " + yearToSearch + " sono: ");
            foundYear.forEach(System.out::println);

        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante la ricerca: " + e.getMessage());
        }
    }


    public static void searchByAuthor(Scanner scanner, SearchService searchService) {
        System.out.println("Inserisci l'autore da cercare: ");
        String authorToSearch = scanner.nextLine().trim();

        try {
            List<Book> foundBooks = searchService.searchByAuthor(authorToSearch);
            System.out.println("Libri trovati dell'autore " + authorToSearch + ":");
            foundBooks.forEach(System.out::println);

        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante la ricerca: " + e.getMessage());
        }
    }

    public static void searchByTitle(Scanner scanner, SearchService searchService) {
        System.out.println("Inserisci il titolo da ricercare: ");
        String titleToSearch = scanner.nextLine().trim();

        try {
            List<Catalog> foundTitles = searchService.searchByTitle(titleToSearch);
            if (!foundTitles.isEmpty()) {
                System.out.println("Titoli trovati per: " + titleToSearch + ":");
                foundTitles.forEach(System.out::println);
            }
        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante la ricerca: " + e.getMessage());
        }
    }

    public static void removeItemByIsbn(Scanner scanner, CatalogService catalogService) {
        System.out.println("Inserisci l'ISBN da eliminare: ");
        String isbnToRemove = scanner.nextLine().trim();

        try {
            catalogService.removeItemByIsbn(isbnToRemove);
            System.out.println("ISBN " + isbnToRemove + " eliminato con successo.");
        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante la rimozione: " + e.getMessage());
        }
    }




    public static void updateCatalog(Scanner scanner, CatalogService catalogService) {
        System.out.println("Inserisci il codice ISBN dell'elemento da aggiornare: ");
        String isbnToUpdate = scanner.nextLine();
        try {
            catalogService.updateCatalog(isbnToUpdate, scanner);
            System.out.println("Aggiornamento completato.");
        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante l'aggiornamento: " + e.getMessage());
        }
    }





}






