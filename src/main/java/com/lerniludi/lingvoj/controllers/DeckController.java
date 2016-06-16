package com.lerniludi.lingvoj.controllers;

import com.lerniludi.lingvoj.dtos.DeckDto;
import com.lerniludi.lingvoj.models.Deck;
import com.lerniludi.lingvoj.services.DeckService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlleur des paquets de cartes
 */
@Controller
public class DeckController {


    @Autowired
    private DeckService deckService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * /decks
     * Retourne la liste des paquets de cartes
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<DeckDto> getDecks() {
        List<Deck> decks = deckService.getDecksList();
        return decks.stream().map(deck -> convertToDto(deck)).collect(Collectors.toList());
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