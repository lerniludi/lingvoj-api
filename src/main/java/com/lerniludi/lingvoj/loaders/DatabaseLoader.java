package com.lerniludi.lingvoj.loaders;

import com.lerniludi.lingvoj.models.Deck;
import com.lerniludi.lingvoj.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Données à insérer par défaut dans la base de données
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

    private final DeckRepository deckRepository;

    /**
     * Constructeur
     *
     * @param deckRepository (required)
     */
    @Autowired
    public DatabaseLoader(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Création des decks par défaut
        ArrayList<Deck> defaultDecks = new ArrayList<>();
        defaultDecks.add(new Deck("Deck 1"));
        defaultDecks.add(new Deck("Deck 2"));
        defaultDecks.add(new Deck("Deck 3"));

        this.deckRepository.save(defaultDecks);
    }
}
