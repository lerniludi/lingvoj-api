package com.lerniludi.lingvoj.controller;

import com.lerniludi.lingvoj.LingvojApplication;
import com.lerniludi.lingvoj.dto.DeckDTO;
import com.lerniludi.lingvoj.model.Deck;
import com.lerniludi.lingvoj.repository.DeckRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * Tests associés au controller Deck
 *
 * @øee http://javabeat.net/spring-boot-testing/ pour référence
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LingvojApplication.class)
@WebIntegrationTest
public class DeckControllerTest {

    private RestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private DeckRepository deckRepository;

    /**
     * Test de la méthode de récupération de l'ensemble des paquets de cartes
     *
     * @throws Exception
     */
    @Test
    public void index() throws Exception {
        // Récupération de l'intégralité des paquets de cartes en provenance de l'API
        DeckDTO[] decks = restTemplate.getForObject("http://localhost:8080/decks", DeckDTO[].class);

        // Validation du nombre de cartes
        assertTrue(decks.length == 3);

        // Validation des attributs des différents paquets de cartes
        assertTrue(decks[0].getId() == 1);
        assertTrue(decks[0].getName().equals("Deck 1"));
        assertTrue(decks[0].getCardsCount() == 3);
        assertTrue(decks[1].getId() == 2);
        assertTrue(decks[1].getName().equals("Deck 2"));
        assertTrue(decks[1].getCardsCount() == 0);
        assertTrue(decks[2].getId() == 3);
        assertTrue(decks[2].getName().equals("Deck 3"));
        assertTrue(decks[2].getCardsCount() == 1);
    }

    /**
     * Test de la méthode de récupération d'un paquet de cartes
     *
     * @throws Exception
     */
    @Test
    public void show() throws Exception {
        // Création d'un deck pour les tests
        Deck deckSource = new Deck("Nouveau deck");
        deckRepository.save(deckSource);

        // Récupération du paquet de carte préalablement créé
        DeckDTO deck = restTemplate.getForObject("http://localhost:8080/decks/" + deckSource.getId(), DeckDTO.class);

        // Validation des attributs du paquet de cartes
        assertTrue(deck.getId() == deckSource.getId());
        assertTrue(deck.getName().equals("Nouveau deck"));
        assertTrue(deck.getCardsCount() == 0);

        // Effacement du deck créé
        deckRepository.delete(deckSource.getId());
    }
}
