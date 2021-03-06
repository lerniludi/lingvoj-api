package com.lerniludi.lingvoj.loader;

import com.lerniludi.lingvoj.model.Card;
import com.lerniludi.lingvoj.model.Deck;
import com.lerniludi.lingvoj.repository.CardRepository;
import com.lerniludi.lingvoj.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Initialisation des données à insérer par défaut dans la base de données
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    /**
     * Création des decks par défaut, avec leurs cartes associées
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        // Création des decks
        Deck deck1 = new Deck("Deck 1");
        Deck deck2 = new Deck("Deck 2");
        Deck deck3 = new Deck("Deck 3");
        this.deckRepository.save(Arrays.asList(deck1, deck2, deck3));

        // Création de 3 cartes pour le deck 1
        Card card1 = new Card(deck1, "front1", "back1");
        Card card2 = new Card(deck1, "front2", "back2");
        Card card3 = new Card(deck1, "front3", "back3");

        // Création d'une carte pour le deck 3
        Card card4 = new Card(deck3, "front4", "back4");

        this.cardRepository.save(Arrays.asList(card1, card2, card3, card4));
    }
}
