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
        CardDTO[] cards = this.restTemplate.getForObject("http://localhost:8080/decks/1/cards", CardDTO[].class);

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
        cards = this.restTemplate.getForObject("http://localhost:8080/decks/2/cards", CardDTO[].class);

        // Validation du nombre de cartes du paquet de carte 2
        assertTrue(cards.length == 0);
    }

    /**
     * Test de la méthode de création d'une carte associée à un paquet
     *
     * @throws Exception
     */
    @Test
    @DirtiesContext
    public void create() throws Exception {
        // Récupération du deck pour lequel sera créé la carte
        Deck deckSource = this.deckRepository.findById((long) 2).get();

        // Création d'une DTO de carte à envoyer à l'API pour création
        CardDTO cardSource = new CardDTO();
        cardSource.setFront("Face");
        cardSource.setBack("Dos");

        // Appel de la méthode de création d'une carte associée à un paquet
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(cardSource), requestHeaders);
        ResponseEntity<CardDTO> response = this.restTemplate.exchange("http://localhost:8080/decks/" + deckSource.getId() +
                "/cards", HttpMethod.POST, httpEntity, CardDTO.class);

        // Validation des données reçues
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        CardDTO cardResponse = response.getBody();
        assertTrue(cardResponse.getId() == 5);
        assertTrue(cardResponse.getFront().equals(cardSource.getFront()));
        assertTrue(cardResponse.getBack().equals(cardSource.getBack()));

        // Validation des données en base
        Card cardPersisted = this.cardRepository.findByIdAndDeck(cardResponse.getId(), deckSource).get();
        assertTrue(cardPersisted.getId() == 5);
        assertTrue(cardPersisted.getFront().equals(cardSource.getFront()));
        assertTrue(cardPersisted.getBack().equals(cardSource.getBack()));
    }

    /**
     * Test de la méthode de mise à jour d'une carte associée à un paquet
     *
     * @throws Exception
     */
    @Test
    @DirtiesContext
    public void update() throws Exception {
        // Récupération du deck pour lequel sera créé la carte
        Deck deckSource = this.deckRepository.findById((long) 2).get();

        // Création d'une carte source des modifications à effectuer
        Card cardSource = new Card(deckSource, "Front", "Back");
        this.cardRepository.save(cardSource);

        // Création d'une DTO de carte pour envoi à l'API afin de modifier les données de la source
        CardDTO cardPut = new CardDTO();
        cardPut.setFront("Nouveau front");
        cardPut.setBack("Nouveau back");

        // Appel de la méthode d'édition d'une carte associée à un paquet
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(cardPut).getBytes("UTF-8"), requestHeaders);
        ResponseEntity<CardDTO> response = this.restTemplate.exchange("http://localhost:8080/decks/" + deckSource.getId() +
                "/cards/" + cardSource.getId(), HttpMethod.PUT, httpEntity, CardDTO.class);

        // Validation des données reçues
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        CardDTO cardResponse = response.getBody();
        assertTrue(cardResponse.getId() == cardSource.getId());
        assertTrue(cardResponse.getFront().equals(cardPut.getFront()));
        assertTrue(cardResponse.getBack().equals(cardPut.getBack()));

        // Validation des modifications en base
        Card cardPersisted = this.cardRepository.findByIdAndDeck(cardSource.getId(), deckSource).get();
        assertTrue(cardPersisted.getId() == cardSource.getId());
        assertTrue(cardPersisted.getFront().equals(cardPut.getFront()));
        assertTrue(cardPersisted.getBack().equals(cardPut.getBack()));
    }

    @Test
    @DirtiesContext
    public void destroy() {
        // Création d'une carte associée au Deck 2 qui sera supprimée dans les tests
        Deck deckSource = this.deckRepository.findById((long) 2).get();
        Card cardSource = new Card(deckSource, "Carte", "à supprimer");
        this.cardRepository.save(cardSource);

        // Appel de la méthode de suppression de paquet de carte
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:8080/decks/" + deckSource.getId() +
                "/cards/" + cardSource.getId(), HttpMethod.DELETE, httpEntity, String.class);

        // Validation des données reçues
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        assertTrue(response.getBody() == null);

        // Validation de la suppression de la carte en base de données
        assertTrue(this.cardRepository.countByIdAndDeck(cardSource.getId(), deckSource) == 0);
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
        CardDTO card = this.restTemplate.getForObject("http://localhost:8080/decks/" + deckSource.getId() +
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
        ResponseEntity<LinkedHashMap> response = this.restTemplate.exchange("http://localhost:8080/decks/1/" +
                        "/cards/" + cardSource.getId(), HttpMethod.GET, httpEntity, LinkedHashMap.class);

        // Validation des données reçues
        assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
}