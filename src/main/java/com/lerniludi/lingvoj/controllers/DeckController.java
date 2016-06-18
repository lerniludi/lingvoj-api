package com.lerniludi.lingvoj.controllers;

import com.lerniludi.lingvoj.dtos.DeckDto;
import com.lerniludi.lingvoj.exceptions.NotFoundException;
import com.lerniludi.lingvoj.models.Deck;
import com.lerniludi.lingvoj.repositories.DeckRepository;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Autowired
    private DeckRepository deckRepository;

    /**
     * Retourne la liste des paquets de cartes
     *
     * @return Iterable<Deck>
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<DeckDto> index() {
        return this.deckRepository.findAll().stream().map(deck -> convertToDto(deck)).collect(Collectors.toList());
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

    /**
     * TODO: J'aime vraiment pas la conversion entité => dto (et dto => entité dans l'exemple du @see)
     *       A déplacer autre part ?
     *
     * @see http://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
     * @param deck
     * @return
     */
    private DeckDto convertToDto(Deck deck) {
        DeckDto deckDto = modelMapper.map(deck, DeckDto.class);
        return deckDto;
    }

}