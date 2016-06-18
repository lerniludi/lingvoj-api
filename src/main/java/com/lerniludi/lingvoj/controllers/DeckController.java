package com.lerniludi.lingvoj.controllers;

import com.lerniludi.lingvoj.exceptions.NotFoundException;
import com.lerniludi.lingvoj.models.Deck;
import com.lerniludi.lingvoj.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller des paquets de cartes
 */
@RestController
@RequestMapping(value = "/decks")
public class DeckController {

    @Autowired
    private DeckRepository deckRepository;

    /**
     * Retourne la liste des paquets de cartes
     *
     * @return Iterable<Deck>
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Deck> index() {
        return this.deckRepository.findAll();
    }

    /**
     * Retourne les données associées à un paquet de cartes
     *
     * @param deckId (required) l'identifiant du paquet de carte
     * @return Deck
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{deckId}", method = RequestMethod.GET)
    public Deck show(@PathVariable Long deckId) {
        return this.deckRepository.findById(deckId)
                .orElseThrow(() -> new NotFoundException("Le paquet de cartes n'a pas été trouvé"));
    }
}
