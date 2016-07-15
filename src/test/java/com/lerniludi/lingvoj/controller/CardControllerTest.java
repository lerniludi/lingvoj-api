package com.lerniludi.lingvoj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lerniludi.lingvoj.LingvojApplication;
import com.lerniludi.lingvoj.dto.CardDTO;
import com.lerniludi.lingvoj.model.Card;
import com.lerniludi.lingvoj.model.Deck;
import com.lerniludi.lingvoj.repository.CardRepository;
import com.lerniludi.lingvoj.repository.DeckRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertTrue;

/**
 * Tests associés au controller Deck
 *
 * @øee http://javabeat.net/spring-boot-testing/ pour référence
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LingvojApplication.class)
@WebIntegrationTest
@DirtiesContext
public class CardControllerTest {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private RestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    /**
     * Test de la méthode de récupération des cartes associées à un paquet
     *
     * @throws Exception
     */
    @Test
    public void index() throws Exception {
        // Récupération des cartes associées au paquet de carte 1 de l'API
        CardDTO[] cards = restTemplate.getForObject("http://localhost:8080/decks/1/cards", CardDTO[].class);

        // Validation du nombre de cartes du paquet de carte 1
        assertTrue(cards.length == 3);

        // Validation des attributs des différentes cartes
        assertTrue(cards[0].getId() == 1);
        assertTrue(cards[0].getFront().equals("front1"));
        assertTrue(cards[0].getBack().equals("back1"));
        assertTrue(cards[1].getId() == 2);
        assertTrue(cards[1].getFront().equals("front2"));
        assertTrue(cards[1].getBack().equals("back2"));
        assertTrue(cards[2].getId() == 3);
        assertTrue(cards[2].getFront().equals("front3"));
        assertTrue(cards[2].getBack().equals("back3"));

        // Récupération des cartes associées au paquet de carte 2 de l'API
        cards = restTemplate.getForObject("http://localhost:8080/decks/2/cards", CardDTO[].class);

        // Validation du nombre de cartes du paquet de carte 2
        assertTrue(cards.length == 0);
    }

    /**
     * Test de la méthode de récupération d'une carte associée à un paquet
     *
     * @throws Exception
     */
    @Test
    @DirtiesContext
    public void show() throws Exception {
        // Création d'une carte associée au Deck 2 pour les tests
        Deck deckSource = this.deckRepository.findById((long) 2).get();
        Card cardSource = new Card(deckSource, "Mon front", "Et mon back");
        this.cardRepository.save(cardSource);

        // Récupération de la DTO de carte correspondant à la Card préalablement crée
        CardDTO card = restTemplate.getForObject("http://localhost:8080/decks/" + deckSource.getId() +
                "/cards/" + cardSource.getId(), CardDTO.class);

        // Validation des attributs de la carte
        assertTrue(card.getId() == cardSource.getId());
        assertTrue(card.getFront().equals(cardSource.getFront()));
        assertTrue(card.getBack().equals(cardSource.getBack()));

        // Tentative de récupération de la carte par un deck qui ne lui est pas associée
        // La carte ne doit pas pouvoir être récupérable et une erreur doit survenir
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(cardSource).getBytes("UTF-8"), requestHeaders);
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange("http://localhost:8080/decks/1/" +
                        "/cards/" + cardSource.getId(), HttpMethod.GET, httpEntity, LinkedHashMap.class);

        // Validation des données reçues
        assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
}