package com.lerniludi.lingvoj.loaders;

import com.lerniludi.lingvoj.models.Card;
import com.lerniludi.lingvoj.models.Deck;
import com.lerniludi.lingvoj.repositories.CardRepository;
import com.lerniludi.lingvoj.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Données à insérer par défaut dans la base de données
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;

    /**
     * Constructeur
     *
     * @param deckRepository (required)
     */
    @Autowired
    public DatabaseLoader(DeckRepository deckRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
    }

    /**
     * Création des decks par défaut, avec leurs cartes associées
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        // Création des decks
        Deck deck1 = deckRepository.save(new Deck("Deck 1"));
        Deck deck2 = deckRepository.save(new Deck("Deck 2"));
        Deck deck3 = deckRepository.save(new Deck("Deck 3"));

        // Création de 3 cartes pour le deck 1
        Card card1ForDeck1 = cardRepository.save(new Card(deck1, "a", "b"));
        Card card2ForDeck1 = cardRepository.save(new Card(deck1, "d", "bbdsfsf"));
        Card card3ForDeck1 = cardRepository.save(new Card(deck1, "dfd", "b"));

        // Création d'une carte pour le deck 3
        Card card1ForDeck3 = cardRepository.save(new Card(deck3, "dfdsfsdfsfd", "vcxvxcb"));
    }
}
