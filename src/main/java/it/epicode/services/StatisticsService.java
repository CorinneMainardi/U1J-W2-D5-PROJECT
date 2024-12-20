package it.epicode.services;

import it.epicode.entity.Book;
import it.epicode.entity.Catalog;
import it.epicode.entity.Magazine;
import it.epicode.exceptions.ElementNotFound;

import java.util.Comparator;
import java.util.List;

public class StatisticsService {
    private List<Catalog> catalogList;


    public StatisticsService(List<Catalog> catalogList) {
        this.catalogList = catalogList;
    }


    public void printStatistics() {
        try {
            long totalBooks = catalogList.stream()
                    .filter(catalog -> catalog instanceof Book)
                    .count();

            long totalMagazines = catalogList.stream()
                    .filter(catalog -> catalog instanceof Magazine)
                    .count();

            Catalog maxPagesElement = catalogList.stream()
                    .max(Comparator.comparingInt(Catalog::getPagesNr))
                    .orElseThrow(() -> new ElementNotFound("Nessun elemento nel catalogo per calcolare le statistiche."));

            double averagePages = catalogList.stream()
                    .mapToInt(Catalog::getPagesNr)
                    .average()
                    .orElseThrow(() -> new ElementNotFound("Nessun elemento nel catalogo per calcolare la media delle pagine."));

            System.out.println("Statistiche del catalogo:");
            System.out.println("Numero totale di libri: " + totalBooks);
            System.out.println("Numero totale di riviste: " + totalMagazines);
            System.out.println("Pubblicazione con il maggior numero di pagine: " + maxPagesElement);
            System.out.println("Media delle pagine di tutte le pubblicazioni: " + averagePages);

        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante il calcolo delle statistiche: " + e.getMessage());
        }
    }
}