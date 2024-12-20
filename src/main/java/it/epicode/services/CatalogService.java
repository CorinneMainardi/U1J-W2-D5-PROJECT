package it.epicode.services;

import it.epicode.entity.Book;
import it.epicode.entity.Catalog;
import it.epicode.entity.Magazine;
import it.epicode.enums.Periodicity;
import it.epicode.exceptions.ElementNotFound;
import it.epicode.exceptions.ExistingIsbn;

import java.util.List;
import java.util.Scanner;

public class CatalogService {
    private List<Book> books;
    private List<Magazine> magazines;
    private List<Catalog> catalogList;

    public CatalogService(List<Book> books, List<Magazine> magazines, List<Catalog> catalogList) {
        this.books = books;
        this.magazines = magazines;
        this.catalogList = catalogList;
        this.catalogList.addAll(books);
        this.catalogList.addAll(magazines);
    }
    public void addBook(Book book) throws ExistingIsbn {
        if (books.stream().anyMatch(b -> b.getIsbn().equals(book.getIsbn()))) {
            throw new ExistingIsbn("ISBN già presente in archivio: " + book.getIsbn());
        }
        books.add(book);
        catalogList.add(book);
    }
    public void addMagazine(Magazine magazine) throws ExistingIsbn {

        if (magazines.stream().anyMatch(existingMagazine -> existingMagazine.getIsbn().equals(magazine.getIsbn()))) {
            throw new ExistingIsbn("ISBN già presente in archivio: " + magazine.getIsbn());
        }
        magazines.add(magazine);
        catalogList.add(magazine);
    }

    public void removeItemByIsbn(String isbn) throws ElementNotFound {
        boolean isRemoved = catalogList.removeIf(catalog -> catalog.getIsbn().equals(isbn));
        if (!isRemoved) {
            throw new ElementNotFound("ISBN " + isbn + " da eliminare non trovato!");
        }
    }



        public void updateCatalog(String isbnToUpdate, Scanner scanner) throws ElementNotFound {
            Catalog foundCatalog = catalogList.stream()
                    .filter(catalog -> catalog.getIsbn().equals(isbnToUpdate))
                    .findFirst()
                    .orElseThrow(() -> new ElementNotFound("Elemento con ISBN " + isbnToUpdate + " non trovato!"));

            if (foundCatalog instanceof Book) {
                updateBook((Book) foundCatalog, scanner);
            } else if (foundCatalog instanceof Magazine) {
                updateMagazine((Magazine) foundCatalog, scanner);
            }
        }

        private void updateBook(Book book, Scanner scanner) {
            System.out.println("Elemento trovato: " + book);
            boolean updateAnotherField = true;

            while (updateAnotherField) {
                System.out.println("Scegli il campo da aggiornare:");
                System.out.println("1. ISBN");
                System.out.println("2. Titolo");
                System.out.println("3. Anno di pubblicazione");
                System.out.println("4. Numero di pagine");
                System.out.println("5. Autore");
                System.out.println("6. Genere");
                System.out.println("7. Nessun altro campo da aggiornare");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consuma il newline

                switch (choice) {
                    case 1:
                        System.out.println("Inserisci il nuovo ISBN: ");
                        book.setIsbn(scanner.nextLine());
                        System.out.println("ISBN aggiornato con successo!");
                        break;
                    case 2:
                        System.out.println("Inserisci il nuovo titolo: ");
                        book.setTitle(scanner.nextLine());
                        System.out.println("Titolo aggiornato con successo!");
                        break;
                    case 3:
                        System.out.println("Inserisci il nuovo anno di pubblicazione: ");
                        book.setPublicationYear(scanner.nextInt());
                        System.out.println("Anno di pubblicazione aggiornato con successo!");
                        break;
                    case 4:
                        System.out.println("Inserisci il nuovo numero di pagine: ");
                        book.setPagesNr(scanner.nextInt());
                        System.out.println("Numero di pagine aggiornato con successo!");
                        break;
                    case 5:
                        System.out.println("Inserisci il nuovo autore: ");
                        book.setAuthor(scanner.nextLine());
                        System.out.println("Autore aggiornato con successo!");
                        break;
                    case 6:
                        System.out.println("Inserisci il nuovo genere: ");
                        book.setGenre(scanner.nextLine());
                        System.out.println("Genere aggiornato con successo!");
                        break;
                    case 7:
                        updateAnotherField = false;
                        break;
                    default:
                        System.out.println("Scelta non valida, riprova.");
                }
            }
        }

        private void updateMagazine(Magazine magazine, Scanner scanner) {
            System.out.println("Elemento trovato: " + magazine);
            boolean updateAnotherField = true;

            while (updateAnotherField) {
                System.out.println("Scegli il campo da aggiornare:");
                System.out.println("1. ISBN");
                System.out.println("2. Titolo");
                System.out.println("3. Anno di pubblicazione");
                System.out.println("4. Numero di pagine");
                System.out.println("5. Periodicità");
                System.out.println("6. Nessun altro campo da aggiornare");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Inserisci il nuovo ISBN: ");
                        magazine.setIsbn(scanner.nextLine());
                        System.out.println("ISBN aggiornato con successo!");
                        break;
                    case 2:
                        System.out.println("Inserisci il nuovo titolo: ");
                        magazine.setTitle(scanner.nextLine());
                        System.out.println("Titolo aggiornato con successo!");
                        break;
                    case 3:
                        System.out.println("Inserisci il nuovo anno di pubblicazione: ");
                        magazine.setPublicationYear(scanner.nextInt());
                        System.out.println("Anno di pubblicazione aggiornato con successo!");
                        break;
                    case 4:
                        System.out.println("Inserisci il nuovo numero di pagine: ");
                        magazine.setPagesNr(scanner.nextInt());
                        System.out.println("Numero di pagine aggiornato con successo!");
                        break;
                    case 5:
                        System.out.println("Inserisci la nuova periodicità (weekly, monthly, biannual): ");
                        String newPeriodicity = scanner.nextLine();
                        try {
                            Periodicity newEnumPeriodicity = Periodicity.valueOf(newPeriodicity.toUpperCase());
                            magazine.setPeriodicity(newEnumPeriodicity);
                            System.out.println("Periodicità aggiornata con successo!");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Periodicità non valida. Le opzioni sono: weekly, monthly, biannual.");
                        }
                        break;
                    case 6:
                        updateAnotherField = false;
                        break;
                    default:
                        System.out.println("Scelta non valida, riprova.");
                }
            }
        }
    }












