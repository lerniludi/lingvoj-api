package com.lerniludi.lingvoj.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext
public class DeckControllerTest {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
     * Test de la méthode de création d'un paquet de cartes
     *
     * @throws Exception
     */
    @Test
    @DirtiesContext
    public void create() throws JsonProcessingException {
        // Création d'une DTO de deck à envoyer à l'API pour création
        DeckDTO deckSource = new DeckDTO();
        deckSource.setName("Super Deck");

        // Appel de la méthode de création de paquet de carte
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(deckSource), requestHeaders);
        ResponseEntity<DeckDTO> response = restTemplate.exchange("http://localhost:8080/decks/",
                HttpMethod.POST, httpEntity, DeckDTO.class);

        // Validation des données reçues
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        DeckDTO deckResponse = response.getBody();
        assertTrue(deckResponse.getId() == 4);
        assertTrue(deckResponse.getName().equals("Super Deck"));
        assertTrue(deckResponse.getCardsCount() == 0);

        // Validation des données en base
        Deck deckPersisted = this.deckRepository.findOne(deckResponse.getId());
        assertTrue(deckPersisted.getName().equals("Super Deck"));
    }

    @Test
    @DirtiesContext
    public void update() throws Exception {
        // Création d'un deck source des modifications à effectuer
        Deck deckSource = new Deck("Deck à mettre à jour");
        deckRepository.save(deckSource);

        // Création d'une DTO de deck pour envoi à l'API afin de modifier les données de la source
        DeckDTO deckPut = new DeckDTO();
        deckPut.setName("Deck mis à jour");

        // Appel de la méthode d'édition de paquet de cartes
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(deckPut).getBytes("UTF-8"), requestHeaders);
        ResponseEntity<DeckDTO> response = restTemplate.exchange("http://localhost:8080/decks/" + deckSource.getId(),
            HttpMethod.PUT, httpEntity, DeckDTO.class);

        // Validation des données reçues
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        DeckDTO deckResponse = response.getBody();
        assertTrue(deckResponse.getId() == deckSource.getId());
        assertTrue(deckResponse.getName().equals("Deck mis à jour"));
        assertTrue(deckResponse.getCardsCount() == 0);

        // Validation des modifications en base
        Deck deckPersisted = this.deckRepository.findOne(deckResponse.getId());
        assertTrue(deckPersisted.getId().equals(deckSource.getId()));
        assertTrue(deckPersisted.getName().equals("Deck mis à jour"));
    }

    @Test
    @DirtiesContext
    public void destroy() {
        // Création d'un deck source qui devra être supprimé par l'appel de l'API
        Deck deckSource = new Deck("Deck à supprimer");
        deckRepository.save(deckSource);

        // Appel de la méthode de suppression de paquet de carte
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/decks/" + deckSource.getId(),
                HttpMethod.DELETE, httpEntity, String.class);

        // Validation des données reçues
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        assertTrue(response.getBody() == null);

        // Validation des modifications en base
        assertTrue(!this.deckRepository.exists(deckSource.getId()));
    }

    /**
     * Test de la méthode de récupération d'un paquet de cartes
     *
     * @throws Exception
     */
    @Test
    @DirtiesContext
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
    }
}
