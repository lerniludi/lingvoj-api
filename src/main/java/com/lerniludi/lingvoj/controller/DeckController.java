package com.lerniludi.lingvoj.controller;

import com.lerniludi.lingvoj.dto.DeckDTO;
import com.lerniludi.lingvoj.exception.NotFoundException;
import com.lerniludi.lingvoj.model.Deck;
import com.lerniludi.lingvoj.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller des paquets de cartes
 */
@RestController
@RequestMapping(value = "/decks")
public class DeckController extends LingvojController {

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
     * Créé un paquet de cartes
     *
     * @param deckDTO (required) Données du deck a mettre à jour
     * @return DeckDTO
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public DeckDTO create(@RequestBody DeckDTO deckDTO) {
        Deck deck = deckDTO.deserialize();
        this.deckRepository.save(deck);
        return DeckDTO.serialize(deck);
    }


    /**
     * Met à jour un maquet de cartes
     *
     * @param deckId (required) l'identifiant du paquet de carte
     * @param deckDTO (required) Données du deck a mettre à jour
     * @return DeckDTO
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{deckId}", method = RequestMethod.PUT)
    public DeckDTO update(@PathVariable Long deckId, @RequestBody DeckDTO deckDTO) {
        this.deckRepository.findById(deckId)
                .orElseThrow(() -> new NotFoundException(
                        getTranslation("error.notFound", new Object[]{getEntityNameTranslated()})));
        Deck deck = deckDTO.deserialize();
        deck.setId(deckId);
        this.deckRepository.save(deck);
        return DeckDTO.serialize(deck);
    }

    /**
     * Retourne les données associées à un paquet de cartes
     *
     * @param deckId (required) l'identifiant du paquet de carte
     * @return DeckDTO
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{deckId}", method = RequestMethod.GET)
    public DeckDTO show(@PathVariable Long deckId) {
        Deck deck = this.deckRepository.findById(deckId)
                .orElseThrow(() -> new NotFoundException(
                        getTranslation("error.notFound", new Object[]{getEntityNameTranslated()})));
        return DeckDTO.serialize(deck);
    }
}