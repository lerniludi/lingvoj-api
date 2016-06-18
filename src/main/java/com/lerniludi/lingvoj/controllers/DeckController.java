package com.lerniludi.lingvoj.controllers;

import com.lerniludi.lingvoj.dtos.DeckDTO;
import com.lerniludi.lingvoj.exceptions.NotFoundException;
import com.lerniludi.lingvoj.models.Deck;
import com.lerniludi.lingvoj.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<DeckDTO> index() {
        return this.deckRepository.findAll().stream().map(deck -> DeckDTO.serialize(deck)).collect(Collectors.toList());
    }

    /**
     * Retourne les données associées à un paquet de cartes
     *
     * @param deckId (required) l'identifiant du paquet de carte
     * @return Deck
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{deckId}", method = RequestMethod.GET)
    public DeckDTO show(@PathVariable Long deckId) {
        Deck deck = this.deckRepository.findById(deckId)
                .orElseThrow(() -> new NotFoundException("Le paquet de cartes n'a pas été trouvé"));
        return DeckDTO.serialize(deck);
    }
}